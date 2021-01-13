import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Client {
	private int No;  // No of Client u
	private float ave_r;  // user average filling of client u
	private float gamma;
	private List<Integer> I_u_sample;  // sample item set of client u
	private List<Integer> I_u;   //rated item set of client u

	// ==========================================================
	// initialization of client u
	Client(int No){
		this.No = No;
		this.ave_r = Data.userRatingSumTrain[this.No] / Data.userRatingNumTrain[this.No] ;
		this.gamma = Data.gamma;
		this.I_u = new  LinkedList<Integer> (Data.I_u[this.No]);
		this.I_u_sample = new LinkedList<Integer>(Data.I);
		I_u_sample.removeAll(this.I_u);

	}
	// ==========================================================

	// ==========================================================
	public void train() {
		HashMap<Integer, ArrayList<Float>> VGradientMap = new HashMap<Integer, ArrayList<Float>>();

		Collections.shuffle(this.I_u_sample);

		int userID = this.No;	
		// --- Calculate gradient via rated items 
		for (int i : this.I_u) {
			int itemID = i;
			float rating = Data.ratings[userID][itemID];

			
			float [] Ou = new float[Data.d];				
			if( Data.delta_o!=0 && Data.user_rating_number[userID] > 0)
			{
				float feedback_num_u_sqrt = 0;

				HashSet<Integer> itemSet = Data.Train_ExplicitFeedbacks.get(userID);		

				if(itemSet.contains(itemID) )
				{
					if( itemSet.size()>1 )
					{
						feedback_num_u_sqrt 
						= (float) Math.sqrt(Data.user_rating_number[userID]- 1);
					}
				}
				else
				{
					feedback_num_u_sqrt 
					= (float) Math.sqrt(Data.user_rating_number[userID]);
				}
				

				if (feedback_num_u_sqrt>0)
				{
					for( int i3 : itemSet )
					{
						if(i3 != itemID)
						{
							for (int f=0; f<Data.d; f++)
							{
								Ou[f] += Data.O[i3][f];
							}	    		
						}
					}

					// --- normalization
					for (int f=0; f<Data.d; f++)
					{
						Ou[f] = Ou[f] / feedback_num_u_sqrt;
					}
				}

			}


			// prediction and error
			float pred = 0;
			float err = 0;
			for (int f=0; f<Data.d; f++)
			{	
				pred += ( Data.U[userID][f] + Data.delta_o * Ou[f]) * Data.V[itemID][f];	    				
			}
			err = rating-pred;


			// --- update U, V	    			
			ArrayList<Float> latenDimensionsList = new ArrayList<Float>(Data.d);
			for(int f=0; f<Data.d; f++)
			{	
				float grad_U_f = -err * Data.V[itemID][f] + Data.lambda * Data.U[userID][f];
				float grad_V_f = -err * ( Data.U[userID][f] 
							+ Data.delta_o * Ou[f]) + Data.lambda * Data.V[itemID][f];
				latenDimensionsList.add(grad_V_f);	    			
				
				Data.U[userID][f] = Data.U[userID][f] - this.gamma * grad_U_f;

			}
			VGradientMap.put(itemID, latenDimensionsList);
			Data.V_Gradient.put(userID, VGradientMap);


			// -----------------------	  
			// --- update O    			
			if( Data.delta_o!=0 && Data.user_rating_number[userID] > 0)
			{

				float feedback_num_u_sqrt = 0;
				
				HashSet<Integer> itemSet = Data.Train_ExplicitFeedbacks.get(userID);
				
				if(itemSet.contains(itemID) )
				{
					if( itemSet.size()>1 )
					{
						feedback_num_u_sqrt 
						= (float) Math.sqrt(Data.user_rating_number[userID]- 1);
					}
				}
				else
				{
					feedback_num_u_sqrt 
					= (float) Math.sqrt(Data.user_rating_number[userID]);
				}


				if (feedback_num_u_sqrt>0)
				{
					for(int i3 : itemSet)
					{	    					
						if(i3 != itemID)
						{
							for (int f=0; f<Data.d; f++)
							{
								Data.O[i3][f] = (float) (Data.O[i3][f] - 
										this.gamma * ( -err * Data.V[itemID][f] / feedback_num_u_sqrt + Data.lambda * Data.O[i3][f] ));
							}
						}
					}

				}
			}
		}
		// -------------------------------------------------------------	  

		
		
		// --- use averaging ratings to train
		int sample_number = (int) (Data.rho * this.I_u.size() < I_u_sample.size()? Data.rho * this.I_u.size() : I_u_sample.size());
		for (int count = 0; count < sample_number; ++count) {	
			int itemID = I_u_sample.get(count);
			float rating = ave_r;


			float [] Ou = new float[Data.d];				
			if( Data.delta_o!=0 && Data.user_rating_number[userID] > 0)
			{				
				float feedback_num_u_sqrt = 0;

				HashSet<Integer> itemSet = Data.Train_ExplicitFeedbacks.get(userID);		

				if(itemSet.contains(itemID) )
				{
					if( itemSet.size()>1 )
					{
						feedback_num_u_sqrt 
						= (float) Math.sqrt(Data.user_rating_number[userID]- 1);
					}
				}
				else
				{
					feedback_num_u_sqrt 
					= (float) Math.sqrt(Data.user_rating_number[userID]);
				}
				
				
				if (feedback_num_u_sqrt>0)
				{
					for( int i3 : itemSet )
					{
						if(i3 != itemID)
						{
							for (int f=0; f<Data.d; f++)
							{
								Ou[f] += Data.O[i3][f];
							}	
						}
					}

					// --- normalization
					for (int f=0; f<Data.d; f++)
					{
						Ou[f] = Ou[f] / feedback_num_u_sqrt;			
					}
				}

			}


			// prediction and error
			float pred = 0;
			float err = 0;
			for (int f=0; f<Data.d; f++)
			{	
				pred += ( Data.U[userID][f] + Data.delta_o * Ou[f]) * Data.V[itemID][f];	    				
			}
			err = rating-pred;


			// --- update U, V	    			
			ArrayList<Float> latenDimensionsList = new ArrayList<Float>(Data.d);
			for(int f=0; f<Data.d; f++)
			{	
				float grad_U_f = -err * Data.V[itemID][f] + Data.lambda * Data.U[userID][f];
				float grad_V_f = -err * ( Data.U[userID][f] + Data.delta_o * Ou[f]) + Data.lambda * Data.V[itemID][f];
				latenDimensionsList.add(grad_V_f);	    			

				Data.U[userID][f] = Data.U[userID][f] - this.gamma * grad_U_f;

			}
			VGradientMap.put(itemID, latenDimensionsList);
			Data.V_Gradient.put(userID, VGradientMap);
			
			
			// -----------------------	  
			// --- update O    			
			if( Data.delta_o!=0 && Data.user_rating_number[userID] > 0)
			{
				float feedback_num_u_sqrt = 0;

				HashSet<Integer> itemSet = Data.Train_ExplicitFeedbacks.get(userID);		

				if(itemSet.contains(itemID) )
				{
					if( itemSet.size()>1 )
					{
						feedback_num_u_sqrt 
						= (float) Math.sqrt(Data.user_rating_number[userID]- 1);
					}
				}
				else
				{
					feedback_num_u_sqrt 
					= (float) Math.sqrt(Data.user_rating_number[userID]);
				}
				
				if (feedback_num_u_sqrt>0)
				{

					// --- update O
					for(int i3 : itemSet)
					{	    					
						if(i3 != itemID)
                    	{
							for (int f=0; f<Data.d; f++)
							{
								Data.O[i3][f] = (float) (Data.O[i3][f] - 
										this.gamma * ( -err * Data.V[itemID][f] / feedback_num_u_sqrt + Data.lambda * Data.O[i3][f] ));
							}
                    	}
					}
					
					// --- fill O
					for (int f=0; f<Data.d; f++)
					{    			
						Data.O[itemID][f] = (float) (Data.O[itemID][f] - 
								this.gamma * ( -err * Data.V[itemID][f] / feedback_num_u_sqrt + Data.lambda * Data.O[itemID][f] ));
					}
				}
			}
		}

		this.gamma = (float) (this.gamma * 0.9);

	}
}
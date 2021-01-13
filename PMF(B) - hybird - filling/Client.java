import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Client implements Runnable{
	private int No;  // No of Client u
	private float ave_r;  // user average filling of client u
	private float gamma;
	private int iter;  // iteration number
	private List<Integer> I_u_sample;  // sample item set of client u
	private List<Integer> I_u;   //rated item set of client u
	private int sample_number;

	// initialization of client u
	Client(int No){
		this.No = No;
		this.ave_r = Data.userRatingSumTrain[this.No] / Data.userRatingNumTrain[this.No] ;
		this.gamma = Data.gamma;
		this.I_u = new  LinkedList<Integer> (Data.I_u[this.No]);
		this.I_u_sample = new LinkedList<Integer>(Data.I);
		I_u_sample.removeAll(this.I_u);
	}

	public void run() {
		float grad_U[] = new float[Data.d];
		HashMap<Integer, ArrayList<Float>> VGradientMap = new HashMap<Integer, ArrayList<Float>>();


		// --- Calculate gradient via rated items 
		for (int i : this.I_u) {
			float pred = 0; 
			for (int f=0; f<Data.d; f++)
			{
				pred += Data.U[this.No][f] * Data.V[i][f];
			}
			//			float error = Data.r[this.No][i] - pred;
			float error = (float) (Data.traningDataMap.get(this.No).get(i) - pred);


			for(int f=0; f<Data.d; f++)
			{	
				grad_U[f] += -error * Data.V[i][f] + Data.lambda * Data.U[this.No][f];
			}


			ArrayList<Float> latenDimensionsList = new ArrayList<Float>(Data.d);
			for(int f=0; f<Data.d; f++)
			{	
				float gradientV = -error * Data.U[this.No][f] + Data.lambda * Data.V[i][f];
				latenDimensionsList.add(gradientV);	    			
			}
			VGradientMap.put(i, latenDimensionsList);
		}
		// ----------------------------------------------------


		float [] temp_U = new float[Data.d];
		if(Data.rho != 0)
		{
			// --- training U and V to replace user averaging
			if(this.iter > Data.start_hybrid_averaging_iterations)
			{
				for (int f = 0; f < Data.d; f++) {
					temp_U[f] = Data.U[this.No][f];
				}


				for (int iteration = 0; iteration < Data.local_train_iterations; iteration++) {
					float temp_grad_U[] = new float[Data.d];
					for (int i : this.I_u) {
						float pred = 0; 
						for (int f=0; f<Data.d; f++)
						{
							pred += temp_U[f] * Data.V[i][f];
						}
						//					float error = Data.r[this.No][i] - pred;
						float error = (float) (Data.traningDataMap.get(this.No).get(i) - pred);


						for(int f=0; f<Data.d; f++)
						{	
							temp_grad_U[f] += -error * Data.V[i][f] + Data.lambda * temp_U[f];
						}
					}
					for(int f=0; f<Data.d; f++) temp_U[f] = (float) (temp_U[f] - Data.gamma * temp_grad_U[f] / this.I_u.size());

				}
			}
		}
		// ----------------------------------------------------


		// --- Calculate gradient via sampled items 
		int sample_number = (int) (Data.rho * this.I_u.size() < I_u_sample.size()? Data.rho * this.I_u.size() : I_u_sample.size());
		if(Data.rho != 0)Collections.shuffle(this.I_u_sample);
		for (int count = 0; count < sample_number; ++count) {
			int i = this.I_u_sample.get(count);	
			float pred = 0; 
			for (int f=0; f<Data.d; f++)
			{
				pred += Data.U[this.No][f] * Data.V[i][f];
			}
			float error = 0;
			if(this.iter > Data.start_hybrid_averaging_iterations)
			{
				float temp_pred = 0;
				for (int f=0; f<Data.d; f++)
				{
					temp_pred += temp_U[f] * Data.V[i][f];
				}
				error = temp_pred - pred;
			}
			else
			{
				error = this.ave_r - pred;
			}


			for(int f=0; f<Data.d; f++)
			{			
				grad_U[f] += -error * Data.V[i][f] + Data.lambda * Data.U[this.No][f];		    			
			}

			ArrayList<Float> latenDimensionsList = new ArrayList<Float>(Data.d);
			for(int f=0; f<Data.d; f++)
			{	
				float gradientV = -error * Data.U[this.No][f] + Data.lambda * Data.V[i][f];
				latenDimensionsList.add(gradientV);	    			
			}
			VGradientMap.put(i, latenDimensionsList);
		}
		Data.V_Gradient.put(this.No, VGradientMap);
		// ----------------------------------------------------


		// --- Update user specific latent feature locally
		for(int f=0; f<Data.d; f++) Data.U[this.No][f] = (float) (Data.U[this.No][f] - Data.gamma * grad_U[f] / (this.I_u.size() + sample_number));
		this.gamma = (float) (this.gamma * 0.9);  //Decrese $\gamma$
		this.iter++;
	}

}
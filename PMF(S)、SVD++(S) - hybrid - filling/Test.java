
import java.io.IOException;
import java.util.*;

public class Test 
{
    public static void test() throws IOException 
	{       	
    	// --- number of test cases
    	float mae=0;
    	float rmse=0;    	
    	
    	// ====================================================
    	float [][] Osum = new float[Data.n+1][Data.d];
		
    	for( int userID = 1; userID<=Data.n; userID++ )
    	{	
    		if( Data.delta_o!=0 && Data.user_rating_number[userID] > 0)
    		{
    			float feedback_num_u_sqrt = (float) Math.sqrt(Data.user_rating_number[userID]);
    			
    			HashSet<Integer> itemSet = Data.Train_ExplicitFeedbacks.get(userID);			    			
    			for( int i3 : itemSet )
    			{
    				for (int f=0; f<Data.d; f++)
    				{
    					Osum[userID][f] += Data.O[i3][f];
    				}	    		
    			}

    			// --- normalization
    			for (int f=0; f<Data.d; f++)
    			{
    				Osum[userID][f] = Osum[userID][f] / feedback_num_u_sqrt;			
    			}
	    		// ----------------------	
    		}

    		

    	}
    	// ====================================================
    	    	
    	for(int t=0; t<Data.num_test; t++)
    	{
    		int userID = Data.indexUserTest[t];
    		int itemID = Data.indexItemTest[t];
    		float rating = Data.ratingTest[t];
    		
    		// ===========================================    		
    		// --- prediction via inner product
    		float pred = 0;
    		for (int f=0; f<Data.d; f++)
    		{
    			pred += ( Data.U[userID][f] + Data.delta_o * Osum[userID][f]  )*Data.V[itemID][f];	
    		}
    		// ===========================================
    		
    		// ===========================================
    		if(pred < Data.MinRating) pred = Data.MinRating;
    		if(pred > Data.MaxRating) pred = Data.MaxRating;
    		
			float err = pred-rating;
			mae += Math.abs(err);
			rmse += err*err;
    		// ===========================================    		
    	}
    	float MAE = mae/Data.num_test;
    	float RMSE = (float) Math.sqrt(rmse/Data.num_test);
    	
    	//output result
    	String result = "MAE:" + Float.toString(MAE) +  "| RMSE:" + Float.toString(RMSE);
    	System.out.println(result);
    	try {
			Data.bw.write(result +"\r\n"); 
			Data.bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    // =============================================================
}
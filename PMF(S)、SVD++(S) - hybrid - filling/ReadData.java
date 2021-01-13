
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ReadData
{
	public static void readData() throws IOException 
	{
		// --- some statistics, start from index "1"
		Data.userRatingSumTrain = new float[Data.n+1];
		Data.userRatingNumTrain = new int[Data.n+1];
		Data.user_rating_number = new int[Data.n+1];

		Data.I_u = new HashSet[Data.n + 1];
		Data.I = new HashSet<Integer>();
		for (int i = 1; i <= Data.m; ++i)  {
			Data.I.add(i);
		}
		// ----------------------------------------------------     
		
		
		// --- number of test records
		Data.num_test = 0;
		BufferedReader brTest = new BufferedReader(new FileReader(Data.fnTestData));
		String line = null;
		while ((line = brTest.readLine())!=null)
		{
			Data.num_test += 1;
		}
		System.out.println("num_test: " + Data.num_test);
		// ----------------------------------------------------


		// ----------------------------------------------------  	
		// --- train data
		Data.ratings = new float [Data.n + 1][Data.m + 1]; // start from index "1"

		// ----------------------------------------------------
		// Training data: (userID,itemID,rating)
		BufferedReader brTrain = new BufferedReader(new FileReader(Data.fnTrainingData));    	
		line = null;
		while ((line = brTrain.readLine())!=null)
		{	
			String[] terms = line.split("\\s+|,|;");
			int userID = Integer.parseInt(terms[0]);
			int itemID = Integer.parseInt(terms[1]);
			float rating = Float.parseFloat(terms[2]);
			
			Data.userRatingSumTrain[userID] += rating;
			Data.userRatingNumTrain[userID] += 1;
			
			Data.ratings[userID][itemID] = rating;
			if (Data.I_u[userID] == null) Data.I_u[userID] = new HashSet<Integer>();
			Data.I_u[userID].add(itemID);

			if(Data.Train_ExplicitFeedbacks.containsKey(userID))
			{
				HashSet<Integer> itemSet = Data.Train_ExplicitFeedbacks.get(userID);
				itemSet.add(itemID);
				Data.Train_ExplicitFeedbacks.put(userID, itemSet);
			}
			else
			{
				HashSet<Integer> itemSet = new HashSet<Integer>();
				itemSet.add(itemID);
				Data.Train_ExplicitFeedbacks.put(userID, itemSet);
			}

			// ---
			Data.user_rating_number[userID] += 1;
		}
		brTrain.close();
		System.out.println("Finished reading the train data");
		// ----------------------------------------------------    	


		// ----------------------------------------------------    	
		// --- Locate memory for the data structure  
		// --- test data
		Data.indexUserTest = new int[Data.num_test];
		Data.indexItemTest = new int[Data.num_test];
		Data.ratingTest = new float[Data.num_test];
		// ----------------------------------------------------


		// ----------------------------------------------------
		// Test data: (userID,itemID,rating)   	
		int id_case = 0; // initialize it to zero
		brTest = new BufferedReader(new FileReader(Data.fnTestData));
		line = null;
		while ((line = brTest.readLine())!=null)
		{	
			String[] terms = line.split("\\s+|,|;");
			int userID = Integer.parseInt(terms[0]);    		
			int itemID = Integer.parseInt(terms[1]);
			float rating = Float.parseFloat(terms[2]);
			Data.indexUserTest[id_case] = userID;
			Data.indexItemTest[id_case] = itemID;
			Data.ratingTest[id_case] = rating;
			id_case+=1;
		}
		brTest.close();
		System.out.println("Finished reading the test data");
		// ----------------------------------------------------
	}    
}

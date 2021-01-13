import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Data 
{
	// === Configurations	
	// the number of latent dimensions
	public static int d = 20; 
	
    // tradeoff $\lambda$
	public static float lambda = 0.01f;
	
	// tradeoff $\delta_o$
	public static float delta_o = 0f; // delta_o = 1, corresponding SVD++, else PMF
	
	// filling parameter $\rho$
    public static float rho = 0;
    
	// learning rate $\gamma$
	public static float gamma = 0.01f;

	
	// === Input data files
	public static String fnTrainingData = "";
	public static String fnTestData = "";
	public static String fnOutputData = "";


	public static int n = 943; // number of users
	public static int m = 1682; // number of items
	public static int num_test; // number of test triples of (user,item,rating)

	public static float MinRating = 1.0f; // minimum rating value (1 for ML100K and ML1M)
	public static float MaxRating = 5.0f; // maximum rating value

	// scan number over the whole data
	public static int num_iterations = 0; 


	// === training user data
	public static HashMap<Integer, HashSet<Integer>> Train_ExplicitFeedbacks 
	= new HashMap<Integer, HashSet<Integer>>();  // SVD++
	
	
	// === training user data
	public static float[][] ratings;
	
	// === items rated by user u
	public static Set<Integer> []I_u;
	// === all items
	public static HashSet<Integer> I;
    
	// === test data
	public static int[] indexUserTest;
	public static int[] indexItemTest;
	public static float[] ratingTest;

	// === some statistics, start from index "1"
	public static int[] user_rating_number;
    public static float[] userRatingSumTrain;
	public static int[] userRatingNumTrain;

	// === model parameters to learn, start from index "1"
	public static float[][] U;
	public static float[][] V;
	public static float[][] O;
	
	// intermediate gradient
	public static HashMap<Integer, HashMap<Integer, ArrayList<Float>>> V_Gradient = new HashMap<Integer, HashMap<Integer, ArrayList<Float>>>();
	
	// === file operation
	public static FileWriter fw ;
	public static BufferedWriter bw;
}

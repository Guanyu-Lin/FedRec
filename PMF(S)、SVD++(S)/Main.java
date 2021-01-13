import java.io.*;
import java.util.concurrent.BrokenBarrierException;


public class Main 
{
    public static void main(String[] args) throws IOException, InterruptedException, BrokenBarrierException 
	{	
		// 1. read configurations		
		ReadConfigurations.readConfigurations(args);

		// 2. read training data and test data
        ReadData.readData();
        
		// 3. apply initialization
		Initialization.initialization();

		// 4. training
		Server.train(Data.num_iterations);
		
		// 5. test
		Test.test();		
    }
}
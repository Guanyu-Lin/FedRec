import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

public class Main {
	
	public static void main(String[] args) throws IOException, InterruptedException, BrokenBarrierException {

		// 1. read configurations
		ReadConfigurations.readConfigurations(args);
		
		// 2. read training data and test data
        ReadData.readData();
               
		// 3. apply initialization
		Initialization.initialization();
        
		// 4. start server
		Server server = new Server();
		
        // 5. train
		server.train(Data.num_iterations);
        
        // 6. test
		Test.test();
	}

}

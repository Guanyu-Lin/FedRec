import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
	Server() {

	}
	public void train(int num_iterations) throws InterruptedException, BrokenBarrierException {
		// ==========================================================
		// --- Construct Clients
		Client client[] = new Client[Data.n + 1];
		for (int u : Data.trainUserNo) {
			client[u] = new Client(u);
		}
		// ----------------------------------------------------

		// ==========================================================
		// --- Train
		for (int iter = 0; iter < num_iterations; iter++){

			// output each iteration result
			try {
				Data.bw.write("Iter:" + Integer.toString(iter) + "| ");
				Data.bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.print("Iter:" + Integer.toString(iter) + "| ");
			try {
				Test.test();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// =======================================================
			// --- Each client download master model
			//All the users begin to update the user-specific latent feature and send back intermediate gradient
			ExecutorService  executorService = Executors.newFixedThreadPool(500);
			for (int u : Data.trainUserNo) {
				executorService.submit(client[u]);
			}
			executorService.shutdown();
			// ----------------------------------------------------


			while(true){  
				if(executorService.isTerminated()){ 
					// =======================================================
					//After the server has received all the intermediate gradient, it begins to update the item-specific latent feature
					float grad_V[][] = new float[Data.m + 1][Data.d];
					float countI[] = new float[Data.m + 1];
					for (Integer userID : Data.V_Gradient.keySet()) {
						for (Integer itemID : Data.V_Gradient.get(userID).keySet()) {
							countI[itemID]++;
							for(int f=0; f<Data.d; f++)
							{	
								grad_V[itemID][f] += Data.V_Gradient.get(userID).get(itemID).get(f);		    			
							}
						}
					}

					for (int itemID = 1; itemID < Data.m + 1; itemID++) {
						if (countI[itemID] != 0)
							for(int f=0; f<Data.d; f++) Data.V[itemID][f] = (float) (Data.V[itemID][f] - Data.gamma * grad_V[itemID][f] / countI[itemID]);
					}
					// ----------------------------------------------------

					Data.gamma = (float) (Data.gamma * 0.9);  //Decrese $\gamma$
					break;
				}
			}
		}
		// ----------------------------------------------------
	}

}
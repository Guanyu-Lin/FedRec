import java.io.IOException;

public class Server{
	Server() {

	}
	public static void train(int num_iterations) throws InterruptedException {
		// ==========================================================
		// --- Construct Clients
		Client client[] = new Client[Data.n + 1];
		for (int u = 1; u <= Data.n; ++u) {
			client[u] = new Client(u);
		}

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




			// ===========================================
			// --- Each client download master model
			//All the users begin to update the user-specific latent feature and send back intermediate gradient
			for (int iter_rand = 1; iter_rand <= Data.n; ++iter_rand) {
				int u = (int)(Data.n * Math.random()) + 1;
				client[u].train();
				
				for (Integer i : Data.V_Gradient.get(u).keySet()) {
					for(int f=0; f<Data.d; f++)
					{	
						Data.V[i][f] = Data.V[i][f] - Data.gamma * Data.V_Gradient.get(u).get(i).get(f);
					}
				}	
			}
			// =====================================================
			
			
			Data.gamma = (float) (Data.gamma * 0.9); // Decrease the learning rate$\gamma$
		}
		// ==========================================================
	}

}

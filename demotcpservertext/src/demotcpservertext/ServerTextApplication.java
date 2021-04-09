package demotcpservertext;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTextApplication {

	public static void main(String[] args) throws IOException {
		
		//Launch server frame
		ServerTextFrame serverFrame = new ServerTextFrame();
		serverFrame.setVisible(true);
		
		//Binding to a port No.
		int portNo = 4228;
		ServerSocket serverSocket = new ServerSocket(portNo);
		
		
		TextGenerator text = new TextGenerator();
		CalculatorText numberOfText = new CalculatorText();
		
		//Counter to keep track of number of connections
		int totalRequest = 0;
		
		numberOfText.setNumberOfText(text.getText());
		
		
		//Server needs to be alive forever
		while(true) {
			
			//Message to indicate server is alive
			serverFrame.updateServerStatus(false);
			
			
			//Accept client connection
			Socket clientSocket = serverSocket.accept();
			
			//Calculate number of text
			
			
			// Create stream to write data on the network
						DataOutputStream outputStream = 
								new DataOutputStream(clientSocket.getOutputStream());
						
			
			//Send number of text back to client
						
						outputStream.write(numberOfText.getNumberOfText());
						
						
						// Update the request status
						serverFrame.updateRequestStatus(
								"Text sent to the client: " + text.getText());
						serverFrame.updateRequestStatus("Accepted connection to from the "
								+ "client. Total request = " + ++totalRequest );
			
			//Close everything
						clientSocket.close();
						serverSocket.close();
			
			
		}

	}

}

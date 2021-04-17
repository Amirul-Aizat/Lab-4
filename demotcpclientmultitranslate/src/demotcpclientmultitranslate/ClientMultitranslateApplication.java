package demotcpclientmultitranslate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientMultitranslateApplication {

	public static void main(String[] args) 
	
			throws UnknownHostException, IOException {
		
		// Launch client-side frame
		ClientFrame clientDateFrame = new ClientFrame();
		clientDateFrame.setVisible(true);
		
	 
		
		// Connect to the server @ localhost, port 4228
		Socket socket = new Socket(InetAddress.getLocalHost(), 4229);
		
		// Update the status of the connection
		clientDateFrame.updateConnectionStatus(socket.isConnected());
		
		// Read from network
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		

		// Display the current date
		String textReceived = bufferedReader.readLine();

		clientDateFrame.changeText(textReceived);
		
		
		// Close everything
		bufferedReader.close();
		socket.close();

	}

}
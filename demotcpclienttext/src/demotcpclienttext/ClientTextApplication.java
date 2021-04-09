package demotcpclienttext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTextApplication {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		//Launch client side frame
		ClientTextFrame clientFrame = new ClientTextFrame();
		clientFrame.setVisible(true);
		
		//Connect server port 4228
		Socket socket = new Socket(InetAddress.getLocalHost(),4228);
		
		//Update status of connection
		clientFrame.updateConnectionStatus(socket.isConnected());
		
		//Read from network 
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		//Display the number of text
		int numberOfText = bufferedReader.read();
		
		clientFrame.updateNumberOfText(numberOfText);
		
		//close
		bufferedReader.close();
		socket.close();
		

	}

}

package demotcpservermultitranslate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class ServerMultitranslateApplication {
	
	private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
	private static final String CLIENT_SECRET = "PUBLIC_SECRET";
	private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
	
	public static void main(String[] args) throws IOException {
			
			// Launch the server frame
			ServerFrame serverFrame = new ServerFrame();
			serverFrame.setVisible(true);
			
			// Binding to a port or any other port no you are fancy of
			int portNo = 4229;
			ServerSocket serverSocket = new ServerSocket(portNo);
			
			int totalRequest = 0;
			
			while (true) {
				
				// Message to indicate server is alive
				serverFrame.updateServerStatus(false);
				
				
				// Accept client request for connection
				Socket clientSocket = serverSocket.accept();
		
				DataOutputStream outputStream = 
						new DataOutputStream(clientSocket.getOutputStream());
				
				
				String text;
				String oriText = "Please input your text then click 'Translate'";
		    	String fromLang = "en";
		        String toLang = "es";
		    		    	
		        
		        
		        text = translate(fromLang, toLang, oriText);
		        
				text = " ";

		        outputStream.writeBytes(text);
				
				// Close the socket
				clientSocket.close();
			
				// Update the request status
				serverFrame.updateRequestStatus(
						"Data sent to the client: " + text);
				serverFrame.updateRequestStatus("Accepted connection to from the "
						+ "client. Total request = " + ++totalRequest );
				
			}
	}
	
	public static String translate(String fromLang, String toLang, String text) {
		
		
		try {
			
			 String jsonPayload = new StringBuilder()
				      .append("{")
				      .append("\"fromLang\":\"")
				      .append(fromLang)
				      .append("\",")
				      .append("\"toLang\":\"")
				      .append(toLang)
				      .append("\",")
				      .append("\"text\":\"")
				      .append(text)
				      .append("\"")
				      .append("}")
				      .toString();

				    URL url = new URL(ENDPOINT);
				    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				    conn.setDoOutput(true);
				    conn.setRequestMethod("POST");
				    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
				    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
				    conn.setRequestProperty("Content-Type", "application/json");

				    OutputStream os = conn.getOutputStream();
				    os.write(jsonPayload.getBytes());
				    os.flush();
				    os.close();

				    int statusCode = conn.getResponseCode();
				    System.out.println("Status Code: " + statusCode);
				    
				    BufferedReader br = new BufferedReader(new InputStreamReader(
				        (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
				      ));
				    String output;
				    while ((output = br.readLine()) != null) {
				    	
				        return output;
				    }
				    conn.disconnect();
				    
				    return "Error";
				  
		}catch(Exception $e) {
			return "Error";
		}
	}

}

package demotcpclientmultitranslate;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class ClientFrame extends JFrame {

	private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
	private static final String CLIENT_SECRET = "PUBLIC_SECRET";
	private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
	
	private static final long serialVersionUID = 1L;
	private JLabel lblServerDate, lblStatusValue, lblCount;
		private int width = 600;
	private int height = 600;
	
	private String selectedLang = "en";
	private JComboBox langList ;
	
	public ClientFrame () {
		
		// Default frame setting
		this.setLayout(new BorderLayout());
		this.setTitle("TCP Application: Client Side");
		this.setSize(width, height);
		

        this.setLocationRelativeTo(null);
        
		lblServerDate = new JLabel("-");
		lblCount = new JLabel("-");
		lblStatusValue = new JLabel("-");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Organize components
		loadComponent();
		
	}
	
	public void changeText(String text) {
		
		this.lblServerDate.setText(text);		
	}
	
	public void updateConnectionStatus (boolean connStatus) {
		

		// Default status. Assuming for the worst case scenario.
		String status = "No connection to server.";
		
		// Validate status of connection
		if (connStatus)
			status = "Connected to the server.";
		
				
		// Update the status on frame
		this.lblStatusValue.setText(status);
	}
	
	private JPanel getConnectionStatusPanel(Font font) {
		
		// Create component
		JPanel panel = new JPanel();
		JLabel lblConnStatus = new JLabel ("Connection status: ");
		
		// Style the component
		lblConnStatus.setFont(font);
		lblStatusValue.setFont(font);
		lblConnStatus.setOpaque(true);
		lblStatusValue.setOpaque(true);
		
		// Organize components into panel
		panel.add(lblConnStatus);
		panel.add(lblStatusValue);
		
		return panel;
		
	}
	
	private JPanel getTextPanel(Font font) {
		
		// Create component to display date retrieve from the server
		JPanel panel = new JPanel();
		JLabel lblTitle = new JLabel ("Enter words to translate (ENG):");

		// Style the component
		lblTitle.setFont(font);
		lblTitle.setOpaque(true);
		
//		lblServerDate.setFont(font);
//		lblServerDate.setOpaque(true);
		
		final TextArea txtArea=new TextArea("");
		txtArea.setBounds(10,30, 100,100);  
		
		String[] lang = { "Bahasa Malaysia", "Arabic", "Korean"};
		langList = new JComboBox(lang);
		langList.setBounds(100,100,80,30); 
		
		 Button submitBtn=new Button("Translate");  
		 submitBtn.setBounds(50,100,80,30); 
		 
		 
		 
		 langList.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			    	
					selectedLang = (String) langList.getSelectedItem();

			    }
			});
		 
		submitBtn.addActionListener(new ActionListener(){  
	    public void actionPerformed(ActionEvent e){
	    	
	    
	    	String toLang = "ms";
	    	
	    	if(selectedLang == "Bahasa Malaysia") {
	    		toLang = "ms";
	    	}else if(selectedLang == "Arabic") {
	    		toLang = "ar";
	    	}else if(selectedLang == "Korean") {
	    		toLang = "ko";
	    	}
	    	
	    	String text = txtArea.getText();
	    	String fromLang = "en";
	        
	        String displayText = "";
	    	
	        displayText = translate(fromLang, toLang, text);
//	    	displayText = "Not translated :" + selectedLang;
	    	
	        changeText(displayText);
	    }});  


		panel.add(lblTitle);
		panel.add(langList);
		panel.add(txtArea);
		panel.add(submitBtn);
		panel.add(lblServerDate);
		
		return panel;
	} 
		
	private JPanel getTitlePanel(Font font) {
		
		JPanel panel = new JPanel();
		JLabel lblTitle = new JLabel ("TRANSLATION");

		lblTitle.setFont(font);
		lblTitle.setOpaque(true);
		
		lblCount.setFont(font);
		lblCount.setOpaque(true);

		// Organize components into panel
		panel.add(lblTitle);
//		panel.add(lblCount);
		
		return panel;
	}
	
	private void loadComponent() {
		
		Font font = this.getFontStyle();
		
		JPanel countPanel = getTitlePanel(font);
		this.add(countPanel, BorderLayout.PAGE_START);
		
		JPanel textPanel = getTextPanel(font);
		this.add(textPanel, BorderLayout.CENTER);
		
		
		JPanel connectionPanel = this.getConnectionStatusPanel(font);		
		this.add(connectionPanel, BorderLayout.PAGE_END);		
		
	}
	
	private Font getFontStyle() {
		
		Font font = new Font ("Serif", Font.PLAIN, 20);
		
		return font;
	}
	
	
	final  String translate(String fromLang, String toLang, String text) {
		
		
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
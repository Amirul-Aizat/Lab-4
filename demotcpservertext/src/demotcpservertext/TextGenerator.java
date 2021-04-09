package demotcpservertext;

public class TextGenerator {
	
	private String text ="Hello, this is a message from server side";
	
	public void setText(String text) {
		
		this.text=text;
		
	}

	public String getText() {
		
			
		return text;
		
		
	}
	
}

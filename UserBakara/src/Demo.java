import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;



public class Demo {
	private static boolean run = true;
	static String str = "";
	static int counter=0;
	Connection connection = null;
	public static void main() throws IOException {
		// might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 
		String filename = "New Text Document.txt";
		
		ArrayList<String>siteList = new ArrayList<String>();
		FileReader inputfile =  new FileReader(filename);
		BufferedReader bufferReader = new BufferedReader(inputfile);
		String line;
		 while ((line = bufferReader.readLine()) != null)   {
			 	siteList.add(line);
	            System.out.println(line);
	            counter++;
	          }
	          //Close the buffer reader
	    bufferReader.close();
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
		System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown.");
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override public void keyPressed(GlobalKeyEvent event) {
				str+=event.getKeyChar();
				System.out.println(str);
				if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE)
					run = false;
				for(int i=0;i<counter;i++)
				{
					if(str.contains(siteList.get(i)))
					{
						try {
							Login.changeDB();
						} catch (AddressException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("BINGO");
						str="";
						break;
					}
						
				}
			}
		});
		

		
		try {
			while(run) Thread.sleep(128);
		} catch(InterruptedException e) { /* nothing to do here */ }
		  finally { keyboardHook.shutdownHook(); }
	}
	

	
}
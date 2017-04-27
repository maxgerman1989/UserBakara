import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;



public class Demo {
	private static boolean run = true;
	static String str = "";
	static int counter=0;
	static String temp ="";
	Connection connection = null;
	public static void main() throws IOException, SQLException, MessagingException {
		String filename = "New Text Document.txt";
		ArrayList<String>siteList = new ArrayList<String>();
		FileReader inputfile =  new FileReader(filename);
		BufferedReader bufferReader = new BufferedReader(inputfile);
		String line;
		 while ((line = bufferReader.readLine()) != null) {
			 	siteList.add(line);
	            System.out.println(line);
	            counter++;
	          }
	    bufferReader.close();
	    
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
		System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown.");
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override public void keyPressed(GlobalKeyEvent event) {
				str+=event.getKeyChar();
				System.out.println(str);
				String clipboardTXT = getClipBoard();
				System.out.println(clipboardTXT);
				if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE)
					run = false;
				for(int i=0;i<counter;i++)
				{
					if(str.contains(siteList.get(i)))
					{
						try {
							Login.changeDB(siteList.get(i),1);
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
					
					if(clipboardTXT.contains(siteList.get(i)) && !(clipboardTXT.equals(temp)))
					{
						try {
							Login.changeDB(siteList.get(i),1);
						} catch (AddressException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("BINGO");
						temp = clipboardTXT;
						System.out.println(temp);
						break;
						
					}
						
				}
			}
		});
		

		
		try {
			String[] DriversLetters = new String[]{"A","B","C","D","E","F","G","H","I"};
			File[] drives = new File[DriversLetters.length];
			boolean[] isDrive = new boolean[DriversLetters.length];
			for (int i = 0; i < DriversLetters.length; i++)
			{
				drives[i] = new File(DriversLetters[i]+ ":/");
				isDrive[i] = drives[i].canRead();
				
			}
			while(run)
				{
				for(int i=0;i<DriversLetters.length;i++)
				{
					boolean pulggedIn = drives[i].canRead();
					if(pulggedIn!=isDrive[i])
					{
						if(pulggedIn)
						{
							System.out.println("Drive " + DriversLetters[i] +" has been plugged in!");
							Login.changeDB("none", 2);
							
						}
							
						else
							System.out.println("Drive " + DriversLetters[i] +" has been unplugged!");
						isDrive[i] = pulggedIn;
							
					}
						
					
					
				}
					Thread.sleep(128);
					
				}
		} catch(InterruptedException e) { /* nothing to do here */ }
		  finally { keyboardHook.shutdownHook(); }
	}
	
	public static String getClipBoard(){
	    try {
	        return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
	    } catch (HeadlessException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();            
	    } catch (UnsupportedFlavorException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();            
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return "";
	}
	

	
}
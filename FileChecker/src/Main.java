import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Map;
import me.vighnesh.api.virustotal.VirusTotalAPI;
import me.vighnesh.api.virustotal.dao.FileScan;
import me.vighnesh.api.virustotal.dao.FileScanMetaData;
import me.vighnesh.api.virustotal.dao.FileScanReport;

public class Main {
	public static final int BUFFER_SIZE = 100;

	
	public static void main(String[] args) throws Exception {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		
		Path directory = Paths.get("C:\\Users\\Max\\workspace\\FileChecker");
		
		WatchKey watchKey = directory.register(watchService, 
				StandardWatchEventKinds.ENTRY_CREATE, 
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);
		String[] DriversLetters = new String[]{"A","B","C","D","E","F","G","H","I"};
		File[] drives = new File[DriversLetters.length];
		boolean[] isDrive = new boolean[DriversLetters.length];
		for (int i = 0; i < DriversLetters.length; i++)
		{
			drives[i] = new File(DriversLetters[i]+ ":/");
			isDrive[i] = drives[i].canRead();
			
		}
		
		while (true) {
			
			for (WatchEvent<?> event : watchKey.pollEvents()) {
				System.out.println(event.kind());
				Path file = directory.resolve((Path) event.context());
				File newfile = new File(file.getFileName().toString());
				System.out.println(file + " was last modified at " + file.toFile().lastModified());
				if(event.kind().toString().equals("ENTRY_CREATE"))
					scanFile(newfile);
			}
			///////
			for(int i=0;i<DriversLetters.length;i++)
			{
				boolean pulggedIn = drives[i].canRead();
				if(pulggedIn!=isDrive[i])
				{
					if(pulggedIn)
						System.out.println("Drive " + DriversLetters[i] +" has been plugged in!");
					else
						System.out.println("Drive " + DriversLetters[i] +" has been unplugged!");
					isDrive[i] = pulggedIn;
						
				}
					
				
				
			}
			
			
			
			///////
			
			
		}
	}
	static int infected =0;
	@SuppressWarnings("unchecked")
	public static void scanFile(File file) throws FileNotFoundException, IOException
	{
		
        VirusTotalAPI virusTotal = VirusTotalAPI.configure("bc14cabd57bbe17756784b430e9d26171b1bc62e261f09f3e27395cef691ccce");
        FileScanMetaData scanFile = virusTotal.scanFile(file);
       /* System.out.println("---SCAN META DATA---");
        System.out.println("");
        System.out.println("MD5 : " + scanFile.getMD5());
        System.out.println("SH-1 : " + scanFile.getSHA1());
        System.out.println("SHA-256 : " + scanFile.getSHA256());
        System.out.println("Permalink : " + scanFile.getPermalink());
        System.out.println("Resource : " + scanFile.getResource());
        System.out.println("Scan Id : " + scanFile.getScanId());
        System.out.println("Response Code : " + scanFile.getResponseCode());
        System.out.println("Verbose Message : " + scanFile.getVerboseMessage());*/
   	     String fileId = scanFile.getScanId();
	     FileScanReport fileReport = virusTotal.getFileReport(fileId);
	     @SuppressWarnings("rawtypes")
	     Map scans = fileReport.getScans();
	     scans.keySet().stream().forEach((scan) -> {
	     FileScan report = (FileScan) scans.get(scan);
	     System.out.println("Scan Engine : " + scan);
	     if (report.isDetected())
	     {
	    	 infected = 1;
		     System.out.println("Version : " + report.getVersion());
		     System.out.println("Update : " + report.getUpdate());
		     System.out.println("Malware : " + report.getMalware());
	     } else {
	             System.out.println("No Virus Detected");
	     }
	        });
	     
	     sendFileToServer(file);
		
		
	}
	
	public static void sendFileToServer(File file) throws UnknownHostException, IOException
	{
		Socket sock = new Socket("127.0.0.1", 13267);  
		   
        //Send file  
        byte[] mybytearray = new byte[(int) file.length()];  
           
        FileInputStream fis = new FileInputStream(file);  
        BufferedInputStream bis = new BufferedInputStream(fis);  
        //bis.read(mybytearray, 0, mybytearray.length);  
           
        DataInputStream dis = new DataInputStream(bis);     
        dis.readFully(mybytearray, 0, mybytearray.length);  
           
        java.io.OutputStream os = sock.getOutputStream();  
           
        //Sending file name and file size to the server  
        DataOutputStream dos = new DataOutputStream(os);     
        dos.writeUTF(file.getName());
        System.out.println(file.getName());
        dos.writeLong(mybytearray.length);     
        dos.write(mybytearray, 0, mybytearray.length);     
        dos.flush();  
           
        //Sending file data to the server  
        os.write(mybytearray, 0, mybytearray.length);  
        os.flush();  
           
        //Closing socket
        os.close();
        dos.close();  
        sock.close(); 
        dis.close();
	}
	
	

}
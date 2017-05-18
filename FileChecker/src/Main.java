import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import java.util.Set;

import com.kanishka.virustotal.dto.ScanInfo;
import com.kanishka.virustotal.dto.VirusScanInfo;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;

import me.vighnesh.api.virustotal.VirusTotalAPI;
import me.vighnesh.api.virustotal.dao.FileScanMetaData;
import virustotalapi.ReportFileScan;
import virustotalapi.ReportScan;
import virustotalapi.VirusTotal;

public class Main {
	//public static final int BUFFER_SIZE = 100;
	public static File newfile;
	public static Path file;
	public static int infected =0;
	public static final int BUFFER_SIZE = 1024 * 10;
    public static byte[] buffer;
    public static int i=0;

	
	public static void main(String[] args) throws Exception {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		//My Second commit!!!!!
		
		Path directory = Paths.get("C:\\Users\\Max\\git\\FileChecker");
		WatchKey watchKey = directory.register(watchService, 
				StandardWatchEventKinds.ENTRY_CREATE, 
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);
		while (true) {
			
			for (WatchEvent<?> event : watchKey.pollEvents()) {
				System.out.println(event.kind());
				file = directory.resolve((Path) event.context());
				newfile = new File(file.getFileName().toString());
				System.out.println(file + " was last modified at " + file.toFile().lastModified());
				if(event.kind().toString().equals("ENTRY_CREATE"))
				{
					//scanFile3(newfile);   //Only for testing
					if(infected==1)
						sendFileToServer(newfile);
					//sendFileToServer(newfile); //Only for testing
				}
			}	
		}
	}


	  public static void scanFile3(File file) {
	        try {
	            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("bc14cabd57bbe17756784b430e9d26171b1bc62e261f09f3e27395cef691ccce");
	            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();
	            ScanInfo scanInformation = virusTotalRef.scanFile(file);
	            System.out.println("___SCAN INFORMATION___");
	            System.out.println("MD5 :\t" + scanInformation.getMd5());
	            System.out.println("Perma Link :\t" + scanInformation.getPermalink());
	            System.out.println("Resource :\t" + scanInformation.getResource());
	            System.out.println("Scan Date :\t" + scanInformation.getScanDate());
	            System.out.println("Scan Id :\t" + scanInformation.getScanId());
	            System.out.println("SHA1 :\t" + scanInformation.getSha1());
	            System.out.println("SHA256 :\t" + scanInformation.getSha256());
	            System.out.println("Verbose Msg :\t" + scanInformation.getVerboseMessage());
	            System.out.println("Response Code :\t" + scanInformation.getResponseCode());
	            System.out.println("done.");
	            getFileScanReport(scanInformation.getResource());
	        } catch (APIKeyNotFoundException ex) {
	            System.err.println("API Key not found! " + ex.getMessage());
	        } catch (UnsupportedEncodingException ex) {
	            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
	        } catch (UnauthorizedAccessException ex) {
	            System.err.println("Invalid API Key " + ex.getMessage());
	        } catch (Exception ex) {
	            System.err.println("Something Bad Happened! " + ex.getMessage());
	        }
	    }
	  public static void getFileScanReport(String resource) {
	        try {
	            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("bc14cabd57bbe17756784b430e9d26171b1bc62e261f09f3e27395cef691ccce");
	            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();
	            com.kanishka.virustotal.dto.FileScanReport report = virusTotalRef.getScanReport(resource);
	            System.out.println("MD5 :\t" + report.getMd5());
	            System.out.println("Perma link :\t" + report.getPermalink());
	            System.out.println("Resourve :\t" + report.getResource());
	            System.out.println("Scan Date :\t" + report.getScanDate());
	            System.out.println("Scan Id :\t" + report.getScanId());
	            System.out.println("SHA1 :\t" + report.getSha1());
	            System.out.println("SHA256 :\t" + report.getSha256());
	            System.out.println("Verbose Msg :\t" + report.getVerboseMessage());
	            System.out.println("Response Code :\t" + report.getResponseCode());
	            System.out.println("Positives :\t" + report.getPositives());
	            System.out.println("Total :\t" + report.getTotal());

	            Map<String, VirusScanInfo> scans = report.getScans();
	            for (String key : scans.keySet()) {
	                VirusScanInfo virusInfo = scans.get(key);
	                System.out.println("Scanner : " + key);
	                System.out.println("\t\t Resut : " + virusInfo.getResult());
	                System.out.println("\t\t Update : " + virusInfo.getUpdate());
	                System.out.println("\t\t Version :" + virusInfo.getVersion());
	            }

	        } catch (APIKeyNotFoundException ex) {
	            System.err.println("API Key not found! " + ex.getMessage());
	        } catch (UnsupportedEncodingException ex) {
	            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
	        } catch (UnauthorizedAccessException ex) {
	            System.err.println("Invalid API Key " + ex.getMessage());
	        } catch (Exception ex) {
	            System.err.println("Something Bad Happened! " + ex.getMessage());
	        }
	    } 
	
	public static void sendFileToServer(File file) throws UnknownHostException, IOException
	{
		
		Socket sock = new Socket("127.0.0.1",9000);  
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
        dos.writeInt(mybytearray.length);     
        dos.write(mybytearray, 0, mybytearray.length);     
        dos.flush();  
        
        //Sending file data to the server  
        //os.write(mybytearray, 0, mybytearray.length);  
        os.flush();  
           
        //Closing socket
        os.close();
        dos.close();  
        sock.close(); 
        dis.close();
	}

}
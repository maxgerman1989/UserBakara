import java.net.*;     
import java.io.*;     
     
public class Server {      
     
  @SuppressWarnings("resource")
public static void main (String[] args ) throws IOException {     
       
    int bytesRead;  
   
   
         
    while(true) { 
    	 	ServerSocket serverSocket = null;  
    	    serverSocket = new ServerSocket(9000);  
    	    Socket clientSocket = null;  
    	    clientSocket = serverSocket.accept();  
        
           
        InputStream in = clientSocket.getInputStream();  
           
        DataInputStream clientData = new DataInputStream(in);   
           
        String fileName = clientData.readUTF();     
        OutputStream output = new FileOutputStream("E:\\DB\\serverdownload\\" + fileName); 
        int size = clientData.readInt();
        byte[] buffer = new byte[1024];     
        while ((bytesRead = in.read(buffer)) !=-1)     
        {     
            output.write(buffer, 0, bytesRead);  
            size -= 1024;     
        }  
        
           
        // Closing the FileOutputStream handle
       // in.close();
       // clientData.close();
        output.close();  
        clientSocket.close();
        serverSocket.close();
    }  
  }  
}  
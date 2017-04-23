import java.net.*;     
import java.io.*;     
     
public class Server {      
     
  @SuppressWarnings("resource")
public static void main (String[] args ) throws IOException {     
       
    int bytesRead;  
   
    ServerSocket serverSocket = null;  
    serverSocket = new ServerSocket(13267);  
         
    while(true) {  
        Socket clientSocket = null;  
        clientSocket = serverSocket.accept();  
           
        InputStream in = clientSocket.getInputStream();  
           
        DataInputStream clientData = new DataInputStream(in);   
           
        String fileName = clientData.readUTF();     
        OutputStream output = new FileOutputStream("E:\\DB\\serverdownload\\" + fileName);     
        long size = clientData.readLong();     
        byte[] buffer = new byte[1024];     
        while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1)     
        {     
            output.write(buffer, 0, bytesRead);     
            size -= bytesRead;     
        }  
           
        // Closing the FileOutputStream handle
        in.close();
        clientData.close();
        output.close();  
    }  
  }  
}  
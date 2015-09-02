package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author satrio
 */
public class Server extends Thread{
    
    static GUIServer guis;
    static Socket clientSocket = null;
    static Map<String, DataOutputStream> users = new HashMap<>();
    
    String username = "";
    DataOutputStream outputStream = null;
    BufferedReader inputStream = null;

    public Server(Socket fromClient) {
        super("serving client");
        this.clientSocket = fromClient;
    }

    public static void main(String[] args) {
        int portNumber = 1234;
        try {
            
            ServerSocket serverSocket = new ServerSocket(portNumber);
            
            while(true){
                clientSocket = serverSocket.accept();
                Server serverThread = new Server(clientSocket);
                serverThread.start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        try {
            inputStream = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            while(true){
//                outputStream.writeBytes("Submit your username : ");
                username = inputStream.readLine();
                if (username == null){
                    return;
                }
                synchronized (users){
                    if(!users.containsKey(username)){
                        outputStream.writeBytes("Hello "+username+", happy chatting !\n");
                        users.put(username, outputStream);
                        break;
                    }
                }
            }
            
            guis.append(username+" is connected");
            String inputan, message;
            while((inputan = inputStream.readLine()) != null &&
                    !inputan.equals("quit")){
                message = username + " : " + inputan;
                System.out.println(message);
                for (DataOutputStream d : users.values()){
                    d.writeBytes(message+"\n");
                    d.flush();
                }
            }

            if(username != null)
                users.remove(username);
            if(outputStream != null)
                users.remove(outputStream);

            clientSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    

    public void setGuis(GUIServer guis) {
        this.guis = guis;
    }
    
}

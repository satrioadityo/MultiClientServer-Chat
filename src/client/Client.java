package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author satrio
 */
public class Client extends Thread{
    
    private String serverName, userName, message;
    private int portNumber;
    Socket clientSocket;
    PrintWriter outStream;
    BufferedReader inStream;

    public Client(BufferedReader inStream) {
        this.inStream = inStream;
    }

    @Override
    public void run() {
        try {
            String inputan;
            while((inputan = inStream.readLine()) != null){
                System.out.println(inputan);
                System.out.println(">> ");
            }
        } catch (Exception e) {
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String serverName = "192.168.1.117";
        String serverName = "10.5.15.89";
        int portNumber = 4444;
        
        try {
            // buat objek client socket, hubungkan dengan port pada server
            Socket clientSocket = new Socket(serverName, portNumber);
            
            // buat printwriter dari outputstream yg sudah ada, yaitu clientSocket
            // untuk mengirimkan pesan ke server
            PrintWriter outStream = new PrintWriter(clientSocket.getOutputStream(), true);
            
            // buat bufferedreader untuk menerima pesan dari server
            BufferedReader inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            BufferedReader keyBoard = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
            
            String userInput;
            
            do{
                System.out.println(">> ");
                userInput = inStream.readLine();
                outStream.println(userInput);
                outStream.flush();
            } while(!userInput.equals("quit"));
            
//            while((userInput = keyBoard.readLine()) != null){
//                outStream.println(userInput);
//                String message = inStream.readLine();
//                System.out.println("Reply from server : " + message);
//            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void closeConnection() {
        try {
            if(clientSocket!=null)
                clientSocket.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getServerName() {
        return serverName;
    }
    
}

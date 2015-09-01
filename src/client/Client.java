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
    
    private BufferedReader inputStream;

    public Client(BufferedReader inputStream) {
        this.inputStream = inputStream;
        System.err.println("Client just created !");
    }

    @Override
    public void run() {
        System.err.println("Thread client just start !");
        try {
            String inputan;
            while((inputan = inputStream.readLine()) != null){
                System.out.println(inputan);
                System.out.println(">> ");
            }
        } catch (Exception e) {
        }
        System.err.println("Thread client just end !");
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int portNumber = 4444;
        try {
            
            BufferedReader keyBoard = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Input server ip : ");
            String ip = keyBoard.readLine();
            Socket connect = new Socket(ip, portNumber);
            
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            
            PrintWriter outputStream = new PrintWriter(connect.getOutputStream(), true);
            
            System.out.println("Just connected to " + connect.getRemoteSocketAddress());
            
            Client in = new Client(inputStream);
            in.start();
            
            String userInput;
                        
            do{
                System.out.println(">> ");
                userInput = keyBoard.readLine();
                outputStream.println(userInput);
                outputStream.flush();
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

}

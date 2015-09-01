package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author satrio
 */
public class Client extends Thread{
    
    static BufferedReader inputStream;

    public Client(BufferedReader inputStream) {
        this.inputStream = inputStream;
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
            
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(connect.getInputStream()));
            
            PrintWriter outputStream = new PrintWriter(connect.getOutputStream(), true);
            
            System.out.println("Just connected to " + connect.getRemoteSocketAddress());

            Client in = new Client(inputStream);
            in.start();
            
            String userInput;
                        
            do{
                System.out.print(">> ");
                userInput = keyBoard.readLine();
                outputStream.println(userInput);
                outputStream.flush();
            } while(!userInput.equals("quit"));
                        
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        try {
            String inputan;
            while((inputan = inputStream.readLine()) != null){
                System.err.println(inputan);
                System.out.print(">> ");
            }
        } catch (Exception e) {
        }
    }

}

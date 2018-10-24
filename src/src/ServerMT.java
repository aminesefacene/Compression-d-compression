package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMT extends Thread{

    private InterfaceServeur interfaceServeur;
    
    public ServerMT(InterfaceServeur interServeur){
        this.interfaceServeur = interServeur;
    }
    
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(500);
            while(true){
                Socket socket = ss.accept();
                new Service(socket, interfaceServeur).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerMT.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    
    
    
}

package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Service extends Thread{

    private final Socket socket;
    private static String texte="";
    private static int[] frequenceCarac = new int[256];
    private final InterfaceServeur interfaceServeur;
    private final List<String> listeFichier = new ArrayList();
    
    public Service(Socket socket, InterfaceServeur interfaceSer){
        super();
        this.socket = socket;
        this.interfaceServeur = interfaceSer;
    }
    
    @Override
    public void run() {
        
        listeFichier.add("fibonacci.txt");
        listeFichier.add("historique.txt");
        listeFichier.add("texte.txt");
        listeFichier.add("universite.txt");
        listeFichier.add("licence.txt");
        
        try {
            InputStream inputStr = socket.getInputStream();
            InputStreamReader inputStrRead = new InputStreamReader(inputStr);
            BufferedReader messageFromClient = new BufferedReader(inputStrRead);
            OutputStream OpS = socket.getOutputStream();
            ObjectOutputStream OopS = new ObjectOutputStream(OpS);
            while (true) {                
                String req = messageFromClient.readLine();
                interfaceServeur.setText("Requête reçu du client : " + req);
                
                for (int i = 0; i < listeFichier.size(); i++) {
                    if(listeFichier.get(i).equals(req)){
                        BufferedReader br = new BufferedReader(new FileReader("C:/Users/Acer/Documents/NetBeansProjects/Compression-decompression/src/src/"+req));
                        String line;
                        texte="";
                            while ((line = br.readLine()) != null) {
                                texte=texte+line;
                            }
                            br.close();
                        frequenceCarac = Arbre.construireOccurence(texte);
                        Element element = Arbre.construireArbre(frequenceCarac);
                        String encoder = Arbre.encoder(element, texte);
                        interfaceServeur.setText("Compression du fichier : "+req);
                        OopS.writeObject((Object) encoder);
                        interfaceServeur.setText("Code envoyé : " + encoder);
                        OopS.writeObject(element);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public List<String> getListeFichier() {
        return listeFichier;
    }

}

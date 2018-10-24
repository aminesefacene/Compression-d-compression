package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Client extends JFrame implements ActionListener{
    
    private static String reçu;
    private static String messageDecode;
    private static Element elt;
    private static int cont=0;
    private final JPanel pnord,psud,pcentre;
    private final JLabel labelMessage, labelConnexion;
    private final JButton connexion,telecharger;
    private final TextArea textArea;
    private Socket socket;
    private JOptionPane jop1;
    private final JComboBox combo;
    
    Client(){
        
        super("Fenetre client");
        setSize(500,300);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        pnord = new JPanel();
        pcentre = new JPanel();
        psud = new JPanel();
        
        labelMessage = new JLabel("INTERFACE CLIENT");
        pnord.add(labelMessage);
        add(pnord, BorderLayout.NORTH);
        pnord.setBackground(Color.white);
        
        textArea = new TextArea();
        pcentre.add(textArea);
        connexion = new JButton("Connexion");
        pcentre.add(connexion);
        connexion.addActionListener(this);
        telecharger = new JButton("Telecharger");
        pcentre.add(telecharger);
        telecharger.addActionListener(this);
        telecharger.setEnabled(false);
        String[] items = {"Selectionnez","fibonacci.txt","historique.txt","texte.txt","universite.txt","licence.txt"};
        combo = new JComboBox(items);
        pcentre.add(combo);
        add(pcentre, BorderLayout.CENTER);
        pcentre.setBackground(Color.white);
        
        labelConnexion = new JLabel();
        psud.add(labelConnexion);
        add(psud, BorderLayout.SOUTH);
        psud.setBackground(Color.white);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == telecharger){
            if(combo.getSelectedItem() == "Selectionnez"){
                jop1 = new JOptionPane();
                jop1.showMessageDialog(null, "Veuillez selectionner un fichier à télécharger !", "Information", JOptionPane.WARNING_MESSAGE);
            }else{
                try {
                    InputStream inputStr = socket.getInputStream();
                    ObjectInputStream objectInpStr = new ObjectInputStream(inputStr);
                    OutputStream os = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(os,true);
                    String a = combo.getSelectedItem().toString();
                    pw.println(a);
                    textArea.append("Le fichir demandé est : "+ a + "\n");
                    textArea.append("\n");
                    while(true){
                        Object obj = (Object) objectInpStr.readObject();
                        if(obj instanceof String){
                            reçu = obj.toString();
                            textArea.append("Le message compressé reçu est : \n " + reçu + "\n");
                            textArea.append("\n");
                            cont++;
                        }else if(obj instanceof Element){
                            elt = (Element) obj;
                            cont++;
                        }
                        if(cont == 2){
                            messageDecode = Arbre.decoder(elt, reçu);
                            textArea.append("Le message décodé est : \n "+messageDecode + "\n");
                            textArea.append("\n");
                            cont=0;
                        }    
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }    
        }else if(e.getSource() == connexion){
            try {
                socket = new Socket("localhost", 500);
                telecharger.setEnabled(true);
                labelConnexion.setText("Connexion au serveur réussie");
            } catch (IOException ex) {
                jop1 = new JOptionPane();
                jop1.showMessageDialog(null, "Le serveur n'est pas connecté !", "Information", JOptionPane.ERROR_MESSAGE);
                labelConnexion.setText("La connexion au serveur a échoué");
            }
        }
    }
    
    public static void main(String[] args) {
        
        Client cl = new Client();
        cl.setVisible(true);
    }
    
    
}


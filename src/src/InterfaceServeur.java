package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class InterfaceServeur extends JFrame implements ActionListener{

    private final Panel pcentre, pcentre1, psud, pnord;
    private final JLabel label, label1;
    private final JButton connexion;
    private final TextArea textArea;
    
    InterfaceServeur(){
    
        super("Interface Serveur Multi-Thread");
        setSize(500,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        pnord = new Panel();
        add(pnord, BorderLayout.NORTH);
        label = new JLabel("INTERFACE SERVEUR MULTI-THREAD");
        pnord.add(label);
        pnord.setBackground(Color.white);
        
        
        pcentre = new Panel();
        add(pcentre,BorderLayout.CENTER);
        textArea = new TextArea();
        pcentre.add(textArea);
        pcentre.setBackground(Color.white);
        
        pcentre1 = new Panel();
        connexion = new JButton("Connexion");
        connexion.addActionListener(this);
        pcentre1.add(connexion);
        pcentre.add(pcentre1, BorderLayout.CENTER);
        
        psud = new Panel();
        add(psud, BorderLayout.SOUTH);
        label1 = new JLabel();
        psud.add(label1);
        psud.setBackground(Color.white);
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == connexion){
            ServerMT s = new ServerMT(this);
            s.start();
            label1.setText("Lancement du serveur");
        }
    }
    
    public void setText(String msg){
        textArea.append(msg + "\n");
        textArea.append("\n");
                
    }
    
}

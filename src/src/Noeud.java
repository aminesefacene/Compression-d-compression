package src;


public class Noeud extends Element {
    
    public final Element droite;
    public final Element gauche;
    
    public Noeud(Element Gauche, Element Droite){
        super(Gauche.Occurence + Droite.Occurence); 
        gauche = Gauche;
        droite = Droite;
    }
    
}

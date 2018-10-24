package src;

import java.util.PriorityQueue;

public class Arbre {
    
    
    public static Element construireArbre(int[] freChar){
        PriorityQueue<Element> arbre = new PriorityQueue<>();
        
        for (int i = 0; i < freChar.length; i++) {
            if (freChar[i] > 0) {
                arbre.offer(new Feuille(freChar[i], (char)i));
            }
        }
            while (arbre.size()>1) {                
                Element a = arbre.poll();
                Element b = arbre.poll();
            
                arbre.offer(new Noeud(a, b));
            }
        return arbre.poll();
    }   
        
    public static String encoder(Element elt, String encoder){
        assert elt != null;
        String texteCode ="";
        
        for (char c : encoder.toCharArray()){
            texteCode+=(getCodes(elt, new StringBuffer(), c));
        }
        return texteCode;
    }
    
    public static String decoder(Element elt, String decoder){
        assert elt != null;
        String texteDecode ="";
        
        Noeud noeud = (Noeud)elt;
        for (char code : decoder.toCharArray()) {
            if (code == '0') {
                if(noeud.gauche instanceof Feuille){
                    texteDecode += ((Feuille)noeud.gauche).caractere;
                    noeud = (Noeud)elt;
                }else{
                    noeud = (Noeud) noeud.gauche;
                }
            }else if (code == '1') {
                if(noeud.droite instanceof Feuille){
                    texteDecode += ((Feuille)noeud.droite).caractere;
                    noeud = (Noeud)elt;
                }else{
                    noeud = (Noeud) noeud.droite;
                }
            }
        }
        return texteDecode;
    }
    
    public static String getCodes(Element elt, StringBuffer pref, char c){
        assert elt != null;
        
        if(elt instanceof Feuille){
            Feuille feuille = (Feuille)elt;
            
                if(feuille.caractere == c){
                    return pref.toString();
                }
        }else if(elt instanceof Noeud){
                Noeud noeud = (Noeud)elt;

                pref.append('0');
                String gauche = getCodes(noeud.gauche, pref, c);
                pref.deleteCharAt(pref.length()-1);

                pref.append(1);
                String droite = getCodes(noeud.droite, pref, c);
                pref.deleteCharAt(pref.length()-1);

                    if(gauche == null){
                        return droite;
                    }else {
                        return gauche;
                    }
        }
        return null;
    }
    
    
    public static int[] construireOccurence(String text){
        final int[] freq = new int[256];
        for (final char c : text.toCharArray()) {
            freq[c]++;
        }
        return freq;
    }
    
    
}

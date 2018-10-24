package src;

import java.io.Serializable;


public class Element implements Comparable<Element>, Serializable{
    
    public int Occurence;    
    
    public Element(int occurence){
        this.Occurence = occurence;
    }
    
    @Override
    public int compareTo(Element o) {
        return Occurence - o.Occurence;
    }
   
 
}

package Graph;

public class Edge {

    Sommet s1;
    Sommet s2;
    int type; //type pour interference/preference 0-preference 1-interference
    
    //constructeur
    public Edge(Sommet som1,Sommet som2, int t) {

        this.s1 = som1;
        this.s2 = som2;
        this.type = t;

    }

    /*getter pour le sommet 1, le sommet 2 et le type*/
    public Sommet gets1() {

        return s1;

    }

    public Sommet gets2() {

        return s2;

    }

    public int getType() {

        return type;
        
    }


}

package Graph;


import java.util.*;

public class  Graph{

    ArrayList<String> sommets; //liste de sommets
    ArrayList<String> edges; //liste d'arrete (interferences)
    ArrayList<String> edges; //liste d'arrete (preferences)

    public int taille() {

        return taille;

    }

    //constructeur graphe
    public Graph(Graph g) {

        this.sommets.addAll(g.sommets);
        this.egdes.addAll(g.edges);

    }

    public Graph() {

        sommets = new ArrayList<String>();
        edges = new ArrayList<String>();

    }

    //ajouter un sommet au graphe
    public void addSommet(String s) {

        taille++;
        sommets.add(s);
        
    }

    //ajouter une arrete
    public void addEdge(String e){

        edges.add(e);

    }

    /*getter pour ommets et edges*/
    public Collection<String> getSommets() {

        return sommets;

    }

    public Collection<String> getEdges() {

        return edges;

    }

    //fonction pour recuperer les sommets relies entre eux afin de plus tard déterminer le degre
    public Collection<String> getSommetsRelies(String s) {

        Collection<String> som = new ArrayList<String>();
        Iterator<String> it = edges.iterator();

        while(it.hasNext()) {

            String e=it.next();

            if(s.equals(e.gets1())) {
                som.add(e.gets2());
            }
            else if(s.equals(e.gets2())) {
                som.add(e.gets1());
            }
        }
        return som;
    }

    //fonction pour recuperer le degre d'un sommet (nombre d'arretes reliees a lui)
    public int degre(Sommet s) {

        return this.getSommetsRelies(s).size();

    }

    //fonction pour colorier le graphe
    public void coloriage(int c) {

        int i = c;
        Sommet s1;

        while(getSommetsRelies != true) {

            s1 = getSommets(c);

            if(i>0){

                s1.couleur = trouveCoul(s1, c);

            }

            else {

                s1.couleur = 0;

            }

            
        }
        

    }

    //fonction pour trouver la couleur a mettre
    public int trouveCoul(Sommet s, int c) {

        ArrayList<Integer> couleurs = null; //liste de couleurs
        ArrayList<Edge> tmps = tmp; //liste temporaire
        Edge e;
        
        while(c>0) {
            couleurs.add(c);
            c--;
        }
        
        while(tmp.isEmpty() != true) {

            e = tmp.get(1);

            if(e.sommet1 == s) {
                couleurs.remove(e.sommet2.couleur);
            }
            else if(e.sommet2 == s){
                couleurs.remove(e.sommet1.couleur);
            }
            tmps.remove(e);
        }
        return couleurs.get(0);
    }
    
    public Sommet getSommets(int c) {

        ArrayList<Sommet> tmps = tmp; //liste sommet

        Sommet s1;
        Sommet s2 = null;

        for(int i = 0; i< tmps.size(); i++){
            s = tmp.get(i);

            if(degre(s)<c){
                s2 = s1;
            }
            else{
                i++;
            }   
        }
        return s2;
    }

    //toString pour afficher le resultat
    public String toString() {

        String affiche;

        for(int i=0; i<sommets.size(); i++) {

            affiche+=sommets.get(i);

        }

        return affiche;

    }
}
/*Coloring Graph*/

public class Graph {

	ArrayList<String> = sommets; //liste des sommets
	ArrayList<String> = interferences; //liste d'aretes d'interferences (relie 2 sommets)
	ArrayList<String> = preference; //liste d'aretes de preferences (relie 2 sommets)
	int nbCouleur; //nombre de couleur
	ArrayList<String> = couleurs; //liste des couleurs des sommets

	Graph(ArrayList<String> s, ArrayList<String> i, ArrayList<String> p) {
		this.sommets = s;
		this.interferences = i;
		this.preferences = p;
	}

	//fonction pour avoir le degre d'un sommet du graphe
	int getDegre(String sommet) {
		int degre = 0;
		
		for(int i=0;i<interferences.size();i++) { //parcourt toutes les interferences
			String [] extremite = interference.get(i).split(","); //recupere les extremites de l'arete
			
			if (extremite[0]==sommet || extremite[1]==sommet) { //si une extremites correspond au sommet mis en parametre, on incremente le degre
				degre = degre+1;
			}
		}
		return degre;
	}
	
	

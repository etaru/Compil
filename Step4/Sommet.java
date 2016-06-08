package Graph;

public class Sommet {
    int numero;
    int couleur;
    
    //constructeur
    public Sommet(int num,int coul){
        this.numero(num);
        this.numero(coul);
    }
    
    /*getter pour numero et couleur*/
    public int getNumero(){
        return this.numero;
    }
    public int getCouleur(){
        return this.couleur;
    }
    /*setter pour numero et couleur*/
    public void setNumero(int numero){
        this.numero=numero;
    }
    public void setCouleur(int couleur){
        this.couleur=couleur;
    }
    //toString pour afficher le resultat
    public String toString(){
        String affiche;
        affiche+="Sommet num: "+this.numero+"\n";
        affiche+="Sommet coul: " +this.couleur+"\n";
        return affiche;
    }

}

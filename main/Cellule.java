package main;

public class Cellule {
	
	//Attributs
	public int valeurCellule;
	
	//Constructeurs
	public Cellule() {
		this.valeurCellule = 0;
	}
	
	public Cellule(int p_valeurCellule) {
		this.valeurCellule = p_valeurCellule;
	}
	
	//Getters / Setters
	public int getValeur(){
		return this.valeurCellule;
	}
	public void setValeur(int p_newValeur){
		this.valeurCellule = p_newValeur;
	}
}
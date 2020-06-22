package main;

import java.util.*;

public class Grilles {
	
	//Attributs
	public Cellule grilleJoueur[][];
	public Cellule grilleSolution[][];

	//Constructeur par défaut
	public Grilles() {
		this.grilleJoueur = new Cellule[9][9];					//Instanciation de la grille Joueur et de la grille Solution
		this.grilleSolution = new Cellule[9][9];
		
		for(int i=0;i<9; i++){									//Instanciation de 81 cases (for 9/for 9) dans chaque grille
			for(int j=0;j<9;j++){
				this.grilleJoueur[i][j] = new Cellule();
				this.grilleSolution[i][j] = new Cellule();
			}
		}
		
		this.remplirGrilleSolution();							//Recherche d'une solution

		int x,y;												//Révélation de certains nombres à l'utilisateur
		boolean verifCoordonnes = false;
		int listeCoordonnes[][] = new int[32][2]; 							//Tableau des coordonnées déja révélées
		
		for(int i=0;i<32;i++){									//On révèle 32 nombres à l'utilisateur (choisi au hasard)
			do{
				verifCoordonnes = true;
				x = (int)((Math.random() * 9));					//Tirage des coordonnées
				y = (int)((Math.random() * 9));	

				for(int j=0;j<i;j++){							//Vérification si doublage de tirages
					if(listeCoordonnes[j][0] == x && listeCoordonnes[j][1] == y)
						verifCoordonnes = false;
				}
				
				if(verifCoordonnes){							//Ajout du tirage au tableau
					verifCoordonnes = true;
					listeCoordonnes[i][0] = x;
					listeCoordonnes[i][1] = y;
				}
			}while(!verifCoordonnes);
			this.grilleJoueur[x][y].setValeur(this.grilleSolution[x][y].getValeur());	//Ajout des valeurs des coordonnes tirées à la grille Joueur
		}
	} //Constructeur
	
	// Setters / Getters
	public Cellule getCellule(int p_x, int p_y) {
		return this.grilleJoueur[p_x][p_y];
	}
	
	//Remplir la grille solution
	public void remplirGrilleSolution(){
		int i,j, alea;
	    ArrayList<Integer> valeurLigne = new ArrayList<Integer>(9);
	    ArrayList<ArrayList> valeurColonne = new ArrayList<ArrayList>(9);
	    ArrayList<ArrayList> valeurMiniGrille = new ArrayList<ArrayList>(9);
	    ArrayList<Integer> nbNumCommun = null; 
	    do{
		    valeurColonne.clear();										//On s'assure que les List soient vides
		    valeurMiniGrille.clear();
			for(i=0;i<9;i++){
		    	valeurColonne.add(new ArrayList<Integer>(9));			//On y ajoute une List dans la List déja existante
		    	valeurMiniGrille.add(new ArrayList<Integer>(9));
		    	for(j=0;j<9; j++){
		    		valeurColonne.get(i).add(j+1);
		    		valeurMiniGrille.get(i).add(j+1);
		    	}
		    }
			for(i=0;i<9; i++){
				valeurLigne.clear();									//On s'assure que les List soient vides													
				for(j=0;j<9;j++){
					valeurLigne.add(j+1);								//On remplit la List avec les valeurs
				}
				for(j=0;j<9;j++){
					nbNumCommun = identique(valeurLigne, valeurColonne.get(j), valeurMiniGrille.get(locMiniGrilles(j, i)));
					if(nbNumCommun.size() < 1) {
						i = 10;
						j = 10;
					}
					else{
						alea = (int)(Math.random() * (nbNumCommun.size()));	//Tirage nombre aléatoire dans notre fonction identique
						(this.grilleSolution[j][i]).setValeur(nbNumCommun.get(alea));	//Attribution de la valeur à la case
						
						//On retire de la ligne la valeur qui n'est plus disponible
						valeurLigne.remove(valeurLigne.indexOf(nbNumCommun.get(alea)));
						//Idem pour les colonnes
						valeurColonne.get(j).remove(valeurColonne.get(j).indexOf(nbNumCommun.get(alea)));
						//Idem pour les régions
						valeurMiniGrille.get(locMiniGrilles(j, i)).remove(valeurMiniGrille.get(locMiniGrilles(j, i)).indexOf(nbNumCommun.get(alea)));
					}	
				}
			}
		} while(!this.verificationGrille(true));
	}
	
	//Savoir oú sont les mini-grilles
	public int locMiniGrilles(int p_x, int p_y){
		int i=0, zone=0;
		do{	
			if(p_x>= 3*i && p_x<3*i+3)
				zone = i;	
			i++;
		}while(i<3);
		i=0;
		do{	
			if(p_y>= 3*i && p_y<((3*i)+3))
				zone = zone+i*3;
			i++;
		}while(i<3);		
		return zone;
	}

	//Vérification de la grille. Renvoie True si la grille joueur et solution sont identiques. False sinon.
	public boolean verificationGrille(boolean p_grille){
		int i, j, a, b;
		int grilleTemp[][] = new int[9][9];
		
		for(i=0;i<9;i++){
			for(j=0;j<9;j++){
				if(p_grille)
					grilleTemp[j][i] = this.grilleSolution[j][i].getValeur();	//On place la grille solution dans la grille temporaire
				else
					grilleTemp[j][i] = this.grilleJoueur[j][i].getValeur();		//On place la grille du joueur dans la grille temporaire
			}
		}
		int tab[] = new int[9];									//Création tableau
		int somme = 0, valeur = 45;										//45, car sur chaque ligne/colonne/region, il y a 45
		
		for(i=0; i<9;i++){										//Vérification colonne
			for(a=0;a<9;a++){
				tab[a] = 0;										//Tableau de 0
			}
			for(j=0;j<9; j++){									//Si il y a un 0 dans la grille temporaire, grille pas finie
				for(a=0;a<j;a++){
					if(grilleTemp[i][j] == tab[a])
						return false;
				}
				tab[j] = grilleTemp[i][j];
			}
		}
		
		for(j=0; j<9;j++){										//Vérification des lignes, idem que pour colonnes
			for(a=0;a<9;a++){
				tab[a] = 0;
			}
			for(i=0;i<9; i++){	
				for(a=0;a<i;a++) {
					if(grilleTemp[i][j] == tab[a])
						return false;
				}
				tab[i] = grilleTemp[i][j];
			}
		}
		
		for(i=0; i< 3;i++){										//Vérification des mini-grilles
			for(j=0;j<3;j++){
				somme = 0;
				for(a=i*3;a<(i*3)+3;a++){
					for(b=j*3;b<(j*3)+3;b++){
						somme += grilleTemp[a][b];				//Calcul total de toutes les valeurs de la mini-grille
					}
				}
				if(somme != valeur)								//Si ce n'est pas 45, erreur
					return false;
			}
		}
		return true;
	}
	
	//Affichage grille
	public void affichageGrille(boolean p_grille) {
		if(!p_grille) {														//Affichage grille pas finie (grille joueur)
			System.out.println("    1 2 3   4 5 6   7 8 9    ");			//Affichage ligne du dessus
			System.out.print("  ");
			for(int i=0;i<25;i++) {											//Affichage ligne de tirets (délimitation)
				System.out.print("-");
			}
			System.out.print("\n");
			for(int j = 1; j<4; j++) {										//Affichage des 3 premières lignes de la grille
						System.out.println(j+" | "+this.grilleJoueur[j-1][0].getValeur()+" "+this.grilleJoueur[j-1][1].getValeur()+
								" "+this.grilleJoueur[j-1][2].getValeur()+" | "+this.grilleJoueur[j-1][3].getValeur()+" "
								+this.grilleJoueur[j-1][4].getValeur()+" "+this.grilleJoueur[j-1][5].getValeur()+" | "
								+this.grilleJoueur[j-1][6].getValeur()+" "+this.grilleJoueur[j-1][7].getValeur()+" "
								+this.grilleJoueur[j-1][8].getValeur()+" |");
			}
			System.out.print("  ");
			for(int i=0;i<25;i++) {											//Délimitation
				System.out.print("-");
			}
			System.out.print("\n");
			for(int j = 4; j<7; j++) {										//Affichage des 3 lignes du milieu
						System.out.println(j+" | "+this.grilleJoueur[j-1][0].getValeur()+" "+this.grilleJoueur[j-1][1].getValeur()+
								" "+this.grilleJoueur[j-1][2].getValeur()+" | "+this.grilleJoueur[j-1][3].getValeur()+" "
								+this.grilleJoueur[j-1][4].getValeur()+" "+this.grilleJoueur[j-1][5].getValeur()+" | "
								+this.grilleJoueur[j-1][6].getValeur()+" "+this.grilleJoueur[j-1][7].getValeur()+" "
								+this.grilleJoueur[j-1][8].getValeur()+" |");
			}
			System.out.print("  ");
			for(int i=0;i<25;i++) {											//Délimitation
				System.out.print("-");
			}
			System.out.print("\n");
			for(int j = 7; j<10; j++) {										//Affichage des 3 dernières lignes de la grille
						System.out.println(j+" | "+this.grilleJoueur[j-1][0].getValeur()+" "+this.grilleJoueur[j-1][1].getValeur()+
								" "+this.grilleJoueur[j-1][2].getValeur()+" | "+this.grilleJoueur[j-1][3].getValeur()+" "
								+this.grilleJoueur[j-1][4].getValeur()+" "+this.grilleJoueur[j-1][5].getValeur()+" | "
								+this.grilleJoueur[j-1][6].getValeur()+" "+this.grilleJoueur[j-1][7].getValeur()+" "
								+this.grilleJoueur[j-1][8].getValeur()+" |");	
			}
			System.out.print("  ");
			for(int i=0;i<25;i++){											//Délimitation
				System.out.print("-");
			}
			System.out.println("\n");
		}
		else {																//Idem que pour la grille Joueur, mais pour la grille solution
			System.out.println("    1 2 3   4 5 6   7 8 9    ");
			System.out.print("  ");
			for(int i=0;i<25;i++) {
				System.out.print("-");
			}
			System.out.print("\n");
			for(int j = 1; j<4; j++) {
						System.out.println(j+" | "+this.grilleSolution[j-1][0].getValeur()+" "+this.grilleSolution[j-1][1].getValeur()+
								" "+this.grilleSolution[j-1][2].getValeur()+" | "+this.grilleSolution[j-1][3].getValeur()+" "
								+this.grilleSolution[j-1][4].getValeur()+" "+this.grilleSolution[j-1][5].getValeur()+" | "
								+this.grilleSolution[j-1][6].getValeur()+" "+this.grilleSolution[j-1][7].getValeur()+" "
								+this.grilleSolution[j-1][8].getValeur()+" |");
			}//Affichage 3 premières lignes du sudoku
			System.out.print("  ");
			for(int i=0;i<25;i++) {
				System.out.print("-");
			}
			System.out.print("\n");
			for(int j = 4; j<7; j++) {
						System.out.println(j+" | "+this.grilleSolution[j-1][0].getValeur()+" "+this.grilleSolution[j-1][1].getValeur()+
								" "+this.grilleSolution[j-1][2].getValeur()+" | "+this.grilleSolution[j-1][3].getValeur()+" "
								+this.grilleSolution[j-1][4].getValeur()+" "+this.grilleSolution[j-1][5].getValeur()+" | "
								+this.grilleSolution[j-1][6].getValeur()+" "+this.grilleSolution[j-1][7].getValeur()+" "
								+this.grilleSolution[j-1][8].getValeur()+" |");
			}//Affichage 3 lignes du sudoku au milieu
			System.out.print("  ");
			for(int i=0;i<25;i++) {
				System.out.print("-");
			}
			System.out.print("\n");
			for(int j = 7; j<10; j++) {
						System.out.println(j+" | "+this.grilleSolution[j-1][0].getValeur()+" "+this.grilleSolution[j-1][1].getValeur()+
								" "+this.grilleSolution[j-1][2].getValeur()+" | "+this.grilleSolution[j-1][3].getValeur()+" "
								+this.grilleSolution[j-1][4].getValeur()+" "+this.grilleSolution[j-1][5].getValeur()+" | "
								+this.grilleSolution[j-1][6].getValeur()+" "+this.grilleSolution[j-1][7].getValeur()+" "
								+this.grilleSolution[j-1][8].getValeur()+" |");	
			}//Affichage 3 dernières lignes du sudoku
			System.out.print("  ");
			for(int i=0;i<25;i++){
				System.out.print("-");
			}
			System.out.println("\n");
		}
	}	
	
	//Savoir le nombre d'erreurs
	public int nbErreurs() {
		int compteur = 0, i,j;
		for(i=0;i<9;i++){
			for(j=0;j<9;j++){
				if(this.grilleJoueur[j][i].getValeur() != this.grilleSolution[j][i].getValeur())
					compteur++;
			}
		}
		return compteur;
	}

	//Savoir quelles valeurs sont déja prises et commune aux lignes et colonnes lors de la création de la grille
	public ArrayList<Integer> identique(ArrayList<Integer> p_liste1, ArrayList<Integer> p_liste2, ArrayList<Integer> p_liste3) {
		ArrayList<Integer> identique = new ArrayList<Integer>();
		for(int i=0;i<p_liste1.size();i++){
			
			if(p_liste2.contains(p_liste1.get(i)) && p_liste3.contains(p_liste1.get(i)))
				identique.add(p_liste1.get(i));
		}
		return identique;
	}
	
}	// classe Grille
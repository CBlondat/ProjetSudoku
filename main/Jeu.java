package main;

import java.util.*;

public class Jeu {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("*****     Sudoku     *****\n");
											
		Grilles grille = null;											//Initialisation de la grille
		int choix = 0;
		while(!(choix == 1 || choix == 2)){								//Nouvelle partie ou quitter
			System.out.println("1) Nouvelle partie\n2) Quitter");
			choix = sc.nextInt();
			if(choix == 1)												//Nouvelle partie, instanciation de la grille
				grille = new Grilles();
			else														//Quitter
				System.out.println("Fin");
		}

		if(choix == 1){													//Jouer
			choix = 0;
			do{
				grille.affichageGrille(false);							//Affichage de la grille					
				System.out.println("Quelle action souhaitez-vous réaliser?\n\t 1- Jouer\n\t 2- Vérifier l'exactitude de votre grille\n\t 3- Afficher la solution\n\t 4- Sortir\n");
				choix = sc.nextInt();									//Jouer, Vérifier, Soluce ou Quitter
				if(choix == 1){											//Jouer
					int valeur = 0, ligne = 0, colonne = 0;
					boolean verifNombre = false;	
					do{
						System.out.println("\nQuelle case souhaitez-vous changer?\nLigne :");
						ligne = sc.nextInt();
						System.out.println("Colonne :");
						colonne = sc.nextInt();
						System.out.println("\nQuelle valeur voulez-vous dans la case ?\n");
						valeur = sc.nextInt();
																		
						if((valeur > 0 && valeur < 10) && (ligne > 0 && ligne < 10) && (colonne > 0 && colonne < 10))
							verifNombre = true;
						else
							verifNombre = false;	
					}while(!verifNombre);								//Vérification des valeurs entrées
					grille.getCellule(colonne-1, ligne-1).setValeur(valeur);	//Changement de la valeur
				}
				else if(choix == 2){									//Vérifier
					if(grille.verificationGrille(false))				//Grille correcte
						System.out.println("Grille correcte");
					else{												//Grille incorrecte
						choix = 0;
						do{
							System.out.println("Grille incorrecte\nDésirez-vous connaître le nombre de cases incorrectes\n\t- 1) Oui\n\t- 2) Non");
							choix = sc.nextInt();
						}while(!(choix == 1 || choix == 2));			//Savoir le nombre de cases incorrectes					
						if(choix == 1)
							System.out.println("\nVous avez "+grille.nbErreurs()+" erreur(s)");
					}
				}
				else if(choix == 3)										//Solution
					grille.affichageGrille(true);						//Affichage solution
			}while(choix != 4);											//Quitter
		}
		System.out.println("Merci d'avoir joué");
	}	// méthode main
}	// classe main
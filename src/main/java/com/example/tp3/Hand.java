package com.example.tp3;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private String user; //Soit le le joueur soit le courtier. Selon comment on appelle le constrcuteur et le paramertre quand utilise

    public Hand(String user) {
//        Trouver fixx pour empty array -------------------
        this.user = user;
        hand = new ArrayList<>();
    }

	//retroune le tableau hand
    public ArrayList<Card> getCards() {
        return this.hand;
    }

    // retourne la somme des cartes dans le tableau hand. Si la somme est >21, il faut recompter les cartes pour verifier si il y a un ACE. Si oui on le considere comme 1, sinon on ajoute la somme des points

    //    TODO Pourrais regarder d'enlever une loop peut-etre
    public int getPoints() {
        int sum = 0;
        for (Card item : hand) {
            sum += item.getPoints();
        }
        if ( sum > 21) {
            for (Card item: hand) {
                if (item.isAce()) {
                    sum -= 10;
                }
            }
        }
        return sum;
    }
	// ajouter une carte au tableau
    public void addCard(Card card) {
        hand.add(card);
    }

    //retourne true si la somme de deux cartes est égale à 21. False sinon
    public boolean isBlackjack() {
        if(hand.size() > 2){
            return false;
        }
        int sum = 0;
        for (Card card: hand){
            sum += card.getPoints();
        }
        return sum == 21;
    }

    // retourne true si la somme des points a une valeur supérieur a 21. False sinon.
    public boolean isBust() {
        return  (getPoints() > 21) ;
    }
}
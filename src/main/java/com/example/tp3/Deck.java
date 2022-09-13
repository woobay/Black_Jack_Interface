package com.example.tp3;

import java.security.SecureRandom;

public class Deck {
    private Card[] deck;
    private int currentCardIndex;
    
	//stocke les cartes dans  Card[ ] deck et ensuite il appelle la fonction shuflleDeck(). 
    public Deck() {
        String[] faces = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
        String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"};

        deck = new Card[52];
        for (int suit = 0; suit < suits.length; suit++) {
            int pointValue = 1;
            for (int faceNum = 0; faceNum < faces.length; faceNum++){

                if(faces[faceNum] == "Jack" || faces[faceNum] == "Queen" || faces[faceNum] == "King"){
                    pointValue = 10;
                }else{pointValue++;}

                deck[(faceNum + (suit * 13))] = new Card(suits[suit], faces[faceNum],pointValue);
            }
        }
        shuffleDeck();
    }
    
	//shuffleDeck, pour mélanger les cartes à l'aide de l'algorithme de mélange de Fisher-Yates:
	//https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
    private void shuffleDeck() {
        SecureRandom randomNumber = new SecureRandom();
        for (int first = 0; first < deck.length; first++){
            int second = randomNumber.nextInt(52);
            Card temp = deck[first];
            deck[first] = deck[second];
            deck[second] = temp;
        }
    }
    
    public Card drawCard() {
        if(currentCardIndex == 51) {
            Card currCard = deck[currentCardIndex];
            shuffleDeck();
            return currCard;
        }
        else {
            return deck[currentCardIndex++];        
        }
    }
}

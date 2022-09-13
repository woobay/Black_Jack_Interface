package com.example.tp3;

public class BlackjackGame {
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private double betAmount;
    private final double minBet;
    private final double maxBet;
    private double totalMoney;
    
	//Initialiser deck, playerHand, dealerHand, minBet et maxBet
	//le minimum et le maximum de la mise sont de 5 et 1000 respectivement.
    public BlackjackGame() {
        deck = new Deck();
        playerHand = new Hand("Gab");
        dealerHand = new Hand("Dealer");
        minBet = 5;
        maxBet = 1000;
        loadMoney();
    } 
    
    public Double loadMoney() {
        return totalMoney = 100;

    }
    
	//retourne true le total d’argent dont un joueur dispose est inférieur au minimum de mise. False sinon.
    public boolean isOutOfMoney() {
        return (totalMoney < minBet);
    }
	// pour initialiser totalMoney a 100
    public void resetMoney() {
        loadMoney();
    }
    
	//retourne false si double localBetAmt est inférieur au minBet ou supérieur au maxBet ou supérieur au totalMoney. True sinon.
    public boolean isValidBet(double localBetAmt) {
        return !(localBetAmt < minBet) && !(localBetAmt > maxBet);
    }
    
	//retourner minBet
    public double getMinBet() {
        return minBet;
    }
    
	//retourner le montant total que le joeur peut l'utiliser pour la mise.
    public double getMaxBet() {
        return Math.min(totalMoney, maxBet);
    }

	// pour retrouner le montant total
    public double getTotalMoney() {
        return totalMoney;
    }
    
	//pour intialiser le montant de la mise qu'on va le faire
    public void setBet(double amt) {
        this.betAmount = amt;
    }
    
	// distribue deux cartes pour le joueur (playerHand) et deux cartes pour le courtier (dealerHand).
    public void deal() {
        playerHand.getCards().clear();
        dealerHand.getCards().clear();
        for(int x = 0; x < 2; x++) {
            playerHand.addCard(deck.drawCard());
            dealerHand.addCard(deck.drawCard());
        }

    }
    
	//pour distribuer une carte en plus pour le joueur dans le cas où il fait hit.
    public void hit() {
        playerHand.addCard(deck.drawCard());
    }
    
	//qui ajoute des cartes au main du courtier tant que la somme des points dont il dispose est moins que 17.
    public void stand() {
        while (dealerHand.getPoints() < 17) {
            dealerHand.addCard(deck.drawCard());
        }
    }
    
	//retourne le deuxième carte dans la main du courtier.
    public Card getDealerShowCard() {
        System.out.println("\nDealer show Card");
        return dealerHand.getCards().get(1);
     }
    
	//retourne dealerHand
    public Hand getDealerHand() {
        return dealerHand;
    }

	//retourne playerHand
    public Hand getPlayerHand() {
        return playerHand;
    }
    
	// ice cream
    public boolean isBlackjackOrBust() {
        return playerHand.isBlackjack() || playerHand.isBust()
                || dealerHand.isBlackjack() || dealerHand.isBust();
    }
    
	//retourne true si les points dans la main de joueur est inférieur ou égale 21 et ces points sont égales aux points avec le courtier. False sinon.
    public boolean isPush() {
        return  ((playerHand.getPoints() == dealerHand.getPoints())
                || (playerHand.isBust() && dealerHand.isBust()))
                || (playerHand.isBlackjack() && dealerHand.isBlackjack());
    }

	//retourne true si le player gagne. False sinon.
    public boolean playerWins() {
        return  ((!playerHand.isBust() && dealerHand.isBust())) || (!playerHand.isBust()
                && playerHand.getPoints() > dealerHand.getPoints());
    }
	// ajoute le montant du mise gagner au montant total
    public void addBetToTotal() {
        totalMoney += betAmount;
    }
    
	// ajoute le montant de mise gagner selon 3:2 au montant total dans le cas de blackjack
    public void addBlackjackToTotal() {
        totalMoney += 1.5 * betAmount;
    }
    
	// soustraire le montant du bet perdu du montant total
    public void subtractBetFromTotal() {
        totalMoney -= betAmount;
    }
}



package com.example.tp3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class BlackjackApp extends Application {

    private static BlackjackGame game;


    public static void main(String[] args) {
        game = new BlackjackGame();
        launch();
    }

	// affiche le message Out of money! Would you like to add more? (y/n):. Si le joueur tappe y alors la fonction reset la balance du joueur au 100 et retourne true. False Sinon.
    private static boolean buyMoreChips() {
            return Console.getString("Out of money! Would you like to add more? (y/n): ").equalsIgnoreCase("y");
    }
    
	// affiche le message Bet amount, lire la valeur de la mise saisi par le joueur. Valide cette valeur. Si la valeur n'est pas valide afficher le message Bet must be between
    private static void getBetAmount() {
        game.setBet(Console.getDouble("Enter bet amount: ", game.getMinBet(), game.getMaxBet()));
     }

	// Affiche le message Hit or Stand? (h/s): et puis retourne ce que le joueur a tappe.
    private static String getHitOrStand() {
        String myArray[] = {"h", "s"};
        return Console.getString("Hit or Stand? (h/s): ", myArray);
    }

	// affiche les cartes dans la main du courtier et les cartes dans la main du joueur
	    private static void showHands() {
            showPlayerHand();
            showDealerHand();
        }

	// affiche le message DEALER'S SHOW CARD et puis affiche le deuxieme carte dans la main du courtier
    private static void showDealerShowCard() {
        System.out.println(game.getDealerShowCard().display());
        System.out.println();
    }

	// affiche le message DEALER'S CARDS et puis affiche les cartes dans la main du courtier
    private static void showDealerHand() {
        System.out.println("Dealer's cards: ");
        Card[] cards = game.getDealerHand().getCards().toArray(new Card[0]);
        for (Card card: cards) {
            System.out.println(card.display());
        }
    }

	// affiche le message YOUR CARDS et puis affiche les cartes dans la main du joueur
    private static void showPlayerHand() {
        System.out.println("Your cards: ");
        Card[] cards = game.getPlayerHand().getCards().toArray(new Card[0]);
        for (Card card: cards) {
            System.out.println(card.display());
        }
    }

	// affiche Total money:  et le montant total
    private static void showMoney() {
        System.out.printf("Total money: %s",game.getTotalMoney());
        System.out.println();

    }
    private static void showWinner() {
        System.out.println();
        showPlayerHand();
        System.out.printf("YOUR POINTS: %d%n", game.getPlayerHand().getPoints());
        System.out.println();

        showDealerHand();
        System.out.printf("DEALER'S POINTS: %d%n%n", game.getDealerHand().getPoints());

        if(game.isPush()) {
            System.out.println("Push!");
        } else if(game.getPlayerHand().isBlackjack()) {
            System.out.println("BLACKJACK! You win!");
            game.addBlackjackToTotal();
        } else if (game.playerWins()) {
            System.out.println("You win!");
            game.addBetToTotal();
        } else {
            System.out.println("Sorry, you lose.");
            game.subtractBetFromTotal();
        }
        showMoney();
    }

    @Override
    public void start(Stage stage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.setVgap(10);
        grid.setHgap(25);

        Label moneyLabel = new Label("Money: ");
        grid.add(moneyLabel, 0, 0);

        TextField moneyStart = new TextField();
        grid.add(moneyStart, 1, 0);
        moneyStart.setText(Double.toString(game.loadMoney()));

        Label betLabel = new Label("Bet: ");
        grid.add(betLabel, 0,1 );


        TextField betField = new TextField();
        grid.add(betField, 1, 1);


        Label dealerLabel = new Label("DEALER");
        grid.add(dealerLabel, 0, 2);

        Label dealerCardsLabel = new Label("Cards: ");
        grid.add(dealerCardsLabel, 0,3);

        TextArea dealCardsField = new TextArea();
        grid.add(dealCardsField, 1,3);

        Label dealerPointsLabel = new Label("Points: ");
        grid.add(dealerPointsLabel, 0, 4);

        TextField dealerPointsField = new TextField();
        grid.add(dealerPointsField, 1, 4);

        Label playerLabel = new Label("PLAYER");
        grid.add(playerLabel, 0, 5);

        Label playerCardsLabel = new Label("Cards: ");
        grid.add(playerCardsLabel, 0,6);

        TextArea playerCardsField = new TextArea();
        grid.add(playerCardsField, 1,6);

    //  Cartes PLayer
        Label playerPointLabel = new Label("Points: ");
        grid.add(playerPointLabel, 0, 7);

        TextField playerPointField = new TextField();
        grid.add(playerPointField, 1, 7);

    //  Bouton Hit N Stand

        Button hitButton = new Button();
        hitButton.setText("Hit");

        Button standButton = new Button();
        standButton.setText("Stand");


        HBox HitStandButtonBox = new HBox(10);
        HitStandButtonBox.getChildren().add(hitButton);
        HitStandButtonBox.getChildren().add(standButton);

        HitStandButtonBox.setAlignment(Pos.BOTTOM_LEFT);

        grid.add(HitStandButtonBox, 0, 8);

//  Resultat win or loose!

        Label winnerLabel = new Label("RESULT: ");
        grid.add(winnerLabel, 0, 9);

        TextField winnerField = new TextField();
        grid.add(winnerField, 1, 9);

//    Bouton Play ou Exit

        Button playButton = new Button();
        playButton.setText("Play");

        Button exitButton = new Button();
        exitButton.setText("Exit");


        HBox playButtonBox = new HBox(10);
        playButtonBox.getChildren().add(playButton);
        playButtonBox.getChildren().add(exitButton);

        playButtonBox.setAlignment(Pos.BOTTOM_LEFT);

        grid.add(playButtonBox, 0, 10);
















        Scene scene = new Scene(grid, 320, 240);
        stage.setTitle("BlackJack App!");
        stage.setScene(scene);
        stage.show();
    }
}

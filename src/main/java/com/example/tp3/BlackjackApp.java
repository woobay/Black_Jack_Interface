package com.example.tp3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Locale;

import static javafx.application.Application.launch;

public class BlackjackApp extends Application {

    private static BlackjackGame game;
    TextField moneyField = new TextField();
    TextField betField = new TextField();
    ListView<String> dealerCardsField = new ListView<>();
    TextField dealerPointsField = new TextField();
    ListView<String>  playerCardsField = new ListView<>();
    TextField resultField = new TextField();
    TextField playerPointField = new TextField();
    Label errorMessageLabel = new Label();
    Button playButton = new Button();
    Button exitButton = new Button();
    Button hitButton = new Button();
    Button standButton = new Button();


    public static void main(String[] args) {
        launch();
    }
    public void start(Stage stage){
        game = new BlackjackGame();
        moneyField.setText(formatNumber(game.getTotalMoney()));
        disableButton(hitButton, true);
        disableButton(standButton, true);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setVgap(10);
        grid.setHgap(10);

        //------------------ Label and Field---------------------------
        grid.add(errorMessageLabel,0, 11,2, 1);
        errorMessageLabel.setTextFill(Color.RED);

        Label moneyLabel = new Label("Money: ");
        grid.add(moneyLabel, 0, 0);
        grid.add(moneyField, 1, 0);
        moneyField.setEditable(false);
        moneyField.setFocusTraversable(false);

        Label betLabel = new Label("Bet: ");
        grid.add(betLabel, 0,1 );
        grid.add(betField, 1, 1);

        Label dealerLabel = new Label("DEALER");
        grid.add(dealerLabel, 0, 2);

        Label dealerCardsLabel = new Label("Cards: ");
        grid.add(dealerCardsLabel, 0,3);
        grid.add(dealerCardsField, 1,3);

        Label dealerPointsLabel = new Label("Points: ");
        grid.add(dealerPointsLabel, 0, 4);
        grid.add(dealerPointsField, 1, 4);
        dealerPointsField.setEditable(false);

        Label playerLabel = new Label("PLAYER");
        grid.add(playerLabel, 0, 5);

        Label playerCardsLabel = new Label("Cards: ");
        grid.add(playerCardsLabel, 0,6);
        grid.add(playerCardsField, 1,6);

        Label playerPointLabel = new Label("Points: ");
        grid.add(playerPointLabel, 0, 7);
        grid.add(playerPointField, 1, 7);
        playerPointField.setEditable(false);

        Label winnerLabel = new Label("RESULT: ");
        grid.add(winnerLabel, 0, 9);
        grid.add(resultField, 1, 9);
        resultField.setFocusTraversable(false);

        //----------------- Button Hit and Stand -------------------
        hitButton.setText("Hit");
        standButton.setText("Stand");
        HBox HitStandButtonBox = new HBox(10);
        HitStandButtonBox.getChildren().addAll(hitButton, standButton);
        HitStandButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        grid.add(HitStandButtonBox, 0, 8);

        //---------------------- Bouton Play et Exit -----------------------
        playButton.setText("Play");
        exitButton.setText("Exit");
        HBox playButtonBox = new HBox(10);
        playButtonBox.getChildren().addAll(playButton, exitButton);
        playButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        grid.add(playButtonBox, 0, 10);

        // Definition des largeurs des colonnes
        for(int i = 0; i < grid.getColumnCount(); i++){
            ColumnConstraints col = new ColumnConstraints(210);
            grid.getColumnConstraints().add(col);
        }

        Scene scene = new Scene(grid, 600, 600);
        stage.setTitle("BlackJack App!");
        stage.setScene(scene);
        stage.show();

        //-------------------event button---------------------
        betField.setOnAction(event -> playEvent());

        playButton.setOnAction(event -> playEvent());

        exitButton.setOnAction(actionEvent -> { System.exit(0); });

        hitButton.setOnAction(event -> hitEvent());

        standButton.setOnAction(event -> standEvent());
    }

    //----------------------Methode des Events Button--------------------
    private void playEvent(){
        betField.setEditable(true);

        if(playButton.getText().equals("Play Again")){
            errorMessage("");
            betField.setText("");
            resultField.setText("");
            playerCardsField.getItems().clear();
            dealerCardsField.getItems().clear();
            dealerPointsField.setText("");
            playerPointField.setText("");
            moneyField.setText(formatNumber(game.getTotalMoney()));
            playButton.setText("Play");
            betField.requestFocus();
        }
        else{
            try {
                if(game.isValidBet(Double.parseDouble(betField.getText()))){
                    errorMessage("");
                    betField.setEditable(false);
                    disableButton(hitButton, false);
                    disableButton(standButton, false);
                    disableButton(playButton, true);
                    disableButton(exitButton, true);
                    hitButton.requestFocus();

                    game.deal();
                    showDealerShowCard();
                    showPlayerHand();

                    playerPointField.setText(String.valueOf(game.getPlayerHand().getPoints()));
                }else{
                    errorMessage("Your Bet must be between " + formatNumber(game.getMinBet()) + " or " + formatNumber(game.getMaxBet()) + " or your total money");
                    return;
                }
                if (game.getPlayerHand().isBlackjack() || game.getDealerHand().isBlackjack()){
                    showWinner();
                }
            }catch (NumberFormatException e){
                errorMessage("You must be enter a number!!");
            }
        }
    }

    private void hitEvent(){
        playerCardsField.getItems().clear();
        game.hit();
        showPlayerHand();
        playerPointField.setText(String.valueOf(game.getPlayerHand().getPoints()));

        if (game.getPlayerHand().isBust() || game.getPlayerHand().getPoints() == 21) {
            dealerTurn();
        }
    }
    private void standEvent(){
        disableButton(hitButton, true);
        disableButton(standButton, true);
        disableButton(playButton, false);
        disableButton(exitButton, false);

        dealerTurn();
    }

    //-------------------- Methodes General ------------------
    private void dealerTurn(){
        dealerCardsField.getItems().clear();
        game.stand();
        showWinner();
    }

	// affiche le message DEALER'S SHOW CARD et puis affiche le deuxieme carte dans la main du courtier
    private void showDealerShowCard() {
        dealerCardsField.getItems().add(game.getDealerShowCard().display());
    }

	// affiche le message DEALER'S CARDS et puis affiche les cartes dans la main du courtier
    private void showDealerHand() {
        dealerCardsField.getItems().clear();
        Card[] cards = game.getDealerHand().getCards().toArray(new Card[0]);
        for (Card card: cards) {
            dealerCardsField.getItems().add(card.display());
        }
    }

	// affiche le message YOUR CARDS et puis affiche les cartes dans la main du joueur
    private void showPlayerHand() {
        Card[] cards = game.getPlayerHand().getCards().toArray(new Card[0]);
        for (Card card: cards) {
            playerCardsField.getItems().add(card.display());
        }
    }

	// affiche Total money
    private String formatNumber(double num) {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.CANADA_FRENCH);
        String formatNumber = currency.format(num);
        return formatNumber;
    }
    private void showWinner() {

        showDealerHand();

        disableButton(hitButton, true);
        disableButton(standButton, true);
        disableButton(playButton, false);
        disableButton(exitButton, false);
        dealerPointsField.setText(String.valueOf(game.getDealerHand().getPoints()));

        if(game.isPush()) {
            resultText("Push!", "-fx-text-fill: grey");
        } else if(game.getPlayerHand().isBlackjack()) {
            resultText("BLACKJACK! You win!", "-fx-text-fill: green");
            game.addBlackjackToTotal();
        } else if (game.playerWins()) {
            resultText("You win!", "-fx-text-fill: green");
            game.addBetToTotal();
        } else {
            if(game.getDealerHand().isBlackjack()) resultText("Dealer BLACKJACK!! Sorry, you lose..", "-fx-text-fill: red");
            else resultText("Sorry, you lose", "-fx-text-fill: red");
            game.subtractBetFromTotal();
        }
        moneyField.setText(formatNumber(game.getTotalMoney()));
        if(game.isOutOfMoney()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("You are out of money");
            alert.setContentText("If do you want play against, press Play Again button");
            alert.showAndWait();
            game.resetMoney();
        }
        playButton.setText("Play Again");
    }
    private void resultText(String mess, String color){
        resultField.setText(mess);
        resultField.setStyle(color);
    }
    private void disableButton(Button button, boolean bol){
        button.setDisable(bol);
    }
    private void errorMessage(String mess){
        errorMessageLabel.setText(mess);
    }
}

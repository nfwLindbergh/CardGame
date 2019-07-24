import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
   static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("WELCOME TO FRED'S CASINO!");
        do {
            displayMenu();
            int option = getOption();

            if (option == 1) {
                playGame();
            } else if (option == 2) {
                gameRules();
            } else if (option == 3) {
                break;
            }
        } while (true);

        System.out.println("Exiting.... Please come again!");
    }

    static void playGame() {
        //Creating a new deck for the game
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffle();

        //Create an empty deck for the player
        Deck playerDeck = new Deck();

        //Create an empty deck for the dealer
        Deck dealerDeck = new Deck();

        double playerMoney = 100.00;


        //Game Loop
        while (playerMoney > 0) {
            //Game On!
            //Taking the players bet:
            System.out.println("You have $" + playerMoney + ", how much would you like to bet?");
            double playerBet = userInput.nextDouble();
            //Checking so the player doesn't play for money that they don't have.
            if (playerBet > playerMoney) {
                System.out.println("Sorry, you can not bet more money than you got... Please leave!");
                break;
            }

            boolean endRound = false;

            //Start dealing
            //Player gets two cards
            playerDeck.draw(playingDeck);
            playerDeck.draw(playingDeck);

            //Dealer also gets two cards
            dealerDeck.draw(playingDeck);
            dealerDeck.draw(playingDeck);

            while (true) {
                //Display the player's hand
                System.out.println("Your Hand: ");
                System.out.print(playerDeck.toString());
                System.out.println("\nYour deck is valued at: " + playerDeck.cardsValue() + "\n");

                //Display the dealers hand
                System.out.println("Dealer's Hand: " + dealerDeck.getCard(0).toString() + " and [Hidden]");

                //What does the player want to do?
                System.out.println("\nWould you like to [1]Hit or [2]Stand?");
                int response = userInput.nextInt();

                //They hit
                if (response == 1) {
                    playerDeck.draw(playingDeck);
                    System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize() - 1).toString());
                    //Player getting bust if > 21
                    if (playerDeck.cardsValue() > 21) {
                        System.out.println("Bust. Your hand is valued at: " + playerDeck.cardsValue());
                        playerMoney -= playerBet;
                        endRound = true;
                        break;
                    }
                }

                if (response == 2) {
                    break;
                }
            }

            //Reveal dealers hand
            System.out.println("Dealer cards: " + dealerDeck.toString());
            //Checking if dealer has more points than the player
            if ((dealerDeck.cardsValue() > playerDeck.cardsValue()) && endRound == false) {
                System.out.println("Dealer WINS!");
                playerMoney -= playerBet;
                endRound = true;
            }

            //Dealer draws at 16 and stands at 17
            while ((dealerDeck.cardsValue() < 17) && endRound == false) {
                dealerDeck.draw(playingDeck);
                System.out.println("Dealer draws: " + dealerDeck.getCard(dealerDeck.deckSize() - 1).toString());

            }

            //Display total value of Dealer's hand
            System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());
            //Determine if the dealer is busted or not
            if ((dealerDeck.cardsValue() > 21) && endRound == false) {
                System.out.println("Dealer is bust. Player WINS!");
                playerMoney += playerBet;
                endRound = true;
            }

            //Determine if push/tie
            if ((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false) {
                System.out.println("Push! Player keep its bet.");
                endRound = true;
            }

            //Determine if the player wins
            if ((playerDeck.cardsValue() > dealerDeck.cardsValue()) && endRound == false) {
                System.out.println("Player WINS the hand!");
                playerMoney += playerBet;
                endRound = true;
            } else if (endRound == false) {
                System.out.println("Player LOSE the hand.");
                playerMoney -= playerBet;
                endRound = true;
            }

            playerDeck.moveAllToDeck(playingDeck);
            dealerDeck.moveAllToDeck(playingDeck);
            System.out.println("The round is over.");
        }

        System.out.println("Game over! You are broke....");
    }

    static void displayMenu() {
        System.out.println("\n_____________________________\n - Menu -\n");
        System.out.println("1 - Play BlackJack!");
        System.out.println("2 - Rules of the Game");
        System.out.println("3 - Exit");
    }

    static int getOption() {
        System.out.print("Option: ");
        int option = userInput.nextInt();
        userInput.nextLine();
        System.out.println();
        return option;
    }

    static void gameRules() {
        System.out.println("With the exception of Poker, Blackjack is the most popular gambling card game.\n" +
                "Equally well known as Twenty-One. The rules are simple, the play is thrilling, and there is opportunity for high strategy.\n" +
                "In fact, for the expert player who mathematically plays a perfect game and is able to count cards, the odds are sometimes in that player's favor to win.\n" +
                "\n" +
                "Each participant attempts to beat the dealer by getting a count as close to 21 as possible, without going over 21.\n" +
                "It is up to each individual player if an ace is worth 1 or 11. Face cards are 10 and any other card is its pip value.\n" +
                "Before each deal, the player places a bet. Minimum bet is $1. You can not bet more money than you have. Trying to do so will get you kicked out.\n" +
                "Enjoy the game!");
    }
}

public class Card {

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }


    public String toString() {
        return this.rank.toString() + " of " + this.suit.toString();
    }

    public Rank getRank() {
        return rank;
    }
}

/* Student: Björn Hansson (bjornstellan.hansson.5659@student.uu.se) */

public class CardDeck extends CardPile {
    private static final int DECK_SIZE = 52;
    private static final int SUIT_SIZE = 13;

    public CardDeck() {
        for (int i = 1; i <= DECK_SIZE; i++) {
            Card card = createCard(i);
            add(card);
            System.out.println("created card " + card.getClass().getSimpleName() + card.getRank());
        }
    }

    public Card pickCard(int index) {
        return remove(index);
    }

    private Card createCard(int rank) {
        if (rank <= SUIT_SIZE) {
            return new Heart(rank);
        } else if (rank <= SUIT_SIZE * 2) {
            return new Diamond(rank - SUIT_SIZE);
        } else if (rank <= SUIT_SIZE * 3) {
            return new Spade(rank - SUIT_SIZE * 2);
        } else if (rank <= SUIT_SIZE * 4) {
            return new Club(rank - SUIT_SIZE * 3);
        }
        return null;
    }

    public boolean isMoveAllowed(Card newCard) {
        return false;
    }
}

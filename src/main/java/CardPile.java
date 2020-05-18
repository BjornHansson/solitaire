/* Student: Björn Hansson (bjornstellan.hansson.5659@student.uu.se) */

import java.util.*;
import java.awt.*;

public abstract class CardPile {
    private Stack<Card> cards;
    private Placeholder placeholder;

    public CardPile() {
        cards = new Stack<Card>();
    }

    public CardPile(int x, int y) {
        cards = new Stack<Card>();
        placeholder = new Placeholder();
        placeholder.setPosition(x, y);
    }

    public void paint(Graphics g) {
        placeholder.paint(g);
    }

    public void add(Card c) {
        cards.add(c);
    }

    public void prepend(Card c) {
        cards.add(0, c);
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public Card peek() {
        return isEmpty() ? placeholder : cards.peek();
    }

    public Card pop() {
        return cards.pop();
    }

    public boolean isEmpty() {
        return cards.empty();
    }

    public int size() {
        return cards.size();
    }

    public Card remove(int index) {
        return cards.remove(index);
    }

    public abstract boolean isMoveAllowed(Card newCard);
}

class Stock extends CardPile {
    public static final int Y = 100;
    public static final int X = 0;

    public Stock() {
        super(X, Y);
    }

    public boolean isMoveAllowed(Card newCard) {
        return false;
    }
}

class WastePile extends CardPile {
    public static final int Y = 100;
    public static final int X = 100;

    public WastePile() {
        super(X, Y);
    }

    public boolean isMoveAllowed(Card newCard) {
        return false;
    }
}

class Foundation extends CardPile {
    public static final int Y = 100;
    public static final int X = 100;
    public static final int MARGIN = 300;

    public Foundation(int pileIndex) {
        super((pileIndex * X) + MARGIN, Y);
    }

    public boolean isMoveAllowed(Card newCard) {
        Card topCard = peek();
        if (newCard.isIntersectionAllowed(topCard) && isRankHigher(newCard, topCard)) {
            if (isEmpty()) {
                return true;
            } else {
                return isSuitEqual(topCard, newCard);
            }
        }

        return false;
    }

    private boolean isRankHigher(Card card1, Card card2) {
        return card1.getRank() - 1 == card2.getRank();
    }

    private boolean isSuitEqual(Card card1, Card card2) {
        return card1.getClass() == card2.getClass();
    }
}

class Tableau extends CardPile {
    private static final int KING = 13;
    public static final int Y = 250;
    public static final int X = 100;
    public static final int MARGIN = 40;

    public Tableau(int pileIndex) {
        super(pileIndex * X, Y);
    }

    public boolean isMoveAllowed(Card newCard) {
        Card topCard = peek();

        if (newCard.isIntersectionAllowed(topCard)) {
            if (isRankLower(newCard, topCard)) {
                if (isRed(topCard) && isBlack(newCard)) {
                    return true;
                } else if (isRed(newCard) && isBlack(topCard)) {
                    return true;
                }
            } else if (isEmpty() && newCard.getRank() == KING) {
                return true;
            }
        }

        return false;
    }

    private boolean isRankLower(Card card1, Card card2) {
        return card1.getRank() + 1 == card2.getRank();
    }

    private boolean isRed(Card card) {
        return (card instanceof Heart || card instanceof Diamond);
    }

    private boolean isBlack(Card card) {
        return (card instanceof Club || card instanceof Spade);
    }
}

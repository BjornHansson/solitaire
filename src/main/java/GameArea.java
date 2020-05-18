/* Student: Björn Hansson (bjornstellan.hansson.5659@student.uu.se) */

import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.*;
import javax.swing.*;

class GameArea extends JPanel {
    private List<CardPile> allPiles; // Just to keep references to all piles
    private CardPile[] tableau; // Main table. 28 cards when game starts
    private CardPile[] foundation; // Piles to finish. Empty when game starts
    private CardPile stock; // Pile of cards remaining. 24 cards when game starts
    private CardPile wastePile; // Discard pile. Empty when game starts
    private CardPile activePile; // Active pile of the dragged/pressed card. Empty when game starts

    private CardDeck cardDeck; // Card deck to use
    Card pressedCard; // Reference to active pressed card

    public GameArea() {
        setBackground(new Color(80, 164, 79));

        MouseListenerGame listener = new MouseListenerGame(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);

        initCards(true);
        initMenuButtons();
    }

    private void initMenuButtons() {
        JButton newGameBtn = new JButton("New game");
        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initCards(true);
                repaint();
            }
        });
        add(newGameBtn);
    }

    private void initCards(boolean random) {
        tableau = new CardPile[7];
        foundation = new CardPile[4];
        stock = new Stock();
        wastePile = new WastePile();

        allPiles = new ArrayList<CardPile>();
        allPiles.add(stock);
        allPiles.add(wastePile);

        pressedCard = null;

        cardDeck = new CardDeck();
        if (random) {
            Collections.shuffle(cardDeck.getCards());
        }

        for (int pileIndex = 0; pileIndex < tableau.length; pileIndex++) {
            CardPile cardPile = new Tableau(pileIndex);
            for (int i = pileIndex; i >= 0; i--) {
                Card card = cardDeck.pickCard(0);
                card.setPosition(pileIndex * Tableau.X, (i * Tableau.MARGIN) + Tableau.Y);
                cardPile.prepend(card);
            }
            cardPile.peek().setFaceup(true);
            tableau[pileIndex] = cardPile;
            allPiles.add(cardPile);
        }

        for (Card card : cardDeck.getCards()) {
            card.setPosition(Stock.X, Stock.Y);
            stock.add(card);
        }

        for (int pileIndex = 0; pileIndex < foundation.length; pileIndex++) {
            CardPile cardPile = new Foundation(pileIndex);
            foundation[pileIndex] = cardPile;
            allPiles.add(cardPile);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (CardPile pile : allPiles) {
            pile.paint(g);
            for (Card card : pile.getCards()) {
                card.paint(g);
            }
        }

        if (pressedCard != null) {
            pressedCard.paint(g);
        }
    }

    public void dragCard(Card card, Point draggedPoint, Point startPoint) {
        card.move(draggedPoint.x - startPoint.x, draggedPoint.y - startPoint.y);
        repaint();
    }

    public Card getPressedCard(Point point) {
        pressedCard = null;

        for (CardPile pile : allPiles) {
            if (!pile.isEmpty() && pressedCard == null) {
                Card card = pile.peek();
                if (card.contains(point)) {
                    pressedCard = pile.pop();
                    activePile = pile;
                }
            }
        }

        return pressedCard;
    }

    public void clickCard(Point point) {
        if (stock.peek().contains(point)) {
            if (stock.isEmpty()) {
                int size = wastePile.size();
                for (int i = 0; i < size; i++) {
                    Card card = wastePile.pop();
                    card.setFaceup(false);
                    card.setPosition(Stock.X, Stock.Y);
                    stock.add(card);
                }
            } else {
                Card card = stock.pop();
                card.setFaceup(true);
                card.setPosition(WastePile.X, WastePile.Y);
                wastePile.add(card);
            }

            repaint();
        }

    }

    public boolean moveCardInPiles(Card movingCard) {
        int countPiles = 0;
        CardPile foundPile = null;

        for (CardPile pile : allPiles) {
            Card cardInPile = pile.peek();

            if (movingCard.intersects(cardInPile)) {
                countPiles++;
                if (pile.isMoveAllowed(movingCard)) {
                    foundPile = pile;
                }
            }
        }

        if (countPiles == 1 && foundPile != null) { // Only allow if it is one pile
            foundPile.add(movingCard);
            if (activePile.size() > 0) {
                activePile.peek().setFaceup(true);
            }
            repaint();

            return true;
        } else { // Not allowed, put back the card
            activePile.add(movingCard);
        }

        return false;
    }
}

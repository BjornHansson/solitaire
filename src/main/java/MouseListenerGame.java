/* Student: Björn Hansson (bjornstellan.hansson.5659@student.uu.se) */

import java.awt.event.*;
import java.awt.Point;

class MouseListenerGame implements MouseListener, MouseMotionListener {
    private GameArea game;
    private Card pressedCard;
    private Point pressedStartPoint;
    private Point draggedStartPoint;

    public MouseListenerGame(GameArea game) {
        this.game = game;
    }

    public void mouseClicked(MouseEvent e) {
        // System.out.println("Clicked " + e.getPoint());
        game.clickCard(e.getPoint());
    }

    public void mouseEntered(MouseEvent e) {
        // System.out.println("Entered " + e.getPoint());
    }

    public void mouseExited(MouseEvent e) {
        // System.out.println("Exited " + e.getPoint());
    }

    public void mousePressed(MouseEvent e) {
        // System.out.println("Pressed " + e.getPoint());
        pressedCard = game.getPressedCard(e.getPoint());
        pressedStartPoint = draggedStartPoint = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
        // System.out.println("Released " + e.getPoint());
        if (pressedCard != null) {
            boolean moveAllowed = game.moveCardInPiles(pressedCard);
            if (!moveAllowed) {
                // Drag back to start point
                game.dragCard(pressedCard, pressedStartPoint, draggedStartPoint);
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        // System.out.println("Dragged " + e.getPoint());
        if (pressedCard != null) {
            game.dragCard(pressedCard, e.getPoint(), draggedStartPoint);
            draggedStartPoint = e.getPoint();
        }
    }

    public void mouseMoved(MouseEvent e) {
        // System.out.println("Moved " + e.getPoint());
    }
}

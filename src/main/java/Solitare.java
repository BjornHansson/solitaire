/* Student: Björn Hansson (bjornstellan.hansson.5659@student.uu.se) */

import javax.swing.JFrame;

public class Solitare extends JFrame {
    public Solitare() {
        add(new GameArea());
        setTitle("Klondike Solitaire");
        setSize(750, 750);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] arg) {
        new Solitare();
    }
}

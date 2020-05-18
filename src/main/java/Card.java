/* Student: Björn Hansson (bjornstellan.hansson.5659@student.uu.se) */

import java.net.MalformedURLException;
import java.net.URL;
import java.awt.*;
import javax.swing.ImageIcon;

public abstract class Card {
    private static final String PAGE_URL = "https://raw.githubusercontent.com/BjornHansson/solitaire/master/images/";
    private static final int MIN_AREA = 3000;
    private boolean faceup;
    private int rank;
    private int x, y;
    private int width, height;
    private Image face, back;

    public Card(int rank) {
        this.rank = rank;
        this.width = 70;
        this.height = 95;
        this.x = 0;
        this.y = 0;
        this.faceup = false;
        try {
            this.back = createImage("b2fv");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        Image img = isFaceup() ? getFace() : getBack();
        g.drawImage(img, getX(), getY(), null);
    }

    private Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public boolean contains(Point point) {
        return getRectangle().contains(point);
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public boolean isFaceup() {
        return faceup;
    }

    public void setFaceup(boolean faceup) {
        this.faceup = faceup;
    }

    public int getRank() {
        return rank;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public Image getFace() {
        return face;
    }

    public void setFace(Image face) {
        this.face = face;
    }

    public Image getBack() {
        return back;
    }

    public void setBack(Image back) {
        this.back = back;
    }

    public Image createImage(String name) throws MalformedURLException {
        URL url = new URL(PAGE_URL + name + ".gif");
        return new ImageIcon(url).getImage();
    }

    /**
     * Checks if two cards intersects at all
     * 
     * @param card
     * @return boolean
     */
    public boolean intersects(Card card) {
        if (card.equals(this)) {
            return false;
        }
        return getRectangle().intersects(card.getRectangle());
    }

    /**
     * Checks if the two cards intersects enough to be considered allowed to move
     * 
     * @param card
     * @return boolean
     */
    public boolean isIntersectionAllowed(Card card) {
        Rectangle intersection = getRectangle().intersection(card.getRectangle());
        if (intersection.isEmpty()) {
            return false;
        } else {
            return (intersection.width * intersection.height > MIN_AREA);
        }
    }
}

class Club extends Card {
    public Club(int rank) {
        super(rank);

        try {
            Image face = createImage("c" + rank);
            setFace(face);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

class Diamond extends Card {
    public Diamond(int rank) {
        super(rank);

        try {
            Image face = createImage("d" + rank);
            setFace(face);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

class Heart extends Card {
    public Heart(int rank) {
        super(rank);

        try {
            Image face = createImage("h" + rank);
            setFace(face);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

class Spade extends Card {
    public Spade(int rank) {
        super(rank);

        try {
            Image face = createImage("s" + rank);
            setFace(face);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

class Placeholder extends Card {
    public Placeholder() {
        super(0);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(getX(), getY(), getWidth(), getHeight());
    }
}

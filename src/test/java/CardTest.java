import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CardTest extends TestCase {
    public void testSetFaceup() {
        // Given
        Card card = new Heart(1);
        // When
        card.setFaceup(true);
        // Then
        assertTrue(card.isFaceup());
    }
}

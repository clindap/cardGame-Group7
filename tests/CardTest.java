import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void testGetRank() {
        Card card = new Card("A");
        assertEquals("A", card.getRank());
    }

    @Test
    public void testToString() {
        Card card = new Card("10");
        assertEquals("10", card.toString());
    }


}


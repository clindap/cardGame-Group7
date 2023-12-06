import org.junit.Test;
import java.util.Collections;
import static org.junit.Assert.*;

public class testDrawCard {

    @Test
    public void DrawCard() {
        GroupOfCards deck = new GroupOfCards();
        deck.addCard(new Card("A"));

        Player player = new Player("Player1");
        Game game = new Game("Go Fish", Collections.singletonList("Player1"));
        game.drawCard(player, deck);

        assertFalse(deck.isEmpty());
        assertEquals(0, player.getHand().size());
    }

}


import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.Assert.*;

public class testDeclareWinner {

    @Test
    public void DeclareWinner() {
        Player winner = new Player("Winner");
        Player loser = new Player("Loser");

        Game game = new Game("Go Fish", Arrays.asList(winner.getName(), loser.getName()));
        winner.addToHand(Collections.singletonList(new Card("A"))); // Adding a card to the winner
        game.declareWinner();
    }
}


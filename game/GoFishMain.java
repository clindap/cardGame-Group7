import java.util.*;

class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getMatchingCards(String requestedRank) {
        List<Card> matchingCards = new ArrayList<>();
        Iterator<Card> iterator = hand.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getRank().equals(requestedRank)) {
                matchingCards.add(card);
                iterator.remove(); // Remove the card from the player's hand
            }
        }
        return matchingCards;
    }

    public void addToHand(List<Card> cards) {
        hand.addAll(cards);
    }

    public void removeFromHand(List<Card> cards) {
        hand.removeAll(cards);
    }

    public void removeSets() {
        Map<String, Integer> rankCounts = new HashMap<>();

        // Count the occurrences of each rank in the player's hand
        for (Card card : hand) {
            String rank = card.getRank();
            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
        }

        // Identify and remove sets (four cards of the same rank)
        Iterator<Card> iterator = hand.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            String rank = card.getRank();
            int count = rankCounts.get(rank);

            if (count >= 4) {
                // Remove the set from the player's hand
                for (int i = 0; i < 4; i++) {
                    iterator.remove();
                }
            }
        }
    }
}

class Card {
    private String rank;

    public Card(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank;
    }
}


class GroupOfCards {
    private List<Card> cards;

    public GroupOfCards() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (!isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

class Game {
    private String name;
    private List<Player> players;
    private GroupOfCards deck;

    public Game(String name, List<String> playerNames) {
        this.name = name;
        this.players = new ArrayList<>();
        this.deck = new GroupOfCards();

        initializeDeck();
        shuffleDeck();
        dealCards(playerNames);
    }
    public List<Player> getPlayers() {
        return players;
    }
    private void initializeDeck() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        for (String rank : ranks) {
            for (int i = 0; i < 4; i++) {  // Four suits
                deck.addCard(new Card(rank));
            }
        }
    }

    private void shuffleDeck() {
        deck.shuffle();
    }

    private void dealCards(List<String> playerNames) {
        for (String playerName : playerNames) {
            Player player = new Player(playerName);
            for (int i = 0; i < 5; i++) {  // Deal 5 cards to each player
                player.addToHand(Collections.singletonList(deck.drawCard()));
            }
            players.add(player);
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (!deck.isEmpty() && !players.isEmpty()) {
            for (Player currentPlayer : players) {
                displayGameState(currentPlayer);

                // Get input for card request
                System.out.println(currentPlayer.getName() + ", enter card rank to request: ");
                String requestedRank = scanner.next();

                // Perform card request
                performCardRequest(currentPlayer, requestedRank);
            }
        }

        scanner.close();
    }

    private void performCardRequest(Player currentPlayer, String requestedRank) {
        for (Player targetPlayer : players) {
            if (targetPlayer != currentPlayer) {
                List<Card> matchingCards = targetPlayer.getMatchingCards(requestedRank);
                if (!matchingCards.isEmpty()) {
                    // Move matching cards to current player's hand
                    currentPlayer.addToHand(matchingCards);
                    targetPlayer.removeFromHand(matchingCards);

                    // Check for and remove sets from the hands
                    currentPlayer.removeSets();
                    targetPlayer.removeSets();

                    // Check if the target player is out of cards
                    if (targetPlayer.getHand().isEmpty()) {
                        players.remove(targetPlayer);
                    }

                    return;
                }
            }
        }

        // Go Fish: Draw a card from the deck
        Card drawnCard = deck.drawCard();
        currentPlayer.addToHand(Collections.singletonList(drawnCard));
        System.out.println(currentPlayer.getName() + " drew a " + drawnCard.getRank() + " from the deck.");

        // Check for and remove sets from the hand
        currentPlayer.removeSets();
    }

    private void displayGameState(Player currentPlayer) {
        System.out.println("\nCurrent Player: " + currentPlayer.getName());
        System.out.println("===================================");
        for (Player player : players) {
            System.out.println(player.getName() + "'s Hand: " + player.getHand());
        }
        System.out.println("===================================");
    }

    public void declareWinner() {
        Player winner = null;
        int maxSets = 0;

        for (Player player : players) {
            int sets = countSets(player);
            if (sets > maxSets) {
                maxSets = sets;
                winner = player;
            }
        }

        if (winner != null) {
            System.out.println("Congratulations, " + winner.getName() + "! You are the winner!");
        } else {
            System.out.println("It's a tie! No winner in this game.");
        }
    }

    private int countSets(Player player) {
        Map<String, Integer> rankCounts = new HashMap<>();

        for (Card card : player.getHand()) {
            String rank = card.getRank();
            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
        }

        int sets = 0;
        for (int count : rankCounts.values()) {
            sets += count / 4; // Counting sets (four cards of the same rank)
        }

        return sets;
    }
	public void drawCard(Player player, GroupOfCards deck2) {
		// TODO Auto-generated method stub
		
	}
}

public class GoFishMain {
    public static void main(String[] args) {
        List<String> playerNames = Arrays.asList("Player1", "Player2", "Player3");
        Game goFishGame = new Game("Go Fish", playerNames);
        goFishGame.play();
        goFishGame.declareWinner();
    }
}

import org.example.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MainTest {
    @Test
    @DisplayName("Check if adventure deck has 100 cards")
    public void RESP_01_test_01() {
        Game game = new Game();
        assertEquals(100, game.getAdventureDeck().getDeckSize());
    }

    @Test
    @DisplayName("Check if event deck has 17 cards")
    public void RESP_01_test_02() {
        Game game = new Game();
        assertEquals(17, game.getEventDeck().getDeckSize());
    }

    @Test
    @DisplayName("Check if adventure deck has correct amount of foe and weapon cards")
    public void RESP_01_test_03() {
        Game game = new Game();
        List<Card> adventureDeck = game.getAdventureDeck().getDeckCards();

        HashMap<String, Integer> expectedFrequencies = new HashMap<>();
        expectedFrequencies.put("F5", 8);
        expectedFrequencies.put("F10", 7);
        expectedFrequencies.put("F15", 8);
        expectedFrequencies.put("F20", 7);
        expectedFrequencies.put("F25", 7);
        expectedFrequencies.put("F30", 4);
        expectedFrequencies.put("F35", 4);
        expectedFrequencies.put("F40", 2);
        expectedFrequencies.put("F50", 2);
        expectedFrequencies.put("F70", 1);

        expectedFrequencies.put("D", 6);
        expectedFrequencies.put("H", 12);
        expectedFrequencies.put("S", 16);
        expectedFrequencies.put("B", 8);
        expectedFrequencies.put("L", 6);
        expectedFrequencies.put("E", 2);

        HashMap<String, Integer> actualFrequencies = new HashMap<>();

        for (int i = 0;i<adventureDeck.size();i++) {
            String cardName = adventureDeck.get(i).getName();
            if(!actualFrequencies.containsKey(cardName)){
                actualFrequencies.put(cardName,1);
            } else {
                actualFrequencies.put(cardName,actualFrequencies.get(cardName) + 1);
            }
        }
        expectedFrequencies.forEach((cardType, expectedCount) -> {
            int actualCount = actualFrequencies.get(cardType);
            assertEquals(expectedCount, actualCount);
        });
    }

    @Test
    @DisplayName("Check if event deck has correct amount of quest and event cards")
    public void RESP_01_test_04() {
        Game game = new Game();
        List<Card> eventDeck = game.getEventDeck().getDeckCards();

        HashMap<String, Integer> expectedFrequencies = new HashMap<>();
        expectedFrequencies.put("Q2", 3);
        expectedFrequencies.put("Q3", 4);
        expectedFrequencies.put("Q4", 3);
        expectedFrequencies.put("Q5", 2);

        expectedFrequencies.put("Plague", 1);
        expectedFrequencies.put("Queens Favor", 2);
        expectedFrequencies.put("Prosperity", 2);

        HashMap<String, Integer> actualFrequencies = new HashMap<>();

        for (int i = 0;i<eventDeck.size();i++) {
            String cardName = eventDeck.get(i).getName();
            if(!actualFrequencies.containsKey(cardName)){
                actualFrequencies.put(cardName,1);
            } else {
                actualFrequencies.put(cardName,actualFrequencies.get(cardName) + 1);
            }
        }

        expectedFrequencies.forEach((cardType, expectedCount) -> {
            int actualCount = actualFrequencies.get(cardType);
            assertEquals(expectedCount, actualCount);
        });
    }

    @Test
    @DisplayName("Check if adventure deck is shuffled")
    public void RESP_01_test_05() {
        Game game = new Game();
        List<Card> originalDeck = new ArrayList<>(game.getAdventureDeck().getDeckCards());
        game.shuffleAdventureDeck();
        List<Card> shuffledDeck = game.getAdventureDeck().getDeckCards();
        assertFalse(originalDeck.equals(shuffledDeck));
    }

    @Test
    @DisplayName("Check if event deck is shuffled")
    public void RESP_01_test_06() {
        Game game = new Game();
        List<Card> originalDeck = new ArrayList<>(game.getEventDeck().getDeckCards());
        game.shuffleEventDeck();
        List<Card> shuffledDeck = game.getEventDeck().getDeckCards();
        assertFalse(originalDeck.equals(shuffledDeck));
    }

    /*------------------------------------RESP-2-----------------------------------------------------------------*/

    @Test
    @DisplayName("Check Each Player Exists")
    public void RESP_02_test_01() {
        Game game = new Game();
        assertNotNull(game.getPlayer(1));
        assertNotNull(game.getPlayer(2));
        assertNotNull(game.getPlayer(3));
        assertNotNull(game.getPlayer(4));
    }
    @Test
    @DisplayName("Check Each Player Has 12 Cards Each and Deck has 42 Cards")
    public void RESP_02_test_02() {
        Game game = new Game();
        game.dealAdventureCards(12,1);
        game.dealAdventureCards(12,2);
        game.dealAdventureCards(12,3);
        game.dealAdventureCards(12,4);

        assertEquals(12,game.getPlayer(1).getHand().size());
        assertEquals(12,game.getPlayer(2).getHand().size());
        assertEquals(12,game.getPlayer(3).getHand().size());
        assertEquals(12,game.getPlayer(4).getHand().size());

        assertEquals(game.getAdventureDeck().getDeckSize(),52);
    }
    @Test
    @DisplayName("Check Player Hand Is In Sorted Order")
    public void RESP_02_test_03() {
        Game game = new Game();
        game.dealAdventureCards(12,1);
        game.shuffleAdventureDeck();
        List<Card> hand = game.getPlayer(1).getHand();
        int adventureCardStart = 0;
        boolean foundError = false;
        for(int i=1;i<hand.size();i++){
            if(hand.get(i).getCardType() == Card.CardType.WEAPON){
                adventureCardStart = i;
                break;
            }
            foundError = hand.get(i - 1).getValue() < hand.get(i).getValue();
            assertFalse(foundError);
        }
        for(int i=adventureCardStart;i< hand.size();i++){
            foundError = hand.get(i - 1).getValue() > hand.get(i).getValue();
            assertFalse(foundError);
        }
    }

    @Test
    @DisplayName("Check Adventure Deck Is Rebuilt using Discarded Cards")
    public void RESP_02_test_04() {
        Game game = new Game();
        Player player1 = game.getPlayer(1);
        game.dealAdventureCards(100,1);
        for(int i=0;i<100;i++){
            game.discardAdventureCard(player1.getHand().removeFirst());
        }
        assertEquals(0,game.getAdventureDeck().getDeckSize());
        System.out.println(game.getAdventureDeck().getDeckSize());
        game.dealAdventureCards(1,1);
        assertEquals(99, game.getAdventureDeck().getDeckSize());
    }
    /*------------------------------------RESP-3-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check Each Player Plays their Turn In Order of P1-P4 back to P1")
    public void RESP_03_test_01() {
        Game game = new Game();
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(1,game.getCurrPlayer().getId());
        game.setCounter(game.getCounter()+1);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(2,game.getCurrPlayer().getId());
        game.setCounter(game.getCounter()+1);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(3,game.getCurrPlayer().getId());
        game.setCounter(game.getCounter()+1);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(4,game.getCurrPlayer().getId());
        game.setCounter(game.getCounter()+1);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(1,game.getCurrPlayer().getId());
    }
    /*------------------------------------RESP-4-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check Trim Function Will Prompt to Trim Hand if Over 12")
    public void RESP_04_test_01() {
        Game game = new Game();
        ArrayList<String> commands = new ArrayList<>();
        commands.add("1");
        game.setCommands(commands);
        game.dealInitialAdventureCards();
        game.dealAdventureCards(1,1);
        boolean res = game.checkTrim(game.getPlayer(1));
        assertTrue(res);
    }

    @Test
    @DisplayName("Check Trim Function Will Not Prompt to Trim Hand if 12 or Under")
    public void RESP_04_test_02() {
        Game game = new Game();
        ArrayList<String> commands = new ArrayList<>();
        game.setCommands(commands);
        game.dealInitialAdventureCards();
        boolean res = game.checkTrim(game.getPlayer(1));
        assertFalse(res);
    }

    @Test
    @DisplayName("Check Player Hand Is Successfully Trimmed")
    public void RESP_04_test_03() {
        Game game = new Game();
        ArrayList<String> commands = new ArrayList<>();
        commands.add("1");
        game.setCommands(commands);
        game.dealInitialAdventureCards();
        assertEquals(12,game.getPlayer(1).getHand().size());
        game.dealAdventureCards(1,1);
        assertEquals(13,game.getPlayer(1).getHand().size());
        game.checkTrim(game.getPlayer(1));
        assertEquals(12,game.getPlayer(1).getHand().size());
    }

    @Test
    @DisplayName("Check If Card Is Discarded Correctly")
    public void RESP_04_test_04() {
        Game game = new Game();
        ArrayList<String> commands = new ArrayList<>();
        commands.add("1");
        game.setCommands(commands);
        game.dealInitialAdventureCards();
        game.getPlayer(1).addCard(new Card(Card.CardType.FOE,"F5",10));
        game.checkTrim(game.getPlayer(1));
        assertEquals(1,game.getAdventureDeck().getDiscardSize());
        assertEquals("F5",game.getAdventureDeck().getDiscardCards().getFirst().getName());
    }
}
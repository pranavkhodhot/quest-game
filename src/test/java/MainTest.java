import org.example.Game;
import org.example.Card;
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
}
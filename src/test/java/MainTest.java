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

        for (int i = 0; i < adventureDeck.size(); i++) {
            String cardName = adventureDeck.get(i).getName();
            if (!actualFrequencies.containsKey(cardName)) {
                actualFrequencies.put(cardName, 1);
            } else {
                actualFrequencies.put(cardName, actualFrequencies.get(cardName) + 1);
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

        for (int i = 0; i < eventDeck.size(); i++) {
            String cardName = eventDeck.get(i).getName();
            if (!actualFrequencies.containsKey(cardName)) {
                actualFrequencies.put(cardName, 1);
            } else {
                actualFrequencies.put(cardName, actualFrequencies.get(cardName) + 1);
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
        game.dealAdventureCards(12, 1);
        game.dealAdventureCards(12, 2);
        game.dealAdventureCards(12, 3);
        game.dealAdventureCards(12, 4);

        assertEquals(12, game.getPlayer(1).getHand().size());
        assertEquals(12, game.getPlayer(2).getHand().size());
        assertEquals(12, game.getPlayer(3).getHand().size());
        assertEquals(12, game.getPlayer(4).getHand().size());

        assertEquals(game.getAdventureDeck().getDeckSize(), 52);
    }

    @Test
    @DisplayName("Check Player Hand Is In Sorted Order")
    public void RESP_02_test_03() {
        Game game = new Game();
        game.dealAdventureCards(12, 1);
        game.shuffleAdventureDeck();
        List<Card> hand = game.getPlayer(1).getHand();
        int adventureCardStart = 0;
        boolean foundError = false;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getCardType() == Card.CardType.WEAPON) {
                adventureCardStart = i;
                break;
            }
            foundError = hand.get(i - 1).getValue() < hand.get(i).getValue();
            assertFalse(foundError);
        }
        for (int i = adventureCardStart; i < hand.size(); i++) {
            foundError = hand.get(i - 1).getValue() > hand.get(i).getValue();
            assertFalse(foundError);
        }
    }

    @Test
    @DisplayName("Check Adventure Deck Is Rebuilt using Discarded Cards")
    public void RESP_02_test_04() {
        Game game = new Game();
        Player player1 = game.getPlayer(1);
        game.dealAdventureCards(100, 1);
        for (int i = 0; i < 100; i++) {
            game.discardAdventureCard(player1.getHand().removeFirst());
        }
        assertEquals(0, game.getAdventureDeck().getDeckSize());
        System.out.println(game.getAdventureDeck().getDeckSize());
        game.dealAdventureCards(1, 1);
        assertEquals(99, game.getAdventureDeck().getDeckSize());
    }

    /*------------------------------------RESP-3-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check Each Player Plays their Turn In Order of P1-P4 back to P1")
    public void RESP_03_test_01() {
        Game game = new Game();
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(1, game.getCurrPlayer().getId());
        game.setCounter(game.getCounter() + 1);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(2, game.getCurrPlayer().getId());
        game.setCounter(game.getCounter() + 1);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(3, game.getCurrPlayer().getId());
        game.setCounter(game.getCounter() + 1);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(4, game.getCurrPlayer().getId());
        game.setCounter(game.getCounter() + 1);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        assertEquals(1, game.getCurrPlayer().getId());
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
        game.dealAdventureCards(1, 1);
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
        assertEquals(12, game.getPlayer(1).getHand().size());
        game.dealAdventureCards(1, 1);
        assertEquals(13, game.getPlayer(1).getHand().size());
        game.checkTrim(game.getPlayer(1));
        assertEquals(12, game.getPlayer(1).getHand().size());
    }

    @Test
    @DisplayName("Check If Card Is Discarded Correctly")
    public void RESP_04_test_04() {
        Game game = new Game();
        ArrayList<String> commands = new ArrayList<>();
        commands.add("1");
        game.setCommands(commands);
        game.dealInitialAdventureCards();
        game.getPlayer(1).addCard(new Card(Card.CardType.FOE, "F5", 10));
        game.checkTrim(game.getPlayer(1));
        assertEquals(1, game.getAdventureDeck().getDiscardSize());
        assertEquals("F5", game.getAdventureDeck().getDiscardCards().getFirst().getName());
    }

    /*------------------------------------RESP-5-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check If Player Has Drawn Event Card")
    public void RESP_05_test_01() {
        Game game = new Game();
        game.getEventDeck().addCard(new Card(Card.CardType.EVENT, "Plague", 0));
        Player player1 = game.getPlayer(1);
        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "1", "Enter");
        game.setCommands(commands);
        game.processPlayerTurn(player1);
        assertNotNull(game.getDrawnCard());
        assertEquals("Plague", game.getDrawnCard().getName());
    }

    @Test
    @DisplayName("Check If Drawn Event Card is Updated In Deck")
    public void RESP_05_test_02() {
        Game game = new Game();
        game.getEventDeck().addCard(new Card(Card.CardType.EVENT, "Plague", 0));
        assertEquals(18, game.getEventDeck().getDeckSize());
        Player player1 = game.getPlayer(1);
        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "1", "Enter");
        game.setCommands(commands);
        game.processPlayerTurn(player1);
        assertEquals(17, game.getEventDeck().getDeckSize());
    }

    @Test
    @DisplayName("Check If Game Displays Drawn Event Card")
    public void RESP_05_test_03() {
        Game game = new Game();
        game.getEventDeck().addCard(new Card(Card.CardType.EVENT, "Plague", 0));
        Player player1 = game.getPlayer(1);
        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "1", "Enter");
        game.setCommands(commands);
        game.processPlayerTurn(player1);
        List<String> output = game.getOutputs();
        assertTrue(output.contains("P1 Drew Plague"));
    }

    /*------------------------------------RESP-6-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check If player loses 2 shields when the Plague card is drawn")
    public void RESP_06_test_01() {
        Game game = new Game();
        game.getEventDeck().addCard(new Card(Card.CardType.EVENT, "Plague", 0));
        Player player1 = game.getPlayer(1);
        player1.setSheilds(5);
        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "1", "Enter");
        game.setCommands(commands);
        assertEquals(5, player1.getSheilds());
        game.processPlayerTurn(player1);
        assertEquals(3, player1.getSheilds());

    }

    @Test
    @DisplayName("Check If Player Draws Plague and has less than 2 shield than it will still be 0")
    public void RESP_06_test_02() {
        Game game = new Game();
        game.getEventDeck().addCard(new Card(Card.CardType.EVENT, "Plague", 0));
        game.getEventDeck().addCard(new Card(Card.CardType.EVENT, "Plague", 0));

        Player player1 = game.getPlayer(1);
        player1.setSheilds(1);
        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "1", "Enter", "1", "Enter");
        game.setCommands(commands);
        assertEquals(1, player1.getSheilds());
        game.processPlayerTurn(player1);
        assertEquals(0, player1.getSheilds());
        game.processPlayerTurn(player1);
        assertEquals(0, player1.getSheilds());
    }

    /*------------------------------------RESP-7-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check If Player Draws Queens Favor than 2 adventure cards will be dealt")
    public void RESP_07_test_01() {
        Game game = new Game();
        Player player1 = game.getPlayer(1);
        game.dealInitialAdventureCards();
        assertEquals(12, player1.getHand().size());
        game.handleEventCard(new Card(Card.CardType.EVENT, "Queens Favor", 0), player1);
        assertEquals(14, player1.getHand().size());
    }

    @Test
    @DisplayName("Check If Player Draws Queens Favor than it will Trim player hand back to 12 cards")
    public void RESP_07_test_02() {
        Game game = new Game();
        Player player1 = game.getPlayer(1);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "Enter", "1", "1");
        game.setCommands(commands);

        game.dealInitialAdventureCards();
        assertEquals(12, player1.getHand().size());
        game.handleEventCard(new Card(Card.CardType.EVENT, "Queens Favor", 0), player1);
        assertEquals(14, player1.getHand().size());
        boolean isTrimmed = game.checkTrim(player1);
        assertEquals(12, player1.getHand().size());
        assertTrue(isTrimmed);
    }

    @Test
    @DisplayName("Check If Deck and Discard Updates Number of Cards after Queens Favor")
    public void RESP_07_test_03() {
        Game game = new Game();
        Player player1 = game.getPlayer(1);
        game.getEventDeck().addCard(new Card(Card.CardType.EVENT, "Queens Favor", 0));

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "Enter", "1", "1", "Enter");
        game.setCommands(commands);
        game.dealAdventureCards(12, 1);
        game.processPlayerTurn(player1);
        assertEquals(86, game.getAdventureDeck().getDeckSize());
        assertEquals(2, game.getAdventureDeck().getDiscardSize());
    }

    /*------------------------------------RESP-8-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check If Player Draws Prosperity Favor than 2 adventure cards will be dealt")
    public void RESP_08_test_01() {
        Game game = new Game();
        Player player1 = game.getPlayer(1);
        Player player2 = game.getPlayer(2);
        Player player3 = game.getPlayer(3);
        Player player4 = game.getPlayer(4);

        game.dealInitialAdventureCards();
        game.handleEventCard(new Card(Card.CardType.EVENT, "Prosperity", 0), player1);
        assertEquals(14, player1.getHand().size());
        assertEquals(14, player2.getHand().size());
        assertEquals(14, player3.getHand().size());
        assertEquals(14, player4.getHand().size());
    }

    @Test
    @DisplayName("Check If Player Draws Prosperity than 2 adventure cards will be dealt")
    public void RESP_08_test_02() {
        Game game = new Game();
        Player player1 = game.getPlayer(1);
        Player player2 = game.getPlayer(2);
        Player player3 = game.getPlayer(3);
        Player player4 = game.getPlayer(4);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "1", "1", "1", "1", "1", "1", "1", "1");
        game.setCommands(commands);

        game.dealInitialAdventureCards();
        game.handleEventCard(new Card(Card.CardType.EVENT, "Prosperity", 0), player1);

        assertTrue(game.checkTrim(player1));
        assertTrue(game.checkTrim(player2));
        assertTrue(game.checkTrim(player3));
        assertTrue(game.checkTrim(player4));

        assertEquals(12, player1.getHand().size());
        assertEquals(12, player2.getHand().size());
        assertEquals(12, player3.getHand().size());
        assertEquals(12, player4.getHand().size());
    }

    @Test
    @DisplayName("Check If Deck and Discard Updates Number of Cards after Prosperity")
    public void RESP_08_test_03() {
        Game game = new Game();
        Player player1 = game.getPlayer(1);
        Player player2 = game.getPlayer(2);
        Player player3 = game.getPlayer(3);
        Player player4 = game.getPlayer(4);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "1", "1", "1", "1", "1", "1", "1", "1");
        game.setCommands(commands);

        game.dealInitialAdventureCards();
        game.handleEventCard(new Card(Card.CardType.EVENT, "Prosperity", 0), player1);

        game.checkTrim(player1);
        game.checkTrim(player2);
        game.checkTrim(player3);
        game.checkTrim(player4);

        assertEquals(44, game.getAdventureDeck().getDeckSize());
        assertEquals(8, game.getAdventureDeck().getDiscardSize());
    }

    /*------------------------------------RESP-9-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check If game identifies a quest and prompts for the Current Player to sponsor")
    public void RESP_09_test_01() {
        Game game = new Game();
        game.getEventDeck().addCard(new Card(Card.CardType.QUEST, "Q2", 2));
        game.shuffleAdventureDeck();
        game.dealInitialAdventureCards();

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "Enter", "No", "No", "No", "No", "End Turn");
        game.setCommands(commands);

        Player player1 = game.getPlayer(1);
        game.processPlayerTurn(player1);
        List<String> outputs = game.getOutputs();

        assertTrue(outputs.contains("Would P1 like to sponsor the quest Q2? Yes/No"));
    }

    @Test
    @DisplayName("Check If game identifies a quest and prompts for the Current Player to sponsor (in which they decline) it goes onto next person")
    public void RESP_09_test_02() {
        Game game = new Game();
        game.getEventDeck().addCard(new Card(Card.CardType.QUEST, "Q2", 2));
        game.shuffleAdventureDeck();
        game.dealInitialAdventureCards();

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "Enter", "No", "No", "No", "No", "End Turn");
        game.setCommands(commands);

        Player player1 = game.getPlayer(1);
        game.processPlayerTurn(player1);
        List<String> outputs = game.getOutputs();
        assertTrue(outputs.contains("Would P1 like to sponsor the quest Q2? Yes/No"));
        assertEquals("Would P2 like to sponsor the quest Q2? Yes/No", outputs.get(outputs.indexOf("Would P1 like to sponsor the quest Q2? Yes/No") + 1));
    }

    @Test
    @DisplayName("Check If the sponsorship prompt goes through all the players")
    public void RESP_09_test_03() {
        Game game = new Game();
        game.getEventDeck().addCard(new Card(Card.CardType.QUEST, "Q2", 2));
        game.shuffleAdventureDeck();
        game.dealInitialAdventureCards();

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "Enter", "No", "No", "No", "No", "End Turn");
        game.setCommands(commands);

        Player player1 = game.getPlayer(1);
        game.processPlayerTurn(player1);
        List<String> outputs = game.getOutputs();

        assertTrue(outputs.contains("Would P1 like to sponsor the quest Q2? Yes/No"));
        assertTrue(outputs.contains("Would P2 like to sponsor the quest Q2? Yes/No"));
        assertTrue(outputs.contains("Would P3 like to sponsor the quest Q2? Yes/No"));
        assertTrue(outputs.contains("Would P4 like to sponsor the quest Q2? Yes/No"));
    }

    @Test
    @DisplayName("Check If the sponsored player is found when player accepts")
    public void RESP_09_test_04() {
        Game game = new Game();
        game.shuffleAdventureDeck();
        game.dealInitialAdventureCards();

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands, "No", "No", "Yes");
        game.setCommands(commands);

        Player player1 = game.getPlayer(1);
        Player currSponsor = game.findQuestSponsor(2, player1);

        assertNotNull(currSponsor);
        assertEquals(3, currSponsor.getId());
    }
    /*------------------------------------RESP-10-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check if all players have declined sponsorship")
    public void RESP_10_test_01() {
        Game game = new Game();
        game.getEventDeck().addCard(new Card(Card.CardType.QUEST, "Q2", 2));
        game.shuffleAdventureDeck();
        game.dealInitialAdventureCards();

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"No","No","No","No","End Turn");
        game.setCommands(commands);

        Player player1 = game.getPlayer(1);
        Player res = game.findQuestSponsor(3,player1);
        assertNull(res);
    }

    @Test
    @DisplayName("Check if quest is discarded if all players decline to sponsor")
    public void RESP_10_test_02() {
        Game game = new Game();
        game.shuffleAdventureDeck();
        game.getEventDeck().addCard(new Card(Card.CardType.QUEST, "Q2", 2));
        game.dealInitialAdventureCards();

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Draw","No","No","No","No","End Turn");
        game.setCommands(commands);

        Player player1 = game.getPlayer(1);
        game.processPlayerTurn(player1);
        assertEquals(1,game.getEventDeck().getDiscardSize());
    }
    /*------------------------------------RESP-11-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check if sponsor is prompted to set up stages")
    public void RESP_11_test_01() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        List<Card> sponsorHand = new ArrayList<>();
        Collections.addAll(sponsorHand,new Card(Card.CardType.FOE,"F5",5),new Card(Card.CardType.FOE,"F10",10));
        sponsor.setHand(sponsorHand);

        Quest currQuest = new Quest(2,sponsor,game);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"1","Quit","1","Quit","Enter");

        game.setCommands(commands);

        List<String> outputs = game.getOutputs();
        currQuest.buildQuest();

        assertTrue(outputs.contains("P1 Will Begin the Stage Building Process"));
        assertTrue(outputs.contains("Select the position of the card to include in Stage #1 or enter Quit to stop building stage"));
    }

    @Test
    @DisplayName("Check if sponsor is able to setup stage cards")
    public void RESP_11_test_02() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        List<Card> sponsorHand = new ArrayList<>();
        Collections.addAll(sponsorHand,new Card(Card.CardType.FOE,"F5",5),new Card(Card.CardType.FOE,"F10",10));
        sponsor.setHand(sponsorHand);

        Quest currQuest = new Quest(2,sponsor,game);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"1","Quit","1","Quit","Enter");

        game.setCommands(commands);
        assertEquals(0,currQuest.getStageCards().size());
        currQuest.buildQuest();
        assertEquals(2,currQuest.getStageCards().size());
    }
    /*------------------------------------RESP-12-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check if each stage of quest is validated for being non-empty")
    public void RESP_12_test_01() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        List<Card> sponsorHand = new ArrayList<>();
        Collections.addAll(sponsorHand,new Card(Card.CardType.FOE,"F5",5),new Card(Card.CardType.FOE,"F10",10));
        sponsor.setHand(sponsorHand);

        Quest currQuest = new Quest(2,sponsor,game);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Quit","1","Quit","Quit","1","Quit","Enter");

        game.setCommands(commands);

        List<String> outputs = game.getOutputs();
        currQuest.buildQuest();

        assertEquals(2,Collections.frequency(outputs,"Stage cannot be Empty"));
    }

    @Test
    @DisplayName("Check if each stage validates for current stage value being greater than previous")
    public void RESP_12_test_02() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        List<Card> sponsorHand = new ArrayList<>();
        Collections.addAll(sponsorHand,new Card(Card.CardType.FOE,"F5",5),new Card(Card.CardType.FOE,"F10",10), new Card(Card.CardType.WEAPON,"E",30));
        sponsor.setHand(sponsorHand);

        Quest currQuest = new Quest(2,sponsor,game);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"2","Quit","1","Quit","1","Quit","Enter");

        game.setCommands(commands);

        List<String> outputs = game.getOutputs();
        currQuest.buildQuest();

        assertTrue(outputs.contains("Insufficient value for this stage"));
    }

    @Test
    @DisplayName("Check if each stage has ATLEAST 1 Foe Card")
    public void RESP_12_test_03() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        List<Card> sponsorHand = new ArrayList<>();
        Collections.addAll(sponsorHand,new Card(Card.CardType.FOE,"F5",5),new Card(Card.CardType.FOE,"F20",20), new Card(Card.CardType.WEAPON,"D",5));
        sponsor.setHand(sponsorHand);

        Quest currQuest = new Quest(2,sponsor,game);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"3","Quit","1","Quit","1","Quit","Enter");

        game.setCommands(commands);

        List<String> outputs = game.getOutputs();
        currQuest.buildQuest();

        assertTrue(outputs.contains("Stage cannot have no foe"));
    }

    @Test
    @DisplayName("Check that weapon cards are not repeated for a stage")
    public void RESP_12_test_04() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        List<Card> sponsorHand = new ArrayList<>();
        Collections.addAll(sponsorHand,new Card(Card.CardType.FOE,"F5",5),new Card(Card.CardType.FOE,"F5",5), new Card(Card.CardType.WEAPON,"D",5), new Card(Card.CardType.WEAPON,"D",5), new Card(Card.CardType.WEAPON,"H",10));
        sponsor.setHand(sponsorHand);

        Quest currQuest = new Quest(2,sponsor,game);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"1","Quit","1","1","1","Quit","Enter");

        game.setCommands(commands);

        List<String> outputs = game.getOutputs();
        currQuest.buildQuest();

        assertTrue(outputs.contains("Cannot have repeated weapon card in Stage"));
    }
    @Test
    @DisplayName("Check each participant is prompted to participate or withdraw")
    public void RESP_13_test_01() {
        Game game = new Game();
        List<Card> playerHands = new ArrayList<>();
        Collections.addAll(playerHands,new Card(Card.CardType.FOE,"F5",5),new Card(Card.CardType.FOE,"F5",5), new Card(Card.CardType.WEAPON,"D",5), new Card(Card.CardType.WEAPON,"D",5), new Card(Card.CardType.WEAPON,"H",10));

        for(int i=1;i<game.getPlayers().size();i++){
            game.getPlayer(i+1).setHand(playerHands);
        }

        Player sponsor = game.getPlayer(1);
        Quest currQuest = new Quest(2,sponsor,game);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Yes","Yes","Yes");
        game.setCommands(commands);

        List<String> outputs = game.getOutputs();

        List<Player> participants = currQuest.getParticipantsForQuest(sponsor);

        assertTrue(outputs.contains("Would P2 like to participate? Yes/No"));
        assertTrue(outputs.contains("Would P3 like to participate? Yes/No"));
        assertTrue(outputs.contains("Would P4 like to participate? Yes/No"));
    }
    @Test
    @DisplayName("Check if player who withdraws is marked as ineligible for subsequent stages.")
    public void RESP_13_test_02() {
        Game game = new Game();
        List<Card> playerHands = new ArrayList<>();
        Collections.addAll(playerHands,new Card(Card.CardType.FOE,"F5",5),new Card(Card.CardType.FOE,"F5",5), new Card(Card.CardType.WEAPON,"D",5), new Card(Card.CardType.WEAPON,"D",5), new Card(Card.CardType.WEAPON,"H",10));

        for(int i=1;i<game.getPlayers().size();i++){
            game.getPlayer(i+1).setHand(playerHands);
        }

        Player sponsor = game.getPlayer(1);
        Quest currQuest = new Quest(2,sponsor,game);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Yes","Yes","No");
        game.setCommands(commands);

        List<String> outputs = game.getOutputs();

        List<Player> participants = currQuest.getParticipantsForQuest(sponsor);

        assertEquals(2,participants.size());
        assertEquals(true,game.getPlayer(4).getDeclinedToParticipate());
    }
    /*------------------------------------RESP-14-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check if participants draw an adventure card at the start of each stage and trims")
    public void RESP_14_test_01() {
        Game game = new Game();
        game.shuffleAdventureDeck();
        game.dealInitialAdventureCards();
        Player sponsor = game.getPlayer(1);
        Player player = game.getPlayer(2);
        game.getAdventureDeck().addCard(new Card(Card.CardType.WEAPON,"E",30));

        Quest currQuest = new Quest(2,sponsor,game);
        List<Card> originalHand = new ArrayList<>(player.getHand());
        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"1");
        game.setCommands(commands);

        currQuest.handleBeginningStage(game.getPlayer(2));
        assertFalse(originalHand.equals(player.getHand()));
    }
    /*------------------------------------RESP-15-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check if Game prompts participants to set up attack")
    public void RESP_15_test_01() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        Player player = game.getPlayer(2);
        game.getPlayer(2).addCard(new Card(Card.CardType.WEAPON,"E",30));

        Quest currQuest = new Quest(2,sponsor,game);
        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"1","Quit");
        game.setCommands(commands);

        List<String> outputs = game.getOutputs();

        currQuest.buildAttack(player);
        assertTrue(outputs.contains("Select the position of the card to include in your attack or type 'Quit' when finished"));
    }

    @Test
    @DisplayName("Check if Game validates attacks (Player Chooses Foe Card")
    public void RESP_15_test_02() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        Player player = game.getPlayer(2);
        game.getPlayer(2).addCard(new Card(Card.CardType.WEAPON,"E",30));
        game.getPlayer(2).addCard(new Card(Card.CardType.FOE,"F5",5));


        Quest currQuest = new Quest(2,sponsor,game);
        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"1","2","Quit");
        game.setCommands(commands);

        List<String> outputs = game.getOutputs();

        currQuest.buildAttack(player);
        assertTrue(outputs.contains("Cannot use FOE card in attack"));
    }

    @Test
    @DisplayName("Check if Game validates attacks (Player Chooses Repeated Card")
    public void RESP_15_test_03() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        Player player = game.getPlayer(2);
        game.getPlayer(2).addCard(new Card(Card.CardType.WEAPON,"D",5));
        game.getPlayer(2).addCard(new Card(Card.CardType.WEAPON,"E",30));
        game.getPlayer(2).addCard(new Card(Card.CardType.WEAPON,"E",30));


        Quest currQuest = new Quest(2,sponsor,game);
        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"2","2","1","Quit");
        game.setCommands(commands);

        List<String> outputs = game.getOutputs();

        currQuest.buildAttack(player);
        assertTrue(outputs.contains("You cannot use the same card twice"));
    }
    @Test
    @DisplayName("Check Game resolves each attack by comparing the attack value to the stage difficulty")
    public void RESP_16_test_01() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);
        Player player = game.getPlayer(2);
        game.getPlayer(2).addCard(new Card(Card.CardType.WEAPON,"D",5));
        game.getPlayer(2).addCard(new Card(Card.CardType.WEAPON,"E",30));
        game.getPlayer(2).addCard(new Card(Card.CardType.WEAPON,"E",30));

        Quest currQuest = new Quest(2,sponsor,game);
        currQuest.getStageValues().add(0);
        currQuest.getStageValues().add(10);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"3","Quit","Enter");
        game.setCommands(commands);

        boolean res = currQuest.buildAndResolveAttack(player,1);
        assertTrue(res);
    }

    @Test
    @DisplayName("Check that participants who fail an attack are removed from the quest")
    public void RESP_16_test_02(){
        Game game = new Game();
        game.dealInitialAdventureCards();
        game.getEventDeck().addCard(new Card(Card.CardType.QUEST, "Q2", 2));
        game.getAdventureDeck().addCard(new Card(Card.CardType.WEAPON, "S", 10));  // Sword card (value 10)

        Player sponsor = game.getPlayer(1);

        Quest quest = new Quest(2, sponsor, game);
        quest.getStageValues().add(0);
        quest.getStageValues().add(30);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Yes","No","No","1","12","Quit","Enter");
        game.setCommands(commands);

        List<Player> successfulParticipants = quest.startQuest();
        assertTrue(successfulParticipants.isEmpty());
    }
    @Test
    @DisplayName("Check that the cards used by participants for an attack are discarded after each stage")
    public void RESP_17_test_01(){
        Game game = new Game();
        game.dealInitialAdventureCards();
        game.getEventDeck().addCard(new Card(Card.CardType.QUEST, "Q2", 2));
        game.getAdventureDeck().addCard(new Card(Card.CardType.WEAPON, "S", 10));

        Player sponsor = game.getPlayer(1);

        Quest quest = new Quest(2, sponsor, game);
        quest.getStageValues().add(0);
        quest.getStageValues().add(30);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Yes","No","No","1","12","Quit","Enter");
        game.setCommands(commands);

        List<Player> successfulParticipants = quest.startQuest();
        assertEquals(2,game.getAdventureDeck().getDiscardSize());
    }

    @Test
    @DisplayName("Check the game updates the participants hands after discarding cards")
    public void RESP_17_test_02(){
        Game game = new Game();
        game.dealInitialAdventureCards();
        game.getEventDeck().addCard(new Card(Card.CardType.QUEST, "Q2", 2));
        game.getAdventureDeck().addCard(new Card(Card.CardType.WEAPON, "S", 10));

        Player sponsor = game.getPlayer(1);

        Quest quest = new Quest(2, sponsor, game);
        quest.getStageValues().add(0);
        quest.getStageValues().add(30);

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Yes","No","No","1","12","Quit","Enter");
        game.setCommands(commands);

        List<Player> successfulParticipants = quest.startQuest();
        assertEquals(11,game.getPlayer(2).getHand().size());
    }
    /*------------------------------------RESP-18-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check the game awards shields based on number of stages")
    public void RESP_18_test_01(){
        Game game = new Game();
        List<Player> winners = new ArrayList<>();
        winners.add(game.getPlayer(1));
        winners.add(game.getPlayer(2));

        game.getPlayer(1).setSheilds(2);
        game.getPlayer(2).setSheilds(4);


        Player sponsor = game.getPlayer(1);
        int numStages = 2;

        Quest quest = new Quest(numStages, sponsor, game);

        assertEquals(2,game.getPlayer(1).getSheilds());
        assertEquals(4,game.getPlayer(2).getSheilds());
        assertEquals(0,game.getPlayer(3).getSheilds());
        assertEquals(0,game.getPlayer(4).getSheilds());

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Enter");
        game.setCommands(commands);

        quest.finishQuest(winners);

        assertEquals(2+numStages,game.getPlayer(1).getSheilds());
        assertEquals(4+numStages,game.getPlayer(2).getSheilds());
        assertEquals(0,game.getPlayer(3).getSheilds());
        assertEquals(0,game.getPlayer(4).getSheilds());
    }

    @Test
    @DisplayName("Check the game awards shields to winners of quest")
    public void RESP_18_test_02(){
        Game game = new Game();
        List<Player> winners = new ArrayList<>();
        winners.add(game.getPlayer(1));

        Player sponsor = game.getPlayer(1);

        Quest quest = new Quest(2, sponsor, game);

        assertEquals(0,game.getPlayer(1).getSheilds());
        assertEquals(0,game.getPlayer(2).getSheilds());
        assertEquals(0,game.getPlayer(3).getSheilds());
        assertEquals(0,game.getPlayer(4).getSheilds());

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Enter");
        game.setCommands(commands);

        quest.finishQuest(winners);

        assertEquals(2,game.getPlayer(1).getSheilds());
        assertEquals(0,game.getPlayer(2).getSheilds());
        assertEquals(0,game.getPlayer(3).getSheilds());
        assertEquals(0,game.getPlayer(4).getSheilds());
    }
    /*------------------------------------RESP-19-----------------------------------------------------------------*/
    @Test
    @DisplayName("Check that sponsor draws the correct number of cards after the quest ends")
    public void RESP_19_test_01() {
        Game game = new Game();
        Player sponsor = game.getPlayer(1);

        Quest quest = new Quest(2, sponsor, game);
        quest.getStageCards().add(new Card(Card.CardType.FOE,"F5",5));
        quest.getStageCards().add(new Card(Card.CardType.FOE,"F10",10));
        quest.getStageCards().add(new Card(Card.CardType.FOE,"F15",15));
        quest.getStageCards().add(new Card(Card.CardType.FOE,"F20",20));
        quest.getStageCards().add(new Card(Card.CardType.FOE,"F25",25));

        assertEquals(0,sponsor.getHand().size());

        ArrayList<String> commands = new ArrayList<>();
        Collections.addAll(commands,"Enter");
        game.setCommands(commands);

        quest.finishQuest(new ArrayList<>());
        assertEquals(0,quest.getStageCards().size());
        assertEquals(5,game.getAdventureDeck().getDiscardSize());

    }
}

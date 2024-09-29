package main;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TopScoresManagerTest {
    private TopScoresManager topScoresManager;

    @Before
    public void setUp() {
        topScoresManager = new TopScoresManager();
    }

    @Test
    public void testAddValidScore() {
        topScoresManager.addScore("Alice", 100);
        assertEquals(4, topScoresManager.getTopScores().size());
        assertEquals("Bob", topScoresManager.getTopScores().get(0).getName());
        assertEquals(300, topScoresManager.getTopScores().get(0).getScore());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddScoreWithEmptyName() {
        topScoresManager.addScore("", 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddScoreWithNegativeValue() {
        topScoresManager.addScore("Bob", -50);
    }

    @Test
    public void testTopScoresAreSorted() {
        topScoresManager.addScore("Alice", 100);
        topScoresManager.addScore("Bob", 200);
        assertEquals("Bob", topScoresManager.getTopScores().get(0).getName());
        assertEquals(300, topScoresManager.getTopScores().get(0).getScore());
    }

    @Test
    public void testAddMultipleScores() {
        topScoresManager.addScore("Alice", 100);
        topScoresManager.addScore("Bob", 300);
        topScoresManager.addScore("Charlie", 200);
        assertEquals(3, topScoresManager.getTopScores().size());
        assertEquals("Bob", topScoresManager.getTopScores().get(0).getName());
        assertEquals(300, topScoresManager.getTopScores().get(0).getScore());
    }
}

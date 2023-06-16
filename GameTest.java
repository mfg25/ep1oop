package ep1coo;

import static org.junit.Assert.*; 

import org.junit.Test;

public class GameTest {

	@Test
	public void test() {
		GameImpl game = new GameImpl();
		int size = game.getBoard().length;
		int blueSpots = 0, redSpots = 0;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(game.getBoard()[i][j].getColor() == Color.RED) redSpots++;
				if(game.getBoard()[i][j].getColor() == Color.BLUE) blueSpots++;
			}
		}
		
		assertEquals(1, blueSpots);
		assertEquals(1, redSpots);
	}
	
	@Test
	public void test1() {
		GameImpl game = new GameImpl();
		game.getBlueMaster().kill();
		assertTrue(game.checkVictory(Color.RED));
	}
	
	@Test
	public void test2() {
		GameImpl game = new GameImpl();
		game.getRedMaster().kill();
		assertTrue(game.checkVictory(Color.BLUE));
	}
	
	@Test
	public void test3() {
		GameImpl game = new GameImpl("vermelho", "azul");
		assertEquals("vermelho", game.getRedPlayer().getName());
		assertEquals("azul", game.getBluePlayer().getName());
	}
	
	@Test
	public void test4() {
		Card monkey = new Card("Monkey", Color.BLUE, new Position[]{new Position(-1, -1), new Position(1, 1), new Position(1, -1), new Position(-1, 1)});
		Card mantis = new Card("Mantis", Color.BLUE, new Position[]{new Position(-1, 0), new Position(1, -1), new Position(1, 1)});
		Card ox = new Card("OX", Color.RED, new Position[]{new Position(1, 0), new Position(0, 1), new Position(-1, 0)});
		Card cobra = new Card("Cobra", Color.RED, new Position[]{new Position(0, -1), new Position(1, 1), new Position(-1, 1)});
		Card eel = new Card("EEL", Color.RED, new Position[]{new Position(1, -1), new Position(-1, -1), new Position(0, 1)});
		GameImpl game = new GameImpl("vermelho", "azul", new Card[] {monkey, mantis, ox, cobra, eel});

		assertTrue(game.getBluePlayer().getCards().length == 2);
		assertTrue(game.getRedPlayer().getCards().length == 2);
	}
	
	

}

/**
 * 
 */
package devops_black_jack;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author mn
 *
 */
public class Tests {

	Deck d = new Deck();

	/**
	 * Test method for {@link devops_black_jack.Deck#add_decks(int)}.
	 */
	@Test
	public void testAdd_decks() {
		d.clear_deck();
		d.create_deck();
		d.add_decks(1);
		assertEquals("1 deck is 52 cards", 52, Deck.deck.size());
	}

	/**
	 * Test method for {@link devops_black_jack.Deck#draw_card()}.
	 */
	@Test
	public void testDraw_card() {
		d.create_deck();
		d.add_decks(1);
		Card c = d.draw_card();
		assertTrue(c.getValue() > 0);
	}

	/**
	 * Test method for {@link devops_black_jack.Deck#clear_deck()}.
	 */
	@Test
	public void testClear_deck() {
		d.clear_deck();
		assertEquals("Deck is empty",0,Deck.deck.size());
	}
	
	@Test
	public void testDb() {
		SQL sql = new SQL();
		String user = "testUser";
		String pass = "testPass";
		sql.setBalance(user, 1000d);
		double balance = sql.getBalance(user, pass);
		assertEquals("Testing Database write", 1000, (int)balance);
	}
	
	@Test
	public void testHit() {
		d.create_deck();
		d.shuffle_deck();
		Player p = new Player("testUser", null, 1000);
		p.Hit();
		p.Hit();
		assertTrue(p.getHand_Value() > 2); // Lowest value possible after two hits. 
	}
}

package dk.purplegreen.musiclibrary.service.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.Rule;
import org.junit.Test;

import dk.purplegreen.musiclibrary.test.Database;

public class ArtistTest {

	@Rule
	public Database database = new Database();

	@Test
	public void testCRUD() {

		Artist artist = new Artist("Black Sabbath");

		Session session = database.getSessionFactory().openSession();
		session.beginTransaction();

		session.save(artist);

		session.getTransaction().commit();
		session.close();

		assertNotNull("Artist id is null", artist.getId());

		session = database.getSessionFactory().openSession();
		session.beginTransaction();
		artist = session.find(Artist.class, artist.getId());

		assertNotNull("Artist is null", artist);
		assertEquals("Wrong Artist name", "Black Sabbath", artist.getName());

		artist.setName("Deep Purple");
		session.save(artist);

		session.getTransaction().commit();
		session.close();

		session = database.getSessionFactory().openSession();
		session.beginTransaction();
		artist = session.find(Artist.class, artist.getId());

		assertNotNull("Artist is null", artist);
		assertEquals("Wrong Artist name", "Deep Purple", artist.getName());

		session.delete(artist);

		session.getTransaction().commit();
		session.close();

		session = database.getSessionFactory().openSession();

		artist = session.find(Artist.class, artist.getId());
		assertNull("Artist is not null", artist);
		session.close();

	}

}

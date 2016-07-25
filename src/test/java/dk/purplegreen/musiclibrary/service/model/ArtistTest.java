package dk.purplegreen.musiclibrary.service.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import dk.purplegreen.musiclibrary.service.persistence.TestSessionFactory;

public class ArtistTest {

	private SessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {

		sessionFactory = TestSessionFactory.getSessionFactory();
	}

	@Test
	public void testCRUD() {

		Artist artist = new Artist("Black Sabbath");

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(artist);

		session.getTransaction().commit();
		session.close();

		assertNotNull("Artist id is null", artist.getId());

		session = sessionFactory.openSession();
		session.beginTransaction();
		artist = session.find(Artist.class, artist.getId());

		assertNotNull("Artist is null", artist);
		assertEquals("Wrong Artist name", "Black Sabbath", artist.getName());

		artist.setName("Deep Purple");
		session.save(artist);

		session.getTransaction().commit();
		session.close();

		session = sessionFactory.openSession();
		session.beginTransaction();
		artist = session.find(Artist.class, artist.getId());

		assertNotNull("Artist is null", artist);
		assertEquals("Wrong Artist name", "Deep Purple", artist.getName());

		session.delete(artist);

		session.getTransaction().commit();
		session.close();

		session = sessionFactory.openSession();

		artist = session.find(Artist.class, artist.getId());
		assertNull("Artist is not null", artist);
		session.close();

	}

}

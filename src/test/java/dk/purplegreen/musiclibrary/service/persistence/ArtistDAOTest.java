package dk.purplegreen.musiclibrary.service.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import dk.purplegreen.musiclibrary.service.model.Artist;

@RunWith(MockitoJUnitRunner.class)
public class ArtistDAOTest {

	@Mock
	SessionFactory sessionFactory;

	@InjectMocks
	private ArtistDAO artistDAO = new ArtistDAO();

	@Test
	public void testFind() {

		Session session = TestSessionFactory.getSessionFactory().openSession();

		when(sessionFactory.getCurrentSession()).thenReturn(session);

		Artist artist = artistDAO.find(TestSessionFactory.getRoyalHuntId());

		session.close();

		assertEquals("Wrong artist", "Royal Hunt", artist.getName());
	}

	@Test
	public void testGetAllArtists() {

		Session session = TestSessionFactory.getSessionFactory().openSession();

		when(sessionFactory.getCurrentSession()).thenReturn(session);

		List<Artist> artists = artistDAO.getAllArtists();

		session.close();

		assertTrue("No artists", artists.size() > 0);
	}
}

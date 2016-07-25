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

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;

@RunWith(MockitoJUnitRunner.class)
public class AlbumDAOTest {

	@Mock
	SessionFactory sessionFactory;

	@InjectMocks
	private AlbumDAO albumDAO;

	@Test
	public void testFindById() {

		Session session = TestSessionFactory.getSessionFactory().openSession();

		when(sessionFactory.getCurrentSession()).thenReturn(session);

		Album album = albumDAO.find(TestSessionFactory.getParadoxId());

		session.close();

		assertEquals("Wrong artist", "Paradox", album.getTitle());
	}

	@Test
	public void testGetAllAlbums() {
		Session session = TestSessionFactory.getSessionFactory().openSession();

		when(sessionFactory.getCurrentSession()).thenReturn(session);

		List<Album> albums = albumDAO.getAllAlbums();

		session.close();

		assertTrue("No albums", albums.size() > 0);
	}

	@Test
	public void testFindByArtist() {

		Session session = TestSessionFactory.getSessionFactory().openSession();

		when(sessionFactory.getCurrentSession()).thenReturn(session);

		Artist artist = new Artist();
		artist.setId(TestSessionFactory.getRoyalHuntId());

		List<Album> albums = albumDAO.findByArtist(artist);
		session.close();

		assertEquals("Wrong number of albums", 1, albums.size());
	}

	@Test
	public void testFind() {

		Session session = TestSessionFactory.getSessionFactory().openSession();

		when(sessionFactory.getCurrentSession()).thenReturn(session);

		List<Album> albums = albumDAO.find(null, null, null);

		assertTrue("Wrong number of albums", albums.size() >= 3);

		albums = albumDAO.find("royal", null, null);

		assertEquals("Wrong number of albums", 1, albums.size());
		
		albums = albumDAO.find(null, "THOSE", null);

		assertEquals("Wrong number of albums", 1, albums.size());
		
		albums = albumDAO.find(null, "THOSE", 1969);

		assertEquals("Wrong number of albums", 0, albums.size());

		albums = albumDAO.find("beatles", "abbey", 1969);

		assertEquals("Wrong number of albums", 1, albums.size());
		
		session.close();

	}
}

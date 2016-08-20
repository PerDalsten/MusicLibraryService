package dk.purplegreen.musiclibrary.service.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;

@RunWith(MockitoJUnitRunner.class)
public class ArtistDAOTest {

	@Mock
	private EntityManager em;

	@InjectMocks
	private ArtistDAO artistDAO = new ArtistDAO();

	@Test
	public void testFind() {

		EntityManager testEM = TestSessionFactory.getEntityManager();

		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		when(em.find(any(), argument.capture())).then(new Answer<Artist>() {
			@Override
			public Artist answer(InvocationOnMock invocation) {
				return testEM.find(Artist.class, argument.getValue());
			}
		});

		Artist artist = artistDAO.find(TestSessionFactory.getRoyalHuntId());

		testEM.close();

		assertEquals("Wrong artist", "Royal Hunt", artist.getName());
	}

	@Test
	public void testGetAllArtists() {

		EntityManager testEM = TestSessionFactory.getEntityManager();

		when(em.createNamedQuery("findAllArtists", Artist.class))
				.thenReturn(testEM.createNamedQuery("findAllArtists", Artist.class));

		List<Artist> artists = artistDAO.getAllArtists();

		testEM.close();

		assertTrue("No artists", artists.size() > 0);
	}

}

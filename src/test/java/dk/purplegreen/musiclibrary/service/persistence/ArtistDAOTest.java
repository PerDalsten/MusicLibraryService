package dk.purplegreen.musiclibrary.service.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import dk.purplegreen.musiclibrary.service.model.Artist;
import dk.purplegreen.musiclibrary.test.Database;

@RunWith(MockitoJUnitRunner.class)
public class ArtistDAOTest {

	@Rule
	public Database database = new Database();

	@Mock
	private EntityManager em;

	@InjectMocks
	private ArtistDAO artistDAO = new ArtistDAO();

	@Test
	public void testFind() {

		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		when(em.find(any(), argument.capture())).then(new Answer<Artist>() {
			@Override
			public Artist answer(InvocationOnMock invocation) {
				return database.getEntityManager().find(Artist.class, argument.getValue());
			}
		});

		Artist artist = artistDAO.find(2);

		assertEquals("Wrong artist", "Royal Hunt", artist.getName());
	}

	@Test
	public void testGetAllArtists() {

		when(em.createNamedQuery("findAllArtists", Artist.class))
				.thenReturn(database.getEntityManager().createNamedQuery("findAllArtists", Artist.class));

		List<Artist> artists = artistDAO.getAllArtists();

		assertTrue("No artists", artists.size() > 0);
	}

}

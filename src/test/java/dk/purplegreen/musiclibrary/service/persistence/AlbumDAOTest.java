package dk.purplegreen.musiclibrary.service.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;
import dk.purplegreen.musiclibrary.test.Database;

@RunWith(MockitoJUnitRunner.class)
public class AlbumDAOTest {

	@Rule
	public Database database = new Database();

	@Mock
	private EntityManager em;

	@InjectMocks
	private AlbumDAO albumDAO = new AlbumDAO();

	@Test
	public void testFindById() {

		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		when(em.find(any(), argument.capture())).then(new Answer<Album>() {
			@Override
			public Album answer(InvocationOnMock invocation) {
				return database.getEntityManager().find(Album.class, argument.getValue());
			}
		});

		Album album = albumDAO.find(2);

		assertNotNull("Album is null", album);
		assertEquals("Wrong artist", "Royal Hunt", album.getArtist().getName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByArtist() {

		when(em.getCriteriaBuilder()).thenReturn(database.getEntityManager().getCriteriaBuilder());
		@SuppressWarnings("rawtypes")
		ArgumentCaptor<CriteriaQuery> argument = ArgumentCaptor.forClass(CriteriaQuery.class);
		when(em.createQuery(argument.capture())).then(new Answer<TypedQuery<Album>>() {
			public TypedQuery<Album> answer(InvocationOnMock invocation) {
				return database.getEntityManager().createQuery(argument.getValue());
			}
		});

		List<Album> albums = albumDAO.find("beatles", null, null);

		assertEquals("Wrong number of results", 1, albums.size());
		assertEquals("Wrong artist", "The Beatles", albums.get(0).getArtist().getName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindSort() {

		when(em.getCriteriaBuilder()).thenReturn(database.getEntityManager().getCriteriaBuilder());
		@SuppressWarnings("rawtypes")
		ArgumentCaptor<CriteriaQuery> argument = ArgumentCaptor.forClass(CriteriaQuery.class);
		when(em.createQuery(argument.capture())).then(new Answer<TypedQuery<Album>>() {
			public TypedQuery<Album> answer(InvocationOnMock invocation) {
				return database.getEntityManager().createQuery(argument.getValue());
			}
		});

		List<Album> albums = albumDAO.find(null, null, null);
		assertEquals("Wrong artist", "AC/DC", albums.get(0).getArtist().getName());

	}

	@Test
	public void testGetArtistAlbumCount() {

		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		when(em.createQuery(argument.capture())).then(new Answer<Query>() {
			public Query answer(InvocationOnMock invocation) {
				return database.getEntityManager().createQuery(argument.getValue());
			}
		});

		Artist artist = new Artist();
		artist.setId(1);

		assertEquals("Wrong album count", new Integer(1), albumDAO.getArtistAlbumCount(artist));
	}

	@Test
	public void testCreate() {
		Album album = new Album();

		albumDAO.save(album);

		verify(em, times(0)).merge(any());
	}

	@Test
	public void testUpdate() {
		Album album = new Album();
		album.setId(42);

		albumDAO.save(album);

		verify(em, times(1)).merge(any());
	}
}

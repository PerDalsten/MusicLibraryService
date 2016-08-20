package dk.purplegreen.musiclibrary.service.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import dk.purplegreen.musiclibrary.service.model.Artist;

@Repository
public class ArtistDAO {

	@PersistenceContext
	private EntityManager em;

	public Artist save(Artist artist) {

		if (artist.getId() == null) {
			em.persist(artist);
		} else {
			artist = em.merge(artist);
		}
		return artist;
	}

	public Artist find(Integer id) {
		return em.find(Artist.class, id);
	}

	public void delete(Artist artist) {
		em.remove(em.merge(artist));
	}

	public List<Artist> getAllArtists() {
		TypedQuery<Artist> query = em.createNamedQuery("findAllArtists", Artist.class);
		return query.getResultList();
	}
}

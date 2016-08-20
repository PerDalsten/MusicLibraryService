package dk.purplegreen.musiclibrary.service.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;

@Repository
public class AlbumDAO {

	@PersistenceContext
	private EntityManager em;

	public Album save(Album album) {

		if (album.getId() == null) {
			em.persist(album);
		} else {
			album = (Album) em.merge(album);
		}
		return album;
	}

	public Album find(Integer id) {
		return em.find(Album.class, id);
	}

	public void delete(Album album) {
		em.remove(em.merge(album));
	}

	public List<Album> getAllAlbums() {
		TypedQuery<Album> query = em.createNamedQuery("findAllAlbums", Album.class);
		return query.getResultList();
	}

	public List<Album> findByArtist(Artist artist) {
		TypedQuery<Album> query = em.createNamedQuery("findByArtist", Album.class);
		query.setParameter("artist", artist);
		return query.getResultList();
	}

	public List<Album> find(String artist, String title, Integer year) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Album> cq = cb.createQuery(Album.class);
		Root<Album> album = cq.from(Album.class);

		List<Predicate> predicates = new ArrayList<>();

		if (artist != null && !artist.isEmpty()) {
			predicates.add(cb.like(cb.lower(album.get("artist").get("name")), "%" + artist.toLowerCase() + "%"));
		}
		if (title != null && !title.isEmpty()) {
			predicates.add(cb.like(cb.lower(album.get("title")), "%" + title.toLowerCase() + "%"));
		}
		if (year != null) {
			predicates.add(cb.equal(album.get("year"), year));
		}

		cq.select(album).where(predicates.toArray(new Predicate[predicates.size()]));
		cq.orderBy(cb.asc(album.get("artist").get("name")), cb.asc(album.get("title")));

		return em.createQuery(cq).getResultList();
	}

}

package dk.purplegreen.musiclibrary.service.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dk.purplegreen.musiclibrary.service.model.Artist;

@Repository
public class ArtistDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Artist save(Artist artist) {

		Session session = sessionFactory.getCurrentSession();

		if (artist.getId() == null) {
			session.persist(artist);
		} else {
			artist = (Artist) session.merge(artist);
		}
		return artist;
	}

	public Artist find(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return session.find(Artist.class, id);
	}

	public void delete(Artist artist) {
		Session session = sessionFactory.getCurrentSession();
		session.remove(session.merge(artist));
	}

	public List<Artist> getAllArtists() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<Artist> query = session.createNamedQuery("findAllArtists", Artist.class);
		return query.getResultList();
	}
}

package dk.purplegreen.musiclibrary.service.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;

@Repository
public class AlbumDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Album save(Album album) {

		Session session = sessionFactory.getCurrentSession();

		if (album.getId() == null) {
			session.persist(album);
		} else {
			album = (Album) session.merge(album);
		}
		return album;
	}

	public Album find(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return session.find(Album.class, id);
	}

	public void delete(Album album) {
		Session session = sessionFactory.getCurrentSession();
		session.remove(session.merge(album));
	}

	public List<Album> getAllAlbums() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<Album> query = session.createNamedQuery("findAllAlbums", Album.class);
		return query.getResultList();
	}

	public List<Album> findByArtist(Artist artist) {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<Album> query = session.createNamedQuery("findByArtist", Album.class);
		query.setParameter("artist", artist);
		return query.getResultList();
	}

	public List<Album> find(String artist, String title, Integer year) {

		Session session = sessionFactory.getCurrentSession();

		StringBuilder hql = new StringBuilder("FROM Album a");

		int args = 0;

		if (artist != null) {
			if (args == 0) {
				hql.append(" WHERE ");
			} else {
				hql.append(" AND ");
			}

			hql.append("LOWER(a.artist.name) LIKE :artist");
			args++;
		}

		if (title != null) {
			if (args == 0) {
				hql.append(" WHERE ");
			} else {
				hql.append(" AND ");
			}

			hql.append("LOWER(a.title) LIKE :title");
			args++;
		}

		if (year != null) {
			if (args == 0) {
				hql.append(" WHERE ");
			} else {
				hql.append(" AND ");
			}

			hql.append("a.year = :year");
			args++;
		}

		Query<Album> query = session.createQuery(hql.toString());

		if (artist != null) {
			query.setParameter("artist", "%" + artist.toLowerCase() + "%");
		}

		if (title != null) {
			query.setParameter("title", "%" + title.toLowerCase() + "%");
		}

		if (year != null) {
			query.setParameter("year", year);
		}

		List<Album> result = query.getResultList();

		return result;
	}

}

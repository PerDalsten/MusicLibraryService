package dk.purplegreen.musiclibrary.service.persistence;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;
import dk.purplegreen.musiclibrary.service.model.Song;

public class TestSessionFactory {
	static {
		System.setProperty("derby.stream.error.file", "logs/derby.log");
	}

	private static final Logger log = LogManager.getLogger(TestSessionFactory.class);

	private static SessionFactory sessionFactory;

	private static Integer royalHuntId;
	private static Integer paradoxId;

	public static Integer getRoyalHuntId() {
		return royalHuntId;
	}

	public static Integer getParadoxId() {
		return paradoxId;
	}

	public static synchronized SessionFactory getSessionFactory() {

		if (sessionFactory == null) {

			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
			try {
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

				// XXX Add shutdown hook to close factory?

				createData();

			} catch (Exception e) {
				log.error("Error creating session factory", e);
				StandardServiceRegistryBuilder.destroy(registry);
			}
		}

		return sessionFactory;
	}

	public static EntityManager getEntityManager() {

		Session session = getSessionFactory().openSession();

		return session.unwrap(EntityManager.class);
	}

	private static void createData() {

		log.debug("Creating data");

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Artist artist = new Artist("The Beatles");
		session.persist(artist);

		Album album = new Album();
		album.setArtist(artist);
		album.setTitle("Abbey Road");
		album.setYear(1969);

		Song song = new Song();
		song.setTitle("Come Together");
		song.setDisc(1);
		song.setTrack(1);
		song.setAlbum(album);

		album.addSong(song);

		song = new Song();
		song.setTitle("Something");
		song.setDisc(1);
		song.setTrack(2);
		song.setAlbum(album);
		album.addSong(song);

		session.persist(album);

		artist = new Artist("AC/DC");
		session.persist(artist);
		album = new Album();
		album.setArtist(artist);
		album.setTitle("For Those About to Rock We Salute You");
		album.setYear(1981);

		song = new Song();
		song.setTitle("For Those About to Rock We Salute You");
		song.setDisc(1);
		song.setTrack(1);
		song.setAlbum(album);

		album.addSong(song);

		song = new Song();
		song.setTitle("I Put the Finger on You");
		song.setDisc(1);
		song.setTrack(2);
		song.setAlbum(album);
		album.addSong(song);

		session.persist(album);

		// Create last
		artist = new Artist("Royal Hunt");
		session.persist(artist);
		album = new Album();
		album.setArtist(artist);
		album.setTitle("Paradox");
		album.setYear(1997);

		song = new Song();
		song.setTitle("The Awakening");
		song.setDisc(1);
		song.setTrack(1);
		song.setAlbum(album);
		album.addSong(song);

		song = new Song();
		song.setTitle("River of Pain");
		song.setDisc(1);
		song.setTrack(2);
		song.setAlbum(album);
		album.addSong(song);

		session.persist(album);

		session.getTransaction().commit();

		royalHuntId = artist.getId();
		paradoxId = album.getId();

		session.close();
	}
}

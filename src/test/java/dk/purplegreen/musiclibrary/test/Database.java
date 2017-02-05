package dk.purplegreen.musiclibrary.test;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.rules.ExternalResource;

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;
import dk.purplegreen.musiclibrary.service.model.Song;

public class Database extends ExternalResource {

	static {
		System.setProperty("derby.stream.error.file", "logs/derby.log");
	}

	private SessionFactory sessionFactory;

	@Override
	protected void before() throws Throwable {

		Configuration configuration = new Configuration();
		configuration.setProperty("hibernate.connection.driver_class", "org.apache.derby.jdbc.EmbeddedDriver");
		// configuration.setProperty("hibernate.connection.url",
		// "jdbc:derby:src/test/resources/database/musiclibrarydb;create=true");
		configuration.setProperty("hibernate.connection.url", "jdbc:derby:memory:musiclibrarydb;create=true");
		configuration.setProperty("hibernate.connection.username", "musiclibrary");
		configuration.setProperty("hibernate.connection.password", "");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");

		configuration.addAnnotatedClass(Artist.class);
		configuration.addAnnotatedClass(Album.class);
		configuration.addAnnotatedClass(Song.class);

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		sessionFactory = configuration.buildSessionFactory(builder.build());
	}

	@Override
	protected void after() {

		if (sessionFactory != null)
			sessionFactory.close();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public EntityManager getEntityManager() {

		Session session = getSessionFactory().openSession();

		return session.unwrap(EntityManager.class);
	}
}

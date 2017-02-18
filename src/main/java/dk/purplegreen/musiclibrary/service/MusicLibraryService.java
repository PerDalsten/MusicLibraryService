package dk.purplegreen.musiclibrary.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;
import dk.purplegreen.musiclibrary.service.model.Song;
import dk.purplegreen.musiclibrary.service.persistence.AlbumDAO;
import dk.purplegreen.musiclibrary.service.persistence.ArtistDAO;

@Service
public class MusicLibraryService {

	private final ArtistDAO artistDAO;
	private final AlbumDAO albumDAO;

	private static final Logger log = LogManager.getLogger(MusicLibraryService.class);

	@Autowired
	private ArtistValidator artistValidator;

	@Autowired
	private AlbumValidator albumValidator;

	@Autowired
	public MusicLibraryService(ArtistDAO artistDAO, AlbumDAO albumDAO) {
		this.artistDAO = artistDAO;
		this.albumDAO = albumDAO;
	}

	@Transactional(readOnly = true)
	public List<Artist> getArtists() {
		if (log.isDebugEnabled()) {
			log.debug("getArtists() called");
		}
		return artistDAO.getAllArtists();
	}

	@Transactional(readOnly = true)
	public Artist getArtist(Integer id) throws ArtistNotFoundException {
		if (log.isDebugEnabled()) {
			log.debug("getArtist() called with id: " + id);
		}

		Artist result = artistDAO.find(id);
		if (result == null)
			throw new ArtistNotFoundException("Artist with id: " + id + " does not exist.");
		return result;
	}

	@Transactional
	public Artist createArtist(Artist artist) throws InvalidArtistException {

		if (artist == null) {
			throw new InvalidArtistException("Artist cannot be null");
		}

		if (log.isDebugEnabled()) {
			log.debug("createArtist called with artist: " + artist.getName());
		}

		artist.setId(null);
		artistValidator.validate(artist);
		return artistDAO.save(artist);
	}

	@Transactional
	public Artist updateArtist(Artist artist) throws ArtistNotFoundException, InvalidArtistException {

		if (artist == null) {
			throw new InvalidArtistException("Artist cannot be null");
		}

		if (log.isDebugEnabled()) {
			log.debug("updateArtist called with artist: " + artist.getId() + "-" + artist.getName());
		}

		getArtist(artist.getId());
		artistValidator.validate(artist);
		return artistDAO.save(artist);
	}

	@Transactional
	public void deleteArtist(Integer id)
			throws ArtistNotFoundException, dk.purplegreen.musiclibrary.service.InvalidArtistException {

		Artist artist = getArtist(id);

		if (albumDAO.getArtistAlbumCount(artist) > 0) {
			throw new InvalidArtistException("Artist has albums and cannot be deleted");
		}

		if (log.isDebugEnabled()) {
			log.debug("deleteArtist() called with id: " + id);
		}
		artistDAO.delete(artist);
	}

	@Transactional(readOnly = true)
	public List<Album> getAlbums(Artist artist) {
		if (log.isDebugEnabled()) {
			log.debug("getAlbums() called for artist: " + artist.getName());
		}
		return albumDAO.findByArtist(artist);
	}

	@Transactional(readOnly = true)
	public List<Album> findAlbums(String artist, String title, Integer year) {

		if (log.isDebugEnabled()) {
			log.debug("findAlbums called: artist=" + artist + ", title=" + title + ", year=" + year);
		}

		return albumDAO.find(artist, title, year);
	}

	@Transactional(readOnly = true)
	public Album getAlbum(Integer id) throws AlbumNotFoundException {

		if (log.isDebugEnabled()) {
			log.debug("getAlbum() called with id: " + id);
		}

		Album result = albumDAO.find(id);
		if (result == null)
			throw new AlbumNotFoundException("Album with id: " + id + " does not exist.");
		return result;
	}

	@Transactional(readOnly = true)
	public List<Album> getAlbums() {
		if (log.isDebugEnabled()) {
			log.debug("getAlbums() called");
		}
		return albumDAO.getAllAlbums();
	}

	@Transactional
	public Album createAlbum(Album album) throws InvalidAlbumException {

		if (album == null) {
			throw new InvalidAlbumException("Album cannot be null");
		}

		if (log.isDebugEnabled()) {
			log.debug("createAlbum called with artist: " + album.getTitle());
		}

		album.setId(null);
		attachSongs(album);
		albumValidator.validate(album);
		return albumDAO.save(album);
	}

	@Transactional
	public Album updateAlbum(Album album) throws AlbumNotFoundException, InvalidAlbumException {

		if (album == null) {
			throw new InvalidAlbumException("Album cannot be null");
		}

		if (log.isDebugEnabled()) {
			log.debug("updateAlbum called with album: " + album.getId() + "-" + album.getTitle());
		}

		getAlbum(album.getId());
		attachSongs(album);
		albumValidator.validate(album);
		return albumDAO.save(album);
	}

	@Transactional
	public void deleteAlbum(Integer id) throws AlbumNotFoundException {
		if (log.isDebugEnabled()) {
			log.debug("deleteAlbum() called with id: " + id);
		}
		albumDAO.delete(getAlbum(id));
	}

	private void attachSongs(Album album) {
		for (Song song : album.getSongs()) {
			song.setAlbum(album);
		}
	}
}

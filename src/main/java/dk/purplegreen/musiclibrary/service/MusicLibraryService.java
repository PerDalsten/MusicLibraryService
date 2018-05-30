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

		log.debug("getArtists() called");

		return artistDAO.getAllArtists();
	}

	@Transactional(readOnly = true)
	public Artist getArtist(Integer id) throws MusicLibraryServiceException {

		log.debug("getArtist() called with id: {}", id);

		Artist result = artistDAO.find(id);
		if (result == null)
			throw new ArtistNotFoundException("Artist with id: " + id + " does not exist.");
		return result;
	}

	@Transactional
	public Artist createArtist(Artist artist) throws MusicLibraryServiceException {

		log.debug("createArtist() called with artist: {}", artist);

		if (artist == null) {
			throw new InvalidArtistException("Artist cannot be null");
		}

		artist.setId(null);
		artistValidator.validate(artist);
		return artistDAO.save(artist);
	}

	@Transactional
	public Artist updateArtist(Artist artist) throws MusicLibraryServiceException {

		log.debug("updateArtist() called with artist: {}", artist);

		if (artist == null) {
			throw new InvalidArtistException("Artist cannot be null");
		}

		getArtist(artist.getId());
		artistValidator.validate(artist);
		return artistDAO.save(artist);
	}

	@Transactional
	public void deleteArtist(Artist artist) throws MusicLibraryServiceException {

		log.debug("deleteArtist() called with artist: {}", artist);

		artist = getArtist(artist.getId());

		if (albumDAO.getArtistAlbumCount(artist) > 0) {
			throw new InvalidArtistException("Artist has albums and cannot be deleted");
		}

		artistDAO.delete(artist);
	}

	@Transactional(readOnly = true)
	public List<Album> getAlbums(Artist artist) throws MusicLibraryServiceException {

		log.debug("getAlbums() called for artist: {}", artist);

		artist = getArtist(artist.getId());

		return albumDAO.findByArtist(artist);
	}

	@Transactional(readOnly = true)
	public List<Album> findAlbums(String artist, String title, Integer year) {

		log.debug("findAlbums() called: artist={}, title={}, year={}", artist, title, year);

		return albumDAO.find(artist, title, year);
	}

	@Transactional(readOnly = true)
	public Album getAlbum(Integer id) throws MusicLibraryServiceException {

		log.debug("getAlbum() called with id: {}", id);

		Album result = albumDAO.find(id);
		if (result == null)
			throw new AlbumNotFoundException("Album with id: " + id + " does not exist.");
		return result;
	}

	@Transactional(readOnly = true)
	public List<Album> getAlbums() {

		log.debug("getAlbums() called");

		return albumDAO.getAllAlbums();
	}

	@Transactional
	public Album createAlbum(Album album) throws MusicLibraryServiceException {

		log.debug("createAlbum() called with album: {}", album);

		if (album == null) {
			throw new InvalidAlbumException("Album cannot be null");
		}

		album.setId(null);
		attachSongs(album);
		albumValidator.validate(album);
		return albumDAO.save(album);
	}

	@Transactional
	public Album updateAlbum(Album album) throws MusicLibraryServiceException {

		log.debug("updateAlbum() called with album: {}", album);

		if (album == null) {
			throw new InvalidAlbumException("Album cannot be null");
		}

		getAlbum(album.getId());
		attachSongs(album);
		albumValidator.validate(album);
		return albumDAO.save(album);
	}

	@Transactional
	public void deleteAlbum(Album album) throws MusicLibraryServiceException {

		log.debug("deleteAlbum() called with album: {}", album);

		albumDAO.delete(getAlbum(album.getId()));
	}

	private void attachSongs(Album album) {
		for (Song song : album.getSongs()) {
			song.setAlbum(album);
		}
	}
}

package dk.purplegreen.musiclibrary.service;

import org.springframework.stereotype.Component;

import dk.purplegreen.musiclibrary.service.model.Album;

@Component
public class AlbumValidator {

	public void validate(Album album) throws InvalidAlbumException {
		if (album == null) {
			throw new InvalidAlbumException("Album is null");
		}

		if (album.getTitle() == null || album.getTitle().trim().isEmpty()) {
			throw new InvalidAlbumException("Album title cannot be empty");
		}

		if (album.getArtist() == null) {
			throw new InvalidAlbumException("Artist is null");
		}

		if (album.getArtist().getId() == null) {
			throw new InvalidAlbumException("Artist is invalid");
		}

		if (album.getSongs() == null || album.getSongs().isEmpty()) {
			throw new InvalidAlbumException("Album does not have any songs");
		}
	}
}

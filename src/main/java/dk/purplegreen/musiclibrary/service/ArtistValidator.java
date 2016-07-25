package dk.purplegreen.musiclibrary.service;

import org.springframework.stereotype.Component;

import dk.purplegreen.musiclibrary.service.model.Artist;

@Component
public class ArtistValidator {

	public void validate(Artist artist) throws InvalidArtistException {
		if (artist == null) {
			throw new InvalidArtistException("Artist is null");
		}

		if (artist.getName() == null || artist.getName().trim().length() == 0) {
			throw new InvalidArtistException("Artist name cannot be empty");
		}
	}
}

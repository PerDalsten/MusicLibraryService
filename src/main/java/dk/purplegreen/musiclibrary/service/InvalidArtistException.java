package dk.purplegreen.musiclibrary.service;

public class InvalidArtistException extends MusicLibraryServiceException {

	private static final long serialVersionUID = -5785401426502192444L;

	public InvalidArtistException() {
		super();
	}

	public InvalidArtistException(String message) {
		super(message);
	}

}

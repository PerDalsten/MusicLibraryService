package dk.purplegreen.musiclibrary.service;

public class InvalidAlbumException extends MusicLibraryServiceException {

	private static final long serialVersionUID = 498572381784465539L;

	public InvalidAlbumException() {
		super();
	}

	public InvalidAlbumException(String message) {
		super(message);
	}

}

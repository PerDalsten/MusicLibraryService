package dk.purplegreen.musiclibrary.service;

public class ArtistNotFoundException extends Exception {

	private static final long serialVersionUID = 6511452772509671451L;

	public ArtistNotFoundException() {
		super();
	}

	public ArtistNotFoundException(String message) {
		super(message);
	}
}

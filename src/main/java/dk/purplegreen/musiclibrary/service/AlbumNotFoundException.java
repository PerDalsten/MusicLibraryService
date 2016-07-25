package dk.purplegreen.musiclibrary.service;

public class AlbumNotFoundException extends Exception {

	private static final long serialVersionUID = 1068842211143817698L;

	public AlbumNotFoundException() {
		super();
	}

	public AlbumNotFoundException(String message) {
		super(message);
	}
}

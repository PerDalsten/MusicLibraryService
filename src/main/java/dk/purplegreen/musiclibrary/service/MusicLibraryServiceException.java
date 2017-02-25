package dk.purplegreen.musiclibrary.service;

public abstract class MusicLibraryServiceException extends Exception {

	private static final long serialVersionUID = -108741615289868255L;
	
	protected MusicLibraryServiceException() {
	}
	
	protected MusicLibraryServiceException(String message) {
		super(message);
	}
}

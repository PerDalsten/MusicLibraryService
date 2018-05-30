package dk.purplegreen.musiclibrary.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dk.purplegreen.musiclibrary.service.AlbumNotFoundException;
import dk.purplegreen.musiclibrary.service.ArtistNotFoundException;
import dk.purplegreen.musiclibrary.service.InvalidAlbumException;
import dk.purplegreen.musiclibrary.service.InvalidArtistException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LogManager.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(value = { AlbumNotFoundException.class, ArtistNotFoundException.class })
	protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {

		log.info("Not found", ex);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { InvalidAlbumException.class, InvalidArtistException.class })
	protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
		log.error("Bad request", ex);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> handleDefault(Exception ex, WebRequest request) {
		log.error("Internal server error", ex);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

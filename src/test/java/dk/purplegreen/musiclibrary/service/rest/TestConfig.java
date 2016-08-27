package dk.purplegreen.musiclibrary.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;

@Configuration
public class TestConfig {
	
	@Bean
	public List<Album> albums(){
		
		List<Album> albums=new ArrayList<>();
		
		Artist artist=new Artist("Deep Purple");
		artist.setId(1);
		
		Album album=new Album(artist, "Machine Head", 1972);
		album.setId(42);
		
		albums.add(album);
		
		return albums;
	}
		
}

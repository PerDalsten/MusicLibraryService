package dk.purplegreen.musiclibrary.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dk.purplegreen.musiclibrary.service.model.Album;
import dk.purplegreen.musiclibrary.service.model.Artist;
import dk.purplegreen.musiclibrary.service.model.Song;

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
		
		artist=new Artist("Pink Floyd");
		artist.setId(12);
		
		album=new Album(artist, "Animals", 1977);
		album.setId(45);
		
		
		Song song=new Song("Pigs On The Wing, Pt.1", 1);
		album.addSong(song);
		song=new Song("Dogs", 2);
		album.addSong(song);
		song=new Song("Pigs (Three Different Ones", 3);
		album.addSong(song);
		
		albums.add(album);
		
		return albums;
	}
		
}

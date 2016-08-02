package dk.purplegreen.musiclibrary.service.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "ALBUM")
@NamedQueries({ @NamedQuery(name = "findAllAlbums", query = "SELECT a FROM Album a"),
		@NamedQuery(name = "findByArtist", query = "SELECT a FROM Album a WHERE a.artist = :artist"),
		@NamedQuery(name = "findByTitle", query = "SELECT a FROM Album a WHERE a.title = :title") })
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "ARTIST_ID", nullable = false)
	private Artist artist;
	@Column(name = "ALBUM_TITLE", nullable = false)
	private String title;
	@Column(name = "ALBUM_YEAR", nullable = false)
	private Integer year;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "album", orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy("disc, track")
	private List<Song> songs = new ArrayList<>();

	public Album() {
	}

	public Album(Artist artist, String title, Integer year) {
		this.artist = artist;
		this.title = title;
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void addSong(Song song) {
		if (song.getAlbum() != this) {
			song.setAlbum(this);
		}
		getSongs().add(song);
	}
}

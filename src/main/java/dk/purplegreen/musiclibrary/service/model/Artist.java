package dk.purplegreen.musiclibrary.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "artist")
@NamedQueries({ @NamedQuery(name = "findAllArtists", query = "SELECT a FROM Artist a ORDER BY a.name"),
		@NamedQuery(name = "findByName", query = "SELECT a FROM Artist a WHERE a.name = :name") })
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "artist_name", nullable = false)
	private String name;

	public Artist() {
	}

	public Artist(Integer id) {
		this.id = id;
	}

	public Artist(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		StringBuilder result = new StringBuilder("<");
		result.append(id);
		result.append("> ");
		result.append(name);
		return result.toString();
	}
}

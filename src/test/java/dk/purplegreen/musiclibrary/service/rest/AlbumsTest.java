package dk.purplegreen.musiclibrary.service.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dk.purplegreen.musiclibrary.service.AlbumNotFoundException;
import dk.purplegreen.musiclibrary.service.MusicLibraryService;
import dk.purplegreen.musiclibrary.service.model.Album;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class AlbumsTest {

	private static final Logger log = LogManager.getLogger(AlbumsTest.class);

	private MockMvc mockMvc;

	@Mock
	private MusicLibraryService service;

	@InjectMocks
	private Albums albums;

	@Autowired
	private List<Album> testAlbums;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(albums).build();
	}

	@Test
	public void testGetAllAlbumsEmpty() throws Exception {
		when(service.getAlbums()).thenReturn(new ArrayList<Album>());

		mockMvc.perform(get("/albums").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(content().string("[]"));
	}

	@Test
	public void testGetAllAlbums() throws Exception {
		when(service.getAlbums()).thenReturn(testAlbums);

		MvcResult result = mockMvc.perform(get("/albums").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].artist.name").value("Deep Purple"))
				.andExpect(jsonPath("$[1].title").value("Animals"))
				.andExpect(jsonPath("$[1].songs[1].title").value("Dogs")).andDo(print()).andReturn();

		String content = result.getResponse().getContentAsString();
		log.debug("Got response: " + content);
	}

	@Test
	public void testGetAlbum() throws Exception {
		when(service.getAlbum(testAlbums.get(1).getId())).thenReturn(testAlbums.get(1));

		mockMvc.perform(get("/albums/" + testAlbums.get(1).getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.artist.name").value(testAlbums.get(1).getArtist().getName())).andDo(print());
	}

	@Test
	public void testGetNonExistingAlbum() throws Exception {
		when(service.getAlbum(0)).thenThrow(new AlbumNotFoundException());

		mockMvc.perform(get("/albums/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}
}

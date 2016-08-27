package dk.purplegreen.musiclibrary.service.rest;

import static org.junit.Assert.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

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
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andReturn();

		String content = result.getResponse().getContentAsString();
		log.debug("Got response: " + content);

		assertTrue("Album missing", content.contains("Machine Head"));
		assertTrue("Artist missing", content.contains("Deep Purple"));
	}
}

package dk.purplegreen.musiclibrary.service.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = { "dk.purplegreen.musiclibrary.service.rest" })
@EnableWebMvc
public class RestConfig {

}

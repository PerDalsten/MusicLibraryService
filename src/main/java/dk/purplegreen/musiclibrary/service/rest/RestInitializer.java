package dk.purplegreen.musiclibrary.service.rest;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import dk.purplegreen.musiclibrary.service.Config;

public class RestInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { Config.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { RestConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/rest/*" };
	}
}

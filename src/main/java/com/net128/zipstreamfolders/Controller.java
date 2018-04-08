package com.net128.zipstreamfolders;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.net128.zipstreamfolders.contentnodes.ContentNode;
import com.net128.zipstreamfolders.data.ContentRespository;
import com.net128.zipstreamfolders.service.ZipService;

@RestController
public class Controller {

	@Inject
	private ContentRespository contentRespository;
	
	@Inject
	private ZipService zipService;
	
	@GetMapping("/info")
	public Info info() {
		return new Info();
	}
	
	@GetMapping("/list")
	public ContentNode list() {
		return contentRespository.getContentNode();
	}
	
	@GetMapping(value = "/zip", produces="application/zip")
	public void zip(HttpServletResponse response) throws IOException {
	    ContentNode contentNode=contentRespository.getContentNode();
	    response.setStatus(HttpServletResponse.SC_OK);
	    response.addHeader("Content-Disposition", "attachment; filename=\""+contentNode.name().replaceAll("[_.-/]*$", "")+".zip\"");
	    zipService.zipStream(contentNode, response.getOutputStream());
	}
	
	public static class Info {
		public String message="An example Spring Boot Application providing an endpoint, which streams arbitrary content repository trees as a ZIP file.";
	}
}

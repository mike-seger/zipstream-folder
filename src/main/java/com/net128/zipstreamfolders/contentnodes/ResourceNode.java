package com.net128.zipstreamfolders.contentnodes;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.io.Resources;

public class ResourceNode extends ContentNode {
	private String path;	
	public ResourceNode(String pkg, String resourcePattern, String rootName) {
		super(rootName);
		init(pkg, resourcePattern);
		path=null;
	}
	
	@Override
	public ContentNode streamData(OutputStream os) throws IOException {
		URL resource = getClass().getClassLoader().getResource(path);
		Resources.copy(resource, os);
		return this;
	}
	
	@JsonProperty
	@JsonInclude(Include.NON_EMPTY)
	public String getPath() {
		return path;
	}
	
	@Override
    public boolean hasData() {
    	return path!=null;
    }
	
	private ResourceNode(String path) {
		super(getName(path));
		this.path=path;
	}
	
	private void init(String pkg, String resourcePattern) {
		Reflections reflections = new Reflections(pkg, new ResourcesScanner());
		Set<String> paths = new TreeSet<>(reflections.getResources(Pattern.compile(resourcePattern)));
		Map<String, ContentNode> folderNodes = new TreeMap<>();
		paths.forEach(p -> collectParents(getParent(p), folderNodes));
		paths.forEach(p -> folderNodes.get(getParent(p)).add(new ResourceNode(p)));
		Set<String> folderPaths=new TreeSet<>(folderNodes.keySet());
		folderPaths.forEach(p -> {if(p.contains("/")) {folderNodes.remove(p);}});
		if(folderNodes.size()==1) {
			ContentNode c=folderNodes.values().iterator().next();
			children=c.children;
			children.values().forEach(ch -> ch.parent=this);
		} else {
			folderNodes.values().forEach(c -> add(c.getName(), c));
		}
	}
	
	private static String getParent(String path) {
		return path.replaceAll("/[^/]*$", "");
	}
	
	private static String getName(String path) {
		return path.replaceAll(".*/", "");
	}
	
	private ContentNode collectParents(String path, Map<String, ContentNode> parents) {
		ContentNode c=new ContentNode(getName(path));
		parents.put(path, c);
		if(path.contains("/")) {
			String parentPath=getParent(path);
			ContentNode c2=parents.get(parentPath);
			if(c2!=null) {
				c2.add(c);
			} else {
				collectParents(getParent(path), parents).add(c);
			}
		}
		return c;
	}
}

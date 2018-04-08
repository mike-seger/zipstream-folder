package com.net128.zipstreamfolders.contentnodes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Iterables;

public class ContentNode implements Iterable<ContentNode> {
	@JsonIgnore
	private String name;
	@JsonIgnore
	public ContentNode parent;
	protected ContentNode() {}
	public ContentNode(String name)  {
		this.name=name;
	}
	public ContentNode streamData(OutputStream os) throws IOException {
		if(!isStucture()) {
			throw new IOException("No data"); 	
		}
		return this;
	}
	@JsonIgnore
	public Map<String, ContentNode> children;
	public ContentNode add(ContentNode ... contentNodes) {
		for(ContentNode contentNode : contentNodes) {
	    	if(children==null) {
	    		children=new TreeMap<>();
	    	}
	    	contentNode.parent=this;
			children.put(contentNode.name, contentNode);
		}
		return this;
	}
	
	@JsonProperty("path")
	@JsonInclude(Include.NON_EMPTY)
	public String getFullPath() {
		if(isStucture()) {
			return null;
		}
		return getPath();
	}
	
	@JsonIgnore
	public String getPath() {
		String path=name();
		ContentNode c=parent;
		while(c!=null) {
			path=c.name()+path;
			c=c.parent;
		}
		return path;
	}
	
	public String getName() {
		return name;
	}
	
	public String name() {
		return name+(isStucture()?"/":"");
	}
	
    @JsonAnySetter 
    public void add(String key, ContentNode value) {
    	add(value);
    }

    @JsonAnyGetter
    public Map<String,ContentNode> getMap() {
      return children;
    }
    
    public boolean hasData() {
    	return getClass().equals(ContentNode.class);
    }
    
    @JsonIgnore
    public boolean isStucture() {
    	return getClass().equals(ContentNode.class);
    }
    
    public String toString() {
    	return name;
    }
    
	@Override
	public Iterator<ContentNode> iterator() {
		List<Iterable<ContentNode>> iterables=new ArrayList<>();
		if(!isStucture()) {
			iterables.add(Collections.singletonList(this));
		}
		if(children!=null) {
			children.values().forEach(c -> iterables.add(c));
		}
		return Iterables.concat(iterables).iterator();
	}
}

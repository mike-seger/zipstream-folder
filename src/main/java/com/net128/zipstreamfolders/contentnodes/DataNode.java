package com.net128.zipstreamfolders.contentnodes;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DataNode extends ContentNode {
	private byte [] data;
	public DataNode(String name, byte [] data) {
		super(name);
		this.data=data;		
	}
	public DataNode(String name, String s) {
		this(name, s.getBytes(StandardCharsets.UTF_8));		
	}
	public ContentNode streamData(OutputStream os) throws IOException {
		if(hasData()) {
			os.write(data);
		}
		return this;
	}
	
	@Override
    public boolean hasData() {
    	return data!=null;
    }
	
//	@JsonProperty
//	@JsonInclude(Include.NON_EMPTY)
//	public String getData() throws IOException {
//		if(hasData()) {
//			return new String(data, StandardCharsets.UTF_8);
//		}
//		return null;
//	}
}

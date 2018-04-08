package com.net128.zipstreamfolders.data;

import org.springframework.stereotype.Repository;

import com.net128.zipstreamfolders.Application;
import com.net128.zipstreamfolders.contentnodes.ContentNode;
import com.net128.zipstreamfolders.contentnodes.DataNode;
import com.net128.zipstreamfolders.contentnodes.ResourceNode;

@Repository
public class ContentRespository {
	private static ContentNode contentNode;
	static {
		contentNode =
			new ContentNode("c0").add(
				new ContentNode("c1"),
				new ContentNode("c2").add(
					new ContentNode("c2.1").add(
						new DataNode("c2.1.1.txt", "c2.1.1 data"),
						new DataNode("c2.1.2.txt", "c2.1.2 data"),
						new DataNode("c2.1.3.txt", "c2.1.3 data")),
					new ContentNode("c2.2"),
					new ContentNode("c2.3")
				),
				new ContentNode("c3"));
		ResourceNode resourceNode=new ResourceNode(
			Application.class.getPackage().getName()+".data", ".*\\.(gz|txt)$", "com");
		contentNode.add(resourceNode);
	}

	public ContentNode getContentNode() {
		return contentNode;
	}
}

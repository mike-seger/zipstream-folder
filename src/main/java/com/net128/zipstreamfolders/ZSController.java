package com.net128.zipstreamfolders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.net128.zipstreamfolders.contentnodes.ContentNode;
import com.net128.zipstreamfolders.contentnodes.DataNode;
import com.net128.zipstreamfolders.contentnodes.ResourceNode;

@RestController
public class ZSController {
	@GetMapping(value="/info")
	public Info info() {
		return new Info();
	}
	
	@GetMapping(value="/list")
	public ContentNode list() {
		ResourceNode resourceNode=new ResourceNode(getClass().getPackage().getName()+".data", ".*\\.txt$");
		ContentNode contentNode=
				new ContentNode("c0").add(
					new ContentNode("c1"),
					new ContentNode("c2").add(
						new ContentNode("c2.1").add(
							new DataNode("c2.1.1", "c2.1.1 data"),
							new DataNode("c2.1.2", "c2.1.2 data"),
							new DataNode("c2.1.3", "c2.1.3 data")),
						new ContentNode("c2.2"),
						new ContentNode("c2.3")
					),
					new ContentNode("c3"));
		contentNode.add(resourceNode);
		return contentNode;
	}
	
	public static class Info {
		public String message="Hello";
	}
}

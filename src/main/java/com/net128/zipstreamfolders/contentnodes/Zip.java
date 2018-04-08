package com.net128.zipstreamfolders.contentnodes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.net128.zipstreamfolders.ZipstreamFoldersApplication;

public class Zip {
	public static void main(String[] args) {
		ContentNode contentNode=
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
		ResourceNode resourceNode=new ResourceNode(ZipstreamFoldersApplication.class.getPackage().getName()+".data", ".*\\.txt$");
		contentNode.add(resourceNode);
		String zipFile = "/tmp/archive.zip";

		try {
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (ContentNode c : contentNode) {
				if(c.getPath()==null) {
					continue;
				}
				System.out.println("Adding file: " + c.getPath());
				zos.putNextEntry(new ZipEntry(c.getPath()));
				c.streamData(zos);
				zos.closeEntry();
			}
			zos.close();
		} catch (IOException ioe) {
			System.out.println("Error creating zip file" + ioe);
			ioe.printStackTrace();
		}
	}
}
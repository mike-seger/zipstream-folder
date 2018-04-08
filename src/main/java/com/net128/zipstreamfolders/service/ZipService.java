package com.net128.zipstreamfolders.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.ZoneId;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.net128.zipstreamfolders.contentnodes.ContentNode;

@Service
public class ZipService {
	private static final Logger logger = LoggerFactory.getLogger(ZipService.class);
	
	public void zipStream(ContentNode contentNode, OutputStream os) {
		try (ZipOutputStream zos = new ZipOutputStream(os)) {	
			for (ContentNode c : contentNode) {
				logger.debug("Adding file: {}", c.getPath());
				Instant instant = c.getCreated().atZone(ZoneId.of("Europe/Zurich")).toInstant();
				FileTime time=FileTime.from(instant);
				ZipEntry zipEntry=new ZipEntry(c.getPath());
				zipEntry.setCreationTime(time);
				zipEntry.setLastAccessTime(time);
				zipEntry.setLastModifiedTime(time);
				zos.putNextEntry(zipEntry);
				c.streamData(zos);
				zos.closeEntry();
			}
		} catch (IOException e) {
			logger.error("Error creating zip file", e);
		}
	}
}
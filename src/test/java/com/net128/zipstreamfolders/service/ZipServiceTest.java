package com.net128.zipstreamfolders.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.net128.zipstreamfolders.data.ContentRespository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZipServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ZipServiceTest.class);

	@Inject
	private ContentRespository contentRespository;
	
	@Inject
	private ZipService zipService;
	
	@Test
	public void test() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		File zipFile = new File(System.getProperty("java.io.tmpdir"), "archive.zip");
		try {
			zipFile.delete();
			try (OutputStream os = new FileOutputStream(zipFile)) {
				zipService.zipStream(contentRespository.getContentNode(), os);
			}
			logger.info("Created zip file: {}", zipFile.getAbsolutePath());
			assertThat(zipFile.exists());
			assertThat(zipFile.length()).isEqualTo(2084L);
			assertThat(md5(zipFile)).isEqualTo("2CF57900EF6A49209DD8F905818EA319");
		} finally {
			zipFile.delete();			
		}
	}
	
	private String md5(File file) throws IOException, NoSuchAlgorithmException {
		byte[] b = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		byte[] hash = MessageDigest.getInstance("MD5").digest(b);
		return DatatypeConverter.printHexBinary(hash);
	}
}

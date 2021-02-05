package Unicam.SPM2020_FMS.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringJUnitConfig(locations = "classpath:/user-beans.xml")
public class StorageServiceTest {

	@Autowired
	private StorageService storageService;

	@Test
	public void loadNonExistent() {
		Assert.assertTrue(Files.notExists(storageService.load("foo.txt")));
	}

	@Test
	public void saveAndLoad() {
		MockMultipartFile newFile =new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,"Hello, World".getBytes());
		storageService.store(newFile,"HelloWorld.txt");
		try {
			Assert.assertTrue(Files.deleteIfExists(storageService.load("HelloWorld.txt")));
		} catch (IOException e) {
			Assert.fail("IOException given");
		} 
	}
	
	@Test
	public void saveIncorrectFileName() {
		MockMultipartFile newFile =new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,"Hello, World".getBytes());
		Assertions.assertThrows(UncheckedIOException.class, () -> { storageService.store(newFile,"../helloWorld.txt"); } );
	}
	
	@Test
	public void saveEmptyFile() {
		MockMultipartFile newFile =new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,new byte[0]);
		Assertions.assertThrows(UncheckedIOException.class, () -> { storageService.store(newFile,"HelloWorld.txt"); } );
	}

}
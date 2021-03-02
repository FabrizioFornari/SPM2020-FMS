package Unicam.SPM2020_FMS.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import Unicam.SPM2020_FMS.model.Reservation;

@SpringJUnitConfig(locations = "classpath:/user-beans.xml")
public class StorageServiceTest {

	@Autowired
	private StorageService storageService;
	boolean uploadDirOk;
	
	@BeforeEach
	public void checkUploadDir() {
		uploadDirOk=Files.exists(storageService.load(""));
	}
	
	  @Test
	  public void testShowReservationToCheck() {
		  Assert.fail("In order to fail jenkins build");
	  }

	@Test
	public void loadNonExistent() {
		if(uploadDirOk) {
			Assert.assertTrue(Files.notExists(storageService.load("foo.txt")));
		} else {
			Assert.fail("UploadDir not properly configured");
		}
	}

	@Test
	public void saveAndLoad() {
		if(uploadDirOk) {
			MockMultipartFile newFile =new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,"Hello, World".getBytes());
			storageService.store(newFile,"HelloWorld.txt");
			try {
				Assert.assertTrue(Files.deleteIfExists(storageService.load("HelloWorld.txt")));
			} catch (IOException e) {
				Assert.fail("IOException given");
			} 
		} else {
			Assert.fail("UploadDir not properly configured");
		}
	}
	
	@Test
	public void saveIncorrectFileName() {
		if(uploadDirOk) {
			MockMultipartFile newFile =new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,"Hello, World".getBytes());
			Assertions.assertThrows(UncheckedIOException.class, () -> { storageService.store(newFile,"/images/helloWorld.txt"); } );
		} else {
			Assert.fail("UploadDir not properly configured");
		}
	}
	
	@Test
	public void saveEmptyFile() {
		if(uploadDirOk) {
			MockMultipartFile newFile =new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,new byte[0]);
			Assertions.assertThrows(UncheckedIOException.class, () -> { storageService.store(newFile,"HelloWorld.txt"); } );
		} else {
			Assert.fail("UploadDir not properly configured");
		}
	}

}
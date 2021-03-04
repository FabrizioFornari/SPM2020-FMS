package Unicam.SPM2020_FMS.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

public class StorageService {
	
	private Path rootLocation=null;
	
	@Autowired
	public StorageService() {
		Properties prop=new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		String uploadDir=prop.getProperty("uploadDir");
		if (uploadDir.equals("project resources")) {
			try {
				this.rootLocation = Paths.get(this.getClass().getClassLoader().getResource("maps/").toURI());
			} catch (URISyntaxException e) {
				System.out.println(e.getMessage());
			}
		}else {
			this.rootLocation = Paths.get(uploadDir);
		}
	}

	public void store(MultipartFile file, String filename) {
		try {
			if (file.isEmpty()) {
				throw new IOException("Failed to store empty file " + file.getOriginalFilename());
			}
			Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new UncheckedIOException("Failed to read stored files",e);
		}
	}

	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}


	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new IOException("Could not read file: " + filename);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Could not read file: " + filename, e);
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new UncheckedIOException("Could not initialize storage", e);
		}
	}
	
}

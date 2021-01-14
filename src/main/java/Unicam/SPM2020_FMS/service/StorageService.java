package Unicam.SPM2020_FMS.service;

import java.io.IOException;
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
	
	private final Path rootLocation;
	
	@Autowired
	public StorageService() {
		Properties prop=new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		this.rootLocation = Paths.get(prop.getProperty("uploadDir"));
	}

	public void store(MultipartFile file, String filename) throws Exception {
		try {
			if (file.isEmpty()) {
				throw new IOException("Failed to store empty file " + file.getOriginalFilename());
			}
			Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception("Failed to store file " + file.getOriginalFilename());
		}
	}

	public Stream<Path> loadAll() throws IOException {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new IOException("Failed to read stored files", e);
		}
	}

	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}


	public Resource loadAsResource(String filename) throws IOException {
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
			throw new IOException("Could not read file: " + filename, e);
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() throws IOException {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new IOException("Could not initialize storage", e);
		}
	}
	
}

package org.anchorz.java_drive.services;

import org.anchorz.java_drive.mappers.FileMapper;
import org.anchorz.java_drive.models.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private List<File> files;
    private final String root = "uploads";
    private final FileMapper fileMapper;
    public List<File> getAllFiles(int userId) {
        this.files = fileMapper.getAllFiles(userId);
        return files;
    }
    public void addFile(File newFile) {
        this.fileMapper.createFile(newFile);
    }
    public File getFile(int fileId) {
        return this.fileMapper.getFile(fileId);
    }
    public String upload(MultipartFile file, int userId) {
        try {
            Files.createDirectories(Path.of(root, String.valueOf(userId)));
            Path filePath = Path.of(root, String.valueOf(userId), file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);
            return filePath.toString();
        } catch (IOException exception) {
            System.out.print(exception);
            System.out.print(exception.getMessage());
            return exception.getMessage();
        }
    }
    public Boolean checkFileExists(String filename, int userId) {
        return this.fileMapper.checkFile(filename, userId) != null;
    }
    public void deleteFile(int fileId) throws IOException {
        File file = fileMapper.getFile(fileId);
        String url = file.getUrl();
        Files.delete(Path.of(url));
        fileMapper.deleteFile(fileId);
    }
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }
    @PostConstruct
    public void postConstruct() throws IOException {
        System.out.println("Creating Files Service bean");
        Files.createDirectories(Path.of(root));
        this.files = new ArrayList<>();
    }
}

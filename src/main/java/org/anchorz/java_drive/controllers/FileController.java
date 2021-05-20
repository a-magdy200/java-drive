package org.anchorz.java_drive.controllers;

import org.anchorz.java_drive.models.File;
import org.anchorz.java_drive.services.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @GetMapping
    public String getAllFiles(Authentication authentication, @ModelAttribute File fileForm, Model model) {
        int userId = (int) authentication.getPrincipal();
        List<File> files = this.fileService.getAllFiles(userId);
        model.addAttribute("files", files);
        model.addAttribute("fileForm", fileForm);
        return "redirect:/home";
    }
    @PostMapping("/upload")
    public String addFile(Authentication authentication,@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        int userId = (int) authentication.getPrincipal();
        try {
            String filename = file.getOriginalFilename();
            Boolean fileNameExists = fileService.checkFileExists(filename, userId);
            if (fileNameExists) {
                redirectAttributes.addFlashAttribute("error", "File Already Exists");
            } else {
                String url = fileService.upload(file, userId);
                File newFileUpload = new File();
                newFileUpload.setUserId(userId);
                newFileUpload.setUrl(url);
                newFileUpload.setName(filename);
                newFileUpload.setSize(file.getSize());
                newFileUpload.setType(file.getContentType());
                fileService.addFile(newFileUpload);
                redirectAttributes.addFlashAttribute("message", "File Added successfully");
            }
        } catch (Exception error) {
            System.out.print(error.getMessage());
            redirectAttributes.addFlashAttribute("error", error.getMessage());
        }
        return "redirect:/home";
    }
    @GetMapping("/{fileId}/download")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable int fileId) throws IOException {
        File file = fileService.getFile(fileId);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file.getUrl()));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.getSize())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/{fileId}/delete")
    public String deleteFile(@PathVariable int fileId, Model model, RedirectAttributes redirectAttributes) {
        try {
            fileService.deleteFile(fileId);
            redirectAttributes.addFlashAttribute("message", "File Deleted successfully");
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
        }
        return "redirect:/home";
    }
}

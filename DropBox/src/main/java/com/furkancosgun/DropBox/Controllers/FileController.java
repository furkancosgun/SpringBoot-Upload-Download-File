package com.furkancosgun.DropBox.Controllers;

import com.furkancosgun.DropBox.Entites.File;
import com.furkancosgun.DropBox.Services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping()
    public ResponseEntity<?> getAllFiles(){
        return ResponseEntity.status(HttpStatus.OK).body(fileService.getAllImages());
    }

    @PostMapping()
    public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile file) throws IOException {
        File response = fileService.uploadFile(file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/info/{name}")
    public ResponseEntity<?>  getFileInfoByName(@PathVariable("name") String name){
        File file = fileService.getInfoByFileByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .body(file);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?>  getFileByName(@PathVariable("name") String name){
        byte[] file = fileService.getFile(name);
        File fileInfo = fileService.getInfoByFileByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(fileInfo.getType()))
                .body(file);
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<?>  downloadFileByName(@PathVariable("name") String name) {
        byte[] file = fileService.getFile(name);
        File fileInfo = fileService.getInfoByFileByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfo.getName())
                .contentType(MediaType.valueOf(fileInfo.getType()))
                .body(file);
    }
}

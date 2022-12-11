package com.furkancosgun.DropBox.Services;

import com.furkancosgun.DropBox.Entites.File;
import com.furkancosgun.DropBox.Repositories.FileRepository;
import com.furkancosgun.DropBox.Utilties.FileUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public File uploadFile(MultipartFile fileData) throws IOException {
        File file = File.builder()
                .name(fileData.getOriginalFilename())
                .type(fileData.getContentType())
                .fileData(FileUtil.compressFile(fileData.getBytes())).build();
        fileRepository.save(file);
        return file;
    }

    @Transactional
    public File getInfoByFileByName(String name) {
        Optional<File> dbFile = fileRepository.findByName(name);

        return File.builder()
                .id(dbFile.get().getId())
                .name(dbFile.get().getName())
                .type(dbFile.get().getType())
                .fileData(FileUtil.decompressFile(dbFile.get().getFileData())).build();
    }

    @Transactional
    public byte[] getFile(String name) {
        Optional<File> dbFile = fileRepository.findByName(name);
        return  FileUtil.decompressFile(dbFile.get().getFileData());
    }

    @Transactional
    public List<File> getAllImages(){
        return  fileRepository.findAll();
    }


}

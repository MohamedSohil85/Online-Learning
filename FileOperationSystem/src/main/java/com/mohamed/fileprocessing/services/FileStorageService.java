package com.mohamed.fileprocessing.services;

import com.mohamed.fileprocessing.entities.FileDB;
import com.mohamed.fileprocessing.repositories.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageService {
    private final FileDBRepository fileDBRepository;

    public FileStorageService(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }


    public FileDB saveFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB();
        fileDB.setFileName(fileName);
        fileDB.setFileType(file.getContentType());
        fileDB.setFileData(file.getBytes());
        return fileDBRepository.save(fileDB);
    }
    public FileDB getFileById(String fileId) {
        return fileDBRepository.findById(fileId).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}

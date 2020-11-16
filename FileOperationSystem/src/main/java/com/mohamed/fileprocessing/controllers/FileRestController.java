package com.mohamed.fileprocessing.controllers;

import com.mohamed.fileprocessing.entities.FileDB;
import com.mohamed.fileprocessing.entities.ResponseFile;
import com.mohamed.fileprocessing.entities.ResponseMessage;
import com.mohamed.fileprocessing.services.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileRestController {
    private final FileStorageService storageService;

    public FileRestController(FileStorageService storageService) {
        this.storageService = storageService;
    }
    @PostMapping(value = "/uploadFile",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.saveFile(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @GetMapping(value = "/files",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getFileId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getFileName(),
                    fileDownloadUri,
                    dbFile.getFileType(),
                    dbFile.getFileData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping(value = "/getFileById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = storageService.getFileById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
                .body(fileDB.getFileData());
    }
}

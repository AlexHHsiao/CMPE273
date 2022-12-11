package com.example.cpme273.wlb.controller;

import com.example.cpme273.wlb.service.impl.AmazonClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(BucketController.PATH)
public class BucketController extends BaseController{
    private static final int VERSION = 1;
    static final String PATH = "/v" + VERSION + "/storage";

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping("/upload")
    @ApiOperation(value = "Upload File API. Use 'file' as the key.")
    public ResponseEntity<List<String>> uploadFile(@ApiIgnore @RequestHeader HttpHeaders httpHeaders,
                                                  @RequestPart(value = "file") MultipartFile[] file) {
        log.info("upload file");
        List<String> urlList = new ArrayList<>();
        for(MultipartFile f : file) {
            urlList.add(this.amazonClient.uploadFile(f));

        }
        log.info(urlList.toString());
        return ResponseEntity.ok().body(urlList);
    }

    @DeleteMapping("/deleteFile")
    @ApiOperation(value = "Delete File API. Use 'url' as the key.")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}

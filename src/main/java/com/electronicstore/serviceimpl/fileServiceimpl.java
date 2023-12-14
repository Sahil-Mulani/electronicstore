package com.electronicstore.serviceimpl;

import com.electronicstore.exception.BadApiRequest;
import com.electronicstore.service.FileServiceI;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;
@Service
@Slf4j
public class fileServiceimpl implements FileServiceI {


    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        log.info("Entering The Dao Call For Upload Image :" );
        String originalFilename = file.getOriginalFilename();
        log.info("orginalFileName : {}");

        String fileName = UUID.randomUUID().toString();

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileNameWithExtension = fileName + extension;

        String fullpathWithName = path + fileNameWithExtension;

        if (extension.equalsIgnoreCase(".Png") || extension.equalsIgnoreCase(".Jpg") || extension.equalsIgnoreCase(".Jpeg")) {

            File folder = new File(path);
            if(!folder.exists()){
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullpathWithName));
            log.info("Completed The Dao Call For Upload Image");
            return fileNameWithExtension;
        } else {

            throw new BadApiRequest("Do Not Insert File With This Extension "+extension+" This Is Not Allow ");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        log.info("Entering The Dao Call For Get Image :{}");

        String fullPath = path+File.separator + name;

        InputStream inputStream=new FileInputStream(fullPath);
        log.info("Completed The Dao Call For Get Image :{}");

        return inputStream;
    }


}
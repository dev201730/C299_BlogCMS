package com.sportsblog.blogCMSMastery.dao;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 
 * @author Silviu Badica
 * */
public interface ImageDAO {

    public String saveImage(MultipartFile file, String fileName, String directory);

    public String updateImage(MultipartFile file, String fileName, String directory);

    public boolean deleteImage(String fileName);
}

package com.example.demo.service;

import com.example.demo.model.File;

import java.util.List;
import java.util.Optional;

public interface FileService {
    /**
     * 保存文件
     * @param file
     * @return
     */
    File saveFile(File file);

    /**
     * 删除文件
     * @param id
     * @return
     */
    boolean removeFile(String id);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
   File getFileById(String id);

    List<File> findAll();
    List<File> findByIds(String ids);

    void update(String id,String dp,String vp);
}

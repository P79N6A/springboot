package com.example.demo.service.impl;

import com.example.demo.dao.FileRespository;
import com.example.demo.model.File;
import com.example.demo.service.FileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRespository fileRespository;


    @Override
    public File saveFile(File file) {
        return fileRespository.save(file);
    }

    @Override
    public boolean removeFile(String id) {
        if(StringUtils.isNotBlank(id)){
            fileRespository.delete(id);
            return  true;
        }
        return false;
    }

    @Override
    public File getFileById(String id) {
        return fileRespository.findOne(id);
    }

    @Override
    public List<File> findAll() {
        return fileRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
    }


    @Override
    public List<File> findByIds(String ids) {
        List<File> file=new ArrayList<>();
        String id=ids.substring(1,ids.length());
        String result[]=id.split(",");
        for (int i = 0; i <result.length ; i++) {
            file.add(fileRespository.findOne(result[i]));
        }
        return file;
    }

    @Override
    public void update(String id, String dp, String vp) {
        if(StringUtils.isNotBlank(id)){
            File exit=fileRespository.findOne(id);
            exit.setDown_path(dp);
            exit.setView_path(vp);
            fileRespository.save(exit);
        }
    }


}

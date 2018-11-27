package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.File;
import com.example.demo.service.FileService;
import io.swagger.annotations.Api;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "file")
@Api(value = "file", description = "文件服务接口")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * upload
     * @param file
     * @return
     */
    @RequestMapping(value = "upload")
    public ResultBean upload(@RequestParam("file") MultipartFile file) {
       String result;
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),new Binary(file.getBytes()));
            f.setMd5(getMD5(file.getInputStream()));
            File returnFile = fileService.saveFile(f);
            result=returnFile.getId();
            StringBuffer dp=new StringBuffer(FILE_PATH);
            StringBuffer vp=new StringBuffer(FILE_PATH);
            fileService.update(result,dp.append("/download?id=").append(result).toString(),vp.append("/view?id=").append(result).toString());
        } catch (IOException | NoSuchAlgorithmException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            result=sw.toString();

        }
        return new ResultBean<>(result);
    }


     /** 获取文件片信息
	 ** @param id
	 * @return
      * * @throws UnsupportedEncodingException
	 */
     @RequestMapping(value = "download")
    public ResponseEntity<Object> downFile(@RequestParam(value = "id") String id) throws UnsupportedEncodingException {
        File file = fileService.getFileById(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(file.getName().getBytes("utf-8"),"ISO-8859-1"))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "").header("Connection", "close")
                    .body(file.getContent().getData());
    }

    @RequestMapping(value = "view")
    public ResponseEntity<Object> viewFile(@RequestParam(value = "id") String id) {
        File file = fileService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "").header("Connection", "close")
                .body(file.getContent().getData());
    }
    /**
     * find  by ids
     * @param ids
     * @return
     */
    @GetMapping(value = "findByIds")
    public ResultBean<List<File>> findByIds(@RequestParam(value = "ids") String ids) {
        return new ResultBean<>(fileService.findByIds(ids));
    }

    @GetMapping(value = "findAll")
    public ResultBean<List<File>> findAll() {
        return new ResultBean<>(fileService.findAll());
    }

    @PostMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(fileService.removeFile(id));
    }

    /**
     * 获取该输入流的MD5值
     *
     * @param is
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();

        // convert the byte to hex format
        for (int i = 0; i < mdbytes.length; i++) {
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }

    private static String FILE_PATH="http://10.110.2.31:8080/technical/file";
}

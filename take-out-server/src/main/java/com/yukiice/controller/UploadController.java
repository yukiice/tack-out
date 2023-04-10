package com.yukiice.controller;

import com.yukiice.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/10 09:59
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class UploadController {
    @Value("${reggie.path}")
    private String uploadPathPrefix;
    /**
     * 文件上传处理
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        String uploadFullPath; //上传文件的完整路径
        String saveSqlPath; // 上传到数据库的文件路径
        String commonPrefix = "upload_resource/";
        String originalName = file.getOriginalFilename();
        assert originalName != null;
        String fileType = originalName.substring(originalName.lastIndexOf("."));
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String format = sdf.format(date);
        String uuid = UUID.randomUUID().toString().replace("-","");
        String saveDiskName = uuid+fileType;
        saveSqlPath =commonPrefix+format+saveDiskName;
        uploadFullPath = uploadPathPrefix+saveSqlPath;
        File file1 = new File(uploadFullPath);
        try {
            if (!file1.exists()){
                file1.mkdirs();
            }
            file.transferTo(file1);
        }catch (IOException e){
            throw new RuntimeException();
        }
        return  R.success(saveSqlPath);
    }

    @GetMapping("/download")
    public void  downLoad(String name, HttpServletResponse response){
        try {
            //        输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(uploadPathPrefix+name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

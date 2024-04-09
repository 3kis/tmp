package com.thinkstu.controller;

import com.thinkstu.common.MyCheckException;
import com.thinkstu.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.io.InputStream;
import java.util.UUID;

/**
 * 文件上传、下载管理
 *
 * @author : Asher
 * @since : 2024-04/15, 9:00 AM, 周日
 **/
@Api(tags = "文件上传下载系统")
@RestController
@RequestMapping("/common")
public class CommonController {
    // 初始路径导入
    @Value("${reggie.path}")
    String originPath;

    @PostMapping("upload")
    @ApiOperation("图片上传")
    R<String> upload(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String fileType = filename.substring(filename.lastIndexOf("."));
        filename = filename.substring(0, filename.lastIndexOf("."));
        String name = filename + "---" + UUID.randomUUID() + fileType;
        File site = new File(originPath);
        if (!site.exists()) {
            site.mkdirs();
        }
        try {
            file.transferTo(new File(site + "/" + name));
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyCheckException("图片上传失败！");
        }
        return R.success(name);
    }

    @GetMapping("download")
    @ApiOperation("图片下载（浏览器）")
    void downloadWithBrowser(HttpServletResponse response, String name) {
        try (ServletOutputStream outputStream = response.getOutputStream();
             InputStream inputStream = new FileInputStream(originPath + name)) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyCheckException("图片加载失败！");
        }
    }

}

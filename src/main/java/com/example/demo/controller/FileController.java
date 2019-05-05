package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @program: FileSave
 * @description: 文件上传控制器
 * @author: LeeY
 * @create: 2019-05-05 15:16
 **/

@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/save")
    public static String fileUpload(HttpServletRequest request) {
        String savePath = "/Users/wb/desktop/Work/backup/";

//        //这里的代码是我为了把文件分类保存到不同的文件夹中
//        if (fileType.equals("Video"))
//		savePath = servlet.getServletContext().getRealPath("\\WEB-INF\\upload\\") + "\\" + fileType;
//		else
//	    savePath = servlet.getServletContext().getRealPath("\\WEB-INF\\upload\\office\\") + "\\" + fileType;

        File file = new File(savePath);
        if (!file.exists() && !file.isDirectory()) {
		    file.mkdir();
        }
        String fileName = null;
        try {
		    Collection<Part> parts = request.getParts();
		    if (parts.size() == 1) {
		        Part part = request.getPart("file");
                String header = part.getHeader("content-disposition");
//                util.IPTimeStamp newName = new util.IPTimeStamp(header);//这个类是我自己写的根据时间生成名字的类，为了防止文件上传之后文件名重复
                fileName = getFileName(header);
                part.write(savePath + File.separator + fileName);
		        } else {
                for (Part part : parts) {
			    String header = part.getHeader("content-disposition");
			    if(header.indexOf("filename") != -1) {
			    fileName = getFileName(header);
			    part.write(savePath + File.separator + fileName);
			    }
		        }
		    }
        } catch (IOException e) {
		    e.printStackTrace();
        } catch (ServletException e) {
		    e.printStackTrace();
        }
	    return (savePath  + fileName);
    }


    public static String getFileName(String header) {//这个方法通过header获取文件的名字
	    String fileName = null;
	    fileName = header.substring(header.indexOf("filename") + 2 + "filename".length(), header.length() - 1);
        System.out.println(fileName);
	    return fileName;
    }

}

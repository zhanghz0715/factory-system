package com.factory.boot.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDate.now;

/**
 * @function 文件上传
 */
public class FileUpload {



    /**
     * @function  单文件上传
     * @return
     */
				                    //本地文件              拼接路径         文件类型 
    public static String fileOne(MultipartFile file, String saveUrl, String fileType){
	
	// 获取上传的文件名称，并结合存放路径，构建新的文件名称，并截取
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        String newUrl = saveUrl+now()+File.separator+fileName;  // /pro/pic/ghfgh.jpg
        String[] type_array = fileType.split(",");
        File saveFile = new File(StaticUtil.SAVE_URL_LINUX+newUrl);
	//判断文件是否存在
        if(!saveFile.getParentFile().exists()){
	//生成文件
            saveFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUrl;
    }


    /**
     * @function 多文件上传
     * @return
     */
    public static List<String> fileMany(MultipartFile[] files , String saveUrl, String fileType){
        List<String> picUrl = null;
        String newUrl = saveUrl + "\\" + now() + "\\pic\\";
        File saveDir = new File(newUrl);
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        for(MultipartFile file : files){
            if(file != null){
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID() + suffix;
                String newFileUrl = newUrl+fileName;
                File saveFile = new File(newFileUrl);
                try {
                    file.transferTo(saveFile);
                    picUrl.add(newFileUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("上传失败");
                }
            }
        }
        return picUrl;
    }

}

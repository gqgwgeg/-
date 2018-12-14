package pyg.shang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pyg.com.utis.FastDFSClient;
import pyg.com.utis.PygResult;

/**
 * Created by on 2018/8/13.
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    //注入图片服务器地址
    @Value("${fast_dfs_url}")
    private String fast_dfs_url;


    /**
     * 需求：文件上传操作
     */
    @RequestMapping("pic")
    public PygResult uploadPic(MultipartFile file) {

        try {
            //获取文件扩展名
            //获取文件名字
            String originalFilename = file.getOriginalFilename();
            //截取文件扩展名
            //jpg
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            //创建文件上传工具类对象
            FastDFSClient fdfs = new FastDFSClient("classpath:conf/client.conf");
            //上传
            //返回地址：group1/M00/00/00/wKhCQ1qYzxmAX8LgAAlnNJued40320.png
            String url = fdfs.uploadFile(file.getBytes(), extName);
            //拼接图片绝对地址
            url = fast_dfs_url+url;

            System.out.println(url);

            //上传图片成功
            return new PygResult(true,url);

        } catch (Exception e) {
            e.printStackTrace();
            //上传失败
            return new PygResult(false,"上传失败");
        }


    }


}

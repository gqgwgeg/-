import org.csource.fastdfs.*;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 使用fasetDFS    图片上传小案例
 */
public class testFDS {



    @Test
    public void updePic() throws Exception{
//图片地址
        String url = "C:\\Users\\admin\\Pictures\\Saved Pictures\\32ffa904f6.jpg";

        //指定图片服务器连接地址
        String a = "D:\\xiao\\pyg-shop-web\\src\\main\\resources\\conf\\client.conf";

        //加载客户端配置文件
        ClientGlobal.init(a);
        //创建tracker客户端对象
        TrackerClient tc = new TrackerClient();
        //从客户端对象中获取连接
        TrackerServer trackerServer = tc.getConnection();
        //初始化 storageServer
        StorageServer storageServer=null;
        //创建storge客户端对象
        StorageClient sc = new StorageClient(trackerServer, storageServer);
        String[] jpgs = sc.upload_file(url, "jpg", null);

        for (String jpg : jpgs) {
            System.out.println(jpg);
        }


    }
}


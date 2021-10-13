package com.caostudy.test;

import com.caostudy.ChatApplication;
import com.caostudy.utils.FastDFSClient;
import com.caostudy.utils.FileUtils;
import com.caostudy.utils.QRCodeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Cao Study
 * @description <h1>QRCodeTest</h1>
 * @date 2021-10-13 9:53
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QRCodeTest {
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Test
    public void test(){
        // 生成一个唯一的二维码
        String qrCodePath = "D:\\meet-chatStorage\\user\\2110125WCFR1BS3Cqrcode.png";
        qrCodeUtils.createQRCode(qrCodePath, "meet_qrcode:123456");
        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);

        String qrCodeUrl = "";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
            System.out.println(qrCodeUrl);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

}

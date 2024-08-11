package org.jeecg.modules.coderQ.util;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ScreenUtil {


    public static void doScreen(String url, String username, String qzId) {
        try {
            System.out.println(Thread.currentThread().getName() + "开始执行-----");
            DesiredCapabilities dcaps = new DesiredCapabilities();
            dcaps.setCapability("acceptSslCerts", true);
            dcaps.setCapability("takesScreenshot", true);
            dcaps.setCapability("cssSelectorsEnabled", true);
            dcaps.setJavascriptEnabled(true);
            dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                    "E:\\OwnProject\\2023\\GongAn\\WebQuZhen\\WebQZ_Service\\jeecg-boot-module-system\\src\\main\\resources\\static\\phantomjs.exe");
            PhantomJSDriver driver = new PhantomJSDriver(dcaps);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            long start = System.currentTimeMillis();
            driver.get(url);
            Thread.sleep(2000);
            JavascriptExecutor js = driver;
            for (int i = 0; i < 3; i++) {
                js.executeScript("window.scrollBy(0,1000)");
                Thread.sleep(2000);
            }
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Thread.sleep(3000);
            //利用FileUtils工具类的copyFile()方法保存getScreenshotAs()返回的文件对象
            FileUtils.copyFile(srcFile, new File("D:\\opt\\upFiles\\" + username + qzId + "\\" + System.currentTimeMillis() + "-" + IdUtil.fastSimpleUUID() + ".png"));
            System.out.println("耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package org.jeecg.modules.coderQ.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class test {

    public static void main(String[] args) throws IOException {

        File dir = new File("./src/main/java/img");
        if (dir.exists()) {
            System.out.println("该目录已存在!");
        } else {
            dir.mkdirs();
            System.out.println("该目录已创建!");
        }

        String url = "https://news.bjd.com.cn/2023/05/13/10429239.shtml";
        Document document = Jsoup.parse(new URL(url), 1000000);
        //  Elements body = document.getElementsByAttribute("body");
        // Element content = document.getElementsByAttribute("body");
//        Document document = Jsoup.connect(url).get();
        // System.out.println(content);
        Elements imgs = document.getElementsByTag("img");//根据标签属性
        System.out.println(imgs);
        int id = 0;
        for (Element img : imgs) {
            String imgSrc = img.attr("src");//根据属性查找
            StringBuilder builder = new StringBuilder("https:");
            System.out.println(imgSrc);
            id++;
            builder = builder.append(imgSrc);

            String str2 = builder.toString();
            System.out.println(str2);
            URL target = new URL(str2);
            URLConnection urlConnection = target.openConnection();
            InputStream is = urlConnection.getInputStream();
            OutputStream os = new FileOutputStream("./src/main/java/img/" + id + ".jpg");

            int temp = 0;
            while ((temp = is.read()) != -1) {
                os.write(temp);
            }
            System.out.println(id + ".jpg下载完毕！");
            os.close();
            is.close();
        }


    }

}

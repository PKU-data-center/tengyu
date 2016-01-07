package com.tengyu.fetch;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 16/1/1.
 */
public class Openke {


    private static void getAllCourseList() throws Exception {

        String detailInfo = "";
        String lessonSection = "";
        String title = "";
        String href = "";
        String shortDesc = "";

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        for (int i = 1; i <= 11; i++) {
            String path = "http://www.openke.net/list-28-"+i+"-click_count-DESC-text.html";
//            String path = "http://www.openke.net/list-29-"+i+"-click_count-DESC-text.html";
//            String path ="http://www.openke.net/list-30-"+i+"-click_count-DESC-text.html";
            HtmlPage page = webClient.getPage(path);
            List<String> list = new ArrayList<String>();
            DomNodeList<DomElement> elementsByTagName = page.getElementsByTagName("div");
            for (DomElement ele : elementsByTagName) {
                if (ele.getAttribute("class").equals("mode_item")) {
                    // detailInfo = ele.getTextContent().trim();
                    //title = ele.getTextContent().trim();
                    href = ele.getElementsByTagName("a").get(0).getAttribute("href");
                    title = ele.getElementsByTagName("a").get(1).getAttribute("title");
                    shortDesc = ele.getElementsByTagName("p").get(1).getTextContent().trim();
//                shortDesc  = ele.getNextElementSibling().getTextContent().trim();
//                fetchDetail(href, title, shortDesc);
                    System.out.print(title+"\n\n\n");
                    getDetailInfo(href, title, shortDesc);
                }
            }

        }
    }

    private static void getDetailInfo(String url, String title, String shortDesc) throws Exception {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        String prex_url = "http://www.openke.net/";



        String desc = "";
        //开课时间
        String courseTime = "";
        //课程信息类型
        String teacher_Info = "";
        Openke_DB db = new Openke_DB();



                url = prex_url + url;
            HtmlPage page = webClient.getPage(url);
            //获取title
//            DomNodeList<DomElement> elementsByTagName = page.getElementsByTagName("h2");
//            for (DomElement element : elementsByTagName) {
//                System.out.println("开始爬取" + url);
//                if (element.getAttribute("class").equals("f-fl")) {
//                    title = element.getTextContent();
//                    System.out.println(title);
//                    break;
//                }
//            }

          /*  //获取短描述
            elementsByTagName = page.getElementsByTagName("p");
            for (DomElement element : elementsByTagName) {
                if (element.getAttribute("class").equals("f-fc6")) {
                    shortdesc = element.getTextContent();
                    shortdesc = shortdesc.replaceAll("spContent=", "");
                    System.out.println(shortdesc);
                    break;
                }
            }*/

            //获取课程详细信息描述
        DomNodeList<DomElement> elementsByTagName = page.getElementsByTagName("ul");
            for (DomElement element : elementsByTagName) {
                if (element.getAttribute("class").equals("detail")) {
                    desc = element.getTextContent();
                    System.out.println(desc+"\n--------------\n");
                    break;
                }
            }
      //获取老师
                elementsByTagName = page.getElementsByTagName("ul");
                for (DomElement element : elementsByTagName) {
                    if (element.getAttribute("class").equals("details_list")) {
                        teacher_Info = element.getElementsByTagName("li").get(0).getTextContent().trim().replaceAll(" ", ""); ;

                        //shortdesc = shortdesc.replaceAll("spContent=", "");
                        System.out.println(teacher_Info+"\n!!!!!\n");
                        break;
                    }
                }
//            //获取课程开课时间
//            elementsByTagName = page.getElementsByTagName("div");
//            for (DomElement element : elementsByTagName) {
//                if (element.getAttribute("class").equals("m-termInfo f-cb")) {
//                    courseTime = element.getTextContent();
//                    courseTime = new String(courseTime.getBytes("ISO-8859-1"), "UTF-8");
//                    System.out.println(courseTime);
//                    break;
//                }
//            }
//
//            //获取课程开课时间
//            elementsByTagName = page.getElementsByTagName("div");
//            for (DomElement element : elementsByTagName) {
//                if (element.getAttribute("class").equals("m-teachers")) {
//                    teacher_Info = element.getTextContent();
//                    //System.out.println(teacher_Info);
//                    break;
//                }
//            }
//			System.out.print(title);
//			System.out.print(shortdesc);
        courseTime = "";
            db.insertRecord(title, shortDesc, desc, teacher_Info, url);


    }

    public static void main(String[] args) throws Exception {
        getAllCourseList();
    }
}
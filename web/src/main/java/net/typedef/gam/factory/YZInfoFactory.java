package net.typedef.gam.factory;

import net.typedef.gam.bean.BannerModel;
import net.typedef.gam.bean.ArticleModel;
import net.typedef.gam.bean.NavTagModel;
import net.typedef.gam.bean.TagsModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YZInfoFactory {
    public static List<NavTagModel> navTagListCache = new ArrayList<>();

    public static Document loadDocument(String url){
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static List<BannerModel> getBanners(){
        return  getBanners( loadDocument("https://www.gameres.com/"));
    }

    public static List<ArticleModel> getNews(int tid, int page){
        String url = String.format("https://www.gameres.com/newslistJson?p=%s&t=%s",page,tid);
        return getNews(loadDocument(url));
    }

    private static List<BannerModel> getBanners(Document document){
        Elements elements = document.body().select(".carousel-item");
        List<BannerModel> banners =  new ArrayList<>();
        for (Element element : elements) {
            Element titleElement = element.select(".carousel-title").get(0);
            if (titleElement == null) {
                continue;
            }
            Element tagA = titleElement.select("a").first();
            String articleUrl = tagA.attr("href");
            String title = tagA.html();
            String contain = element.select(".carousel-contain").attr("style").toString();
            String imgUrl = getUrl(contain);
            banners.add(new BannerModel(title,imgUrl,articleUrl));
        }
        Element innerPanel = document.body().select("#inner_menu_pane").select(".innerMenuList").first();

        List<NavTagModel> navTagModels = new ArrayList<>();
        for (Node childNode : innerPanel.childNodes()) {
            if(childNode instanceof Element){
                Element ele = (Element) childNode;
                String title = ele.select("h3").text();
                Elements lis = ele.select("li");
                List<TagsModel> tagsModels = new ArrayList<>();
                for (Element li : lis) {
                    String url = li.select("a").attr("href");
                    String tagsType = li.select("span").text();
                    String tidStr = url.replace("/list/","");
                    tagsModels.add(new TagsModel(tagsType,Integer.parseInt(tidStr)));
                }
                navTagModels.add(new NavTagModel(null,title,tagsModels));
            }
        }
        // TODO 将navTagModels存入数据库，或者是缓存中
        // TODO 存入缓存中需要加锁
        synchronized (navTagListCache){
            navTagListCache.clear();
            navTagListCache.addAll(navTagModels);
        }

        return banners;
    }

    private static List<ArticleModel> getNews(Document document){
        Elements elements = document.body().select(".feed-item");
        List<ArticleModel> newsList = new ArrayList<>();
        for (Element element : elements) {
            String arcticleUrl =  element.getElementsByTag("a").first().attr("href");
            arcticleUrl = "https://www.gameres.com"+arcticleUrl;
            String imgUrl = element.select(".feed-item-left").first()
                    .getElementsByTag("div").attr("data-original");
//            imgUrl = getUrl(imgUrl);

            Element right = element.select(".feed-item-right").first();
            String title = right.getElementsByTag("h3").first().html();
            String desc  = right.getElementsByTag("p").first().html();
            Element markInfo = right.selectFirst(".mark-info");
            String pubTime = markInfo.childNode(0).toString().replaceAll("\\n","");
            List<TagsModel> tags = new ArrayList<>();
            Elements tagsA = markInfo.selectFirst(".article-tag").getElementsByTag("a");
            for (Element tagA : tagsA) {
                String url = tagA.attr("href");
                String[] strArray = url.split("/");
                String id = strArray[strArray.length-1];
                int tid = Integer.parseInt(id);
                String tag = tagA.text();
                tags.add(new TagsModel(tag,tid));
            }
            newsList.add(new ArticleModel(title,desc,imgUrl,arcticleUrl,pubTime,tags));
        }
        return newsList;
    }

    private static String getUrl(String urlStr){
        Pattern pattern = Pattern.compile("(?<=url\\()[^\\)]+");
        Matcher matcher = pattern.matcher(urlStr);
        if(matcher.find()){
            return matcher.group();
        }else{
            return "";
        }
    }
}

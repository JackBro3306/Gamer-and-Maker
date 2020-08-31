package net.typedef.gam.service;


import com.google.common.base.Strings;
import net.typedef.gam.bean.*;
import net.typedef.gam.factory.YZInfoFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/YZInfo")
public class YZInfoService {
    @GET
    @Path("/getRecommend")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<RecommendModel> getRecommend(){
        List<BannerModel> banners = YZInfoFactory.getBanners();
        List<ArticleModel> news = YZInfoFactory.getNews(4,1);
        return ResponseModel.buildOk(new RecommendModel(banners,news));
    }

    @GET
    @Path("/getNews/tid/{tid}/page/{page:(.*)?}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<ArticleModel>> getNews(@PathParam("tid") String tid,
                                                     @PathParam("page") @DefaultValue("1") String page){
        int tidInt = 0;
        try {
            tidInt = Integer.parseInt(tid);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseModel.buildParameterError();
        }
        int pageInt = 1;
        if(!Strings.isNullOrEmpty(page)){
            try {
                pageInt = Integer.parseInt(page);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseModel.buildParameterError();
            }
        }

        List<ArticleModel> news = YZInfoFactory.getNews(tidInt,pageInt);
        return ResponseModel.buildOk(news);
    }

    @GET
    @Path("/getTags")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<NavTagModel>> getTags(){
        if(YZInfoFactory.navTagListCache.size() == 0){
            getRecommend();
        }
        return ResponseModel.buildOk(YZInfoFactory.navTagListCache);
    }
}

package net.typedef.gam;

import net.typedef.gam.provider.GsonProvider;
import net.typedef.gam.service.YZInfoService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

public class Application extends ResourceConfig {
    public Application(){
        // 组成逻辑处理的包名
        packages(YZInfoService.class.getPackage().getName());
        // 注册全局请求拦截器
//        register(AuthRequestFilter.class);
        // 注册Gson解析器，bean类中字段需要加上 @Expose
        register(GsonProvider.class);
        // 注册日志打印输出
        register(Logger.class);
    }
}

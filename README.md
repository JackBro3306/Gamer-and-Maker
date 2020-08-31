# Gamer-and-Maker
基于MVP+Rxjava2+Retrofit2+Dagger2的游戏资讯类app
## 项目架构
本项目分为两部分，服务端和客户端，服务端使用轻量RESTful Web服务框架Jersey，只需要简单的配置就可以搭建一个RESTful风格服务器，数据来源是使用jsoup爬取游资网的网页信息，仅供学习。dao层用mybatis或者hibernate都可以，由于时间安排问题，暂时没有做dao层的数据处理、客户端是基于MVP+RxJava2+Retrofit2+Dagger2，并进行了简单的框架封装，减少重复性的编码工作。  

后期打算把indianova的网站资源也加进去，不过是用kotlin+mvvn实现，顺便学习下google官方的jetpack组件包。

### 项目的结构如下：
* di：依赖注入
* presenter：包含了View、Presenter接口的定义以及presenter实现
* model：数据model部分，包含了数据操作相关的网络请求接口和处理类
* ui：用于存放View层相关的、如Activity、Fragment、自定义View、widget、adapter等
* utils：存放工具类

### 功能模块划分：
* 推荐
* 研发
    - 程序·引擎
    - 美术
    - 策划
    - 拆解分析
* 标签
    - 使用流式布局显示所有的标签分类
* 我的
    - 收藏
    - 设置
    - 关于
* 搜索
## api
### 推荐
http://localhost:8080/api/YZInfo/getRecommend
### 获取标签
http://localhost:8080/api/YZInfo/getTags
### 获取文章列表
http://localhost:8080/api/YZInfo/getNews/tid/{tid}/page/{page}

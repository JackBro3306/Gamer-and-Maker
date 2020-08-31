package com.typedef.gam.ui.recommend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.typedef.gam.R;
import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.BannerBean;
import com.typedef.gam.model.bean.TagsBean;
import com.typedef.gam.ui.base.recycler.RecyclerAdapter;
import com.typedef.gam.utils.DimenUtil;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RecommendRecyclerAdapter extends RecyclerView.Adapter<RecommendRecyclerAdapter.BaseViewHolder> {

    private List<ArticleBean> mArticleList = new ArrayList<>();
    private List<BannerBean> mBannerList;

    private RecyclerAdapter.OnClickListener<BannerBean> bannerListener;
    public void setBannerListener(RecyclerAdapter.OnClickListener<BannerBean> listener){
        this.bannerListener = listener;
    }

    private RecyclerAdapter.OnClickListener<ArticleBean> itemListener;
    public void setItemListener(RecyclerAdapter.OnClickListener<ArticleBean> listener){
        itemListener = listener;
    }

    private RecyclerAdapter.OnClickListener<TagsBean> tagsClickListener;
    public void setTagsClickListener(RecyclerAdapter.OnClickListener<TagsBean> listener){
        tagsClickListener = listener;
    }

    public void add(ArticleBean data){
        mArticleList.add(data);
        notifyItemInserted(getItemCount()-1);
    }

    public void add(ArticleBean... dataList){
        if(dataList != null&&dataList.length>0){
            int startPos = getItemCount();
            Collections.addAll(mArticleList,dataList);
            notifyItemRangeInserted(startPos,dataList.length);
        }
    }

    public void add(Collection<ArticleBean> dataList){
        if(dataList != null&&dataList.size()>0){
            int startPos = getItemCount();
            mArticleList.addAll(dataList);
            notifyItemRangeInserted(startPos,dataList.size());
        }
    }
    public void clear(){
        mArticleList.clear();
        notifyDataSetChanged();
    }

    public void setBannerList(List<BannerBean> bannerList){
        mBannerList = bannerList;
        notifyItemChanged(0);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case R.layout.item_article:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,parent,false);
                return new ArticleViewHolder(itemView);
            case R.layout.item_top_banner:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_banner,parent,false);
                return new BannerViewHolder(itemView);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,parent,false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final int position) {
        if(holder instanceof ArticleViewHolder){
            ArticleViewHolder nViewHolder = (ArticleViewHolder) holder;
            nViewHolder.itemListener = itemListener;
            nViewHolder.tagsClickListener =tagsClickListener;
            nViewHolder.convert(mArticleList.get(position-1),position-1);
        }else if(holder instanceof BannerViewHolder){
            BannerViewHolder bViewHolder = (BannerViewHolder) holder;
            bViewHolder.listener = bannerListener;
            bViewHolder.convert(mBannerList,position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return R.layout.item_top_banner;
        }else{
            return R.layout.item_article;
        }
    }

    @Override
    public int getItemCount() {
        return mArticleList.size()+1;
    }

    public abstract static class BaseViewHolder<T> extends RecyclerView.ViewHolder{

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public abstract void convert(T data,int position);
    }

    public static class ArticleViewHolder extends BaseViewHolder<ArticleBean>{

        ImageView header;
        TextView title;
        TextView desc;
        TextView publishTime;
        LinearLayout tagsContainer;
        RecyclerAdapter.OnClickListener<ArticleBean> itemListener;
        RecyclerAdapter.OnClickListener<TagsBean> tagsClickListener;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.iv_header);
            title = itemView.findViewById(R.id.tv_title);
            desc = itemView.findViewById(R.id.tv_desc);
            publishTime = itemView.findViewById(R.id.tv_pubTime);
            tagsContainer = itemView.findViewById(R.id.ll_tagsContainer);
        }


        public void convert(ArticleBean article,int position) {
            if(itemListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemListener.onItemClick(article,position);
                    }
                });
            }
            // 加载图片
            Glide.with(itemView.getContext())
                    .load(article.getThumbUrl())
                    .placeholder(R.mipmap.welcome_bg)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .into(header);

            title.setText(article.getTitle());
            desc.setText(article.getDesc());
            publishTime.setText(article.getPubTime());
            if(tagsContainer.getChildCount()>0)
                tagsContainer.removeAllViews();
            if(article.getTags()!=null&&article.getTags().size()>0){
                LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                // 最多放两个tag
                int size = article.getTags().size()>=2?2:article.getTags().size();
                for (int i=0;i<size;i++) {
                    TagsBean tag = article.getTags().get(i);
                    TextView textView = (TextView) inflater.inflate(R.layout.view_text_tag,tagsContainer,false);
                    textView.setText(tag.getType());
                    if(tagsClickListener != null){
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tagsClickListener.onItemClick(tag,position);
                            }
                        });

                    }
                    tagsContainer.addView(textView);
                }
            }
        }
    }

    public static class BannerViewHolder extends BaseViewHolder<List<BannerBean>>{

        RecyclerAdapter.OnClickListener<BannerBean> listener;

        Banner banner;
        RectangleIndicator indicator;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
            indicator = itemView.findViewById(R.id.banner_indicator);
        }

        @Override
        public void convert(List<BannerBean> datas,int position) {
            if(datas == null){
                datas = new ArrayList<>();
                datas.add(new BannerBean("轮播图","",""));
                datas.add(new BannerBean("轮播图","",""));
            }

            banner.setAdapter(new ArticleBannerAdapter(datas),true)
                    .setIndicator(indicator,false)
                    .setOnBannerListener(new OnBannerListener<BannerBean>() {
                        @Override
                        public void OnBannerClick(BannerBean data, int position) {
                            if(listener!=null)
                                listener.onItemClick(data,position);
                        }
                    })
                    .setBannerGalleryMZ(DimenUtil.getWindowWidth(itemView.getContext()) /10)
                    .setLoopTime(5000);
        }
    }

}

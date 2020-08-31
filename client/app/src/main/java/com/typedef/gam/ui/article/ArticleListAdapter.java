package com.typedef.gam.ui.article;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.typedef.gam.R;
import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.TagsBean;
import com.typedef.gam.ui.base.recycler.RecyclerAdapter;
import com.typedef.gam.ui.recommend.adapter.RecommendRecyclerAdapter;

public class ArticleListAdapter extends RecyclerAdapter<ArticleBean> {


    private AdapterListener<ArticleBean> itemListener;
    public void setItemListener(AdapterListener<ArticleBean> listener){
        itemListener = listener;
    }

    private AdapterListener<TagsBean> tagsClickListener;
    public void setTagsClickListener(AdapterListener<TagsBean> listener){
        tagsClickListener = listener;
    }
    @Override
    protected ViewHolder<ArticleBean> onCreateViewHolder(View root, int viewType) {
        ArticleViewHolder articleViewHolder = new ArticleViewHolder(root);
        articleViewHolder.itemListener = itemListener;
        articleViewHolder.tagsClickListener = tagsClickListener;
        return articleViewHolder;
    }

    @Override
    protected int getItemViewType(int position, ArticleBean data) {
        return R.layout.item_article;
    }

    public static class ArticleViewHolder extends ViewHolder<ArticleBean>{

        ImageView header;
        TextView title;
        TextView desc;
        TextView publishTime;
        LinearLayout tagsContainer;
        AdapterListener<ArticleBean> itemListener;
        AdapterListener<TagsBean> tagsClickListener;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.iv_header);
            title = itemView.findViewById(R.id.tv_title);
            desc = itemView.findViewById(R.id.tv_desc);
            publishTime = itemView.findViewById(R.id.tv_pubTime);
            tagsContainer = itemView.findViewById(R.id.ll_tagsContainer);
        }

        @Override
        protected void onBind(ArticleBean data, int position) {
            if(itemListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemListener.onItemClick(ArticleViewHolder.this,data);
                    }
                });
            }
            // 加载图片
            Glide.with(itemView.getContext())
                    .load(data.getThumbUrl())
                    .placeholder(R.mipmap.welcome_bg)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .into(header);

            title.setText(data.getTitle());
            desc.setText(data.getDesc());
            publishTime.setText(data.getPubTime());
            if(tagsContainer.getChildCount()>0)
                tagsContainer.removeAllViews();
            if(data.getTags()!=null&&data.getTags().size()>0){
                LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                // 最多放两个tag
                int size = data.getTags().size()>=2?2:data.getTags().size();
                for (int i=0;i<size;i++) {
                    TagsBean tag = data.getTags().get(i);
                    TextView textView = (TextView) inflater.inflate(R.layout.view_text_tag,tagsContainer,false);
                    textView.setText(tag.getType());
                    if(tagsClickListener != null){
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tagsClickListener.onItemClick(ArticleViewHolder.this,tag);
                            }
                        });
                    }
                    tagsContainer.addView(textView);
                }
            }
        }


    }
}

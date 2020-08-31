package com.typedef.gam.ui.recommend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.typedef.gam.R;
import com.typedef.gam.model.bean.BannerBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ArticleBannerAdapter extends BannerAdapter<BannerBean, ArticleBannerAdapter.BannerViewHolder> {

    public ArticleBannerAdapter(List<BannerBean> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_content,parent,false);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        itemView.setLayoutParams(lp);
        return new BannerViewHolder(itemView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerBean data, int position, int size) {
        holder.tv_title.setText(data.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(data.getImgUrl())
                .placeholder(R.mipmap.welcome_bg)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.iv_header);
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_header;
        TextView tv_title;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_header = itemView.findViewById(R.id.iv_header);
            tv_title = itemView.findViewById(R.id.tv_title);
        }

    }
}

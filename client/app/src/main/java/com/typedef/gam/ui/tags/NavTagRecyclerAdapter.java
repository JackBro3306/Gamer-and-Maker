package com.typedef.gam.ui.tags;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.flexbox.FlexboxLayout;
import com.typedef.gam.R;
import com.typedef.gam.model.bean.NavTagBean;
import com.typedef.gam.model.bean.TagsBean;
import com.typedef.gam.ui.base.recycler.RecyclerAdapter;

import butterknife.BindView;

public class NavTagRecyclerAdapter extends RecyclerAdapter<NavTagBean> {

    private OnClickListener<TagsBean> tagsListener;
    public void setTagsListener(OnClickListener<TagsBean> listener){
        tagsListener = listener;
    }
    @Override
    protected ViewHolder<NavTagBean> onCreateViewHolder(View root, int viewType) {
        return new TagViewHolder(root);
    }

    @Override
    protected int getItemViewType(int position, NavTagBean data) {
        return R.layout.item_nav_tags;
    }

    class TagViewHolder extends RecyclerAdapter.ViewHolder<NavTagBean> {
        @BindView(R.id.flexbox)
        FlexboxLayout flexboxLayout;
        @BindView(R.id.title)
        TextView tv_title;
        LayoutInflater inflater;
        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            inflater = LayoutInflater.from(itemView.getContext());
        }

        @Override
        protected void onBind(NavTagBean data, int position) {
            if(data.getTags()==null||data.getTags().size() == 0)return;
            tv_title.setText(data.getTitle());
            for (int i=0;i<data.getTags().size();i++) {
                TagsBean tag = data.getTags().get(i);
                TextView textView = (TextView) inflater.inflate(R.layout.view_nav_tag, (ViewGroup) itemView,false);
                textView.setText(tag.getType());
                flexboxLayout.addView(textView);
                if(tagsListener!=null){
                    int finalI = i;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tagsListener.onItemClick(tag, finalI);
                        }
                    });
                }


            }
        }
    }


}

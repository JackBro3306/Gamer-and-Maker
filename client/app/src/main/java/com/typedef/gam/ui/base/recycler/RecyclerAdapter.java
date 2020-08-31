package com.typedef.gam.ui.base.recycler;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.typedef.gam.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RecyclerAdapter<DT> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<DT>>
        implements View.OnClickListener,View.OnLongClickListener,AdapterCallBack<DT>{
    private final List<DT> mDataList ;
    private AdapterListener<DT> mListener;
    public RecyclerAdapter(List<DT> dataList,AdapterListener<DT> listener){
        mDataList = dataList;
        mListener = listener;
    }

    public RecyclerAdapter(AdapterListener<DT> listener){
        this(new ArrayList<DT>(),listener);
    }

    public RecyclerAdapter(){
        this(null);
    }
    /**
     * 创建一个ViewHolder
     * @param parent    RecyclerView
     * @param viewType  界面的类型，约定为xml布局的Id
     * @return
     */
    @NonNull
    @Override
    public ViewHolder<DT> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType,parent,false);
        ViewHolder<DT> holder = onCreateViewHolder(root,viewType);

        // 设置View的tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder,holder);

        // 设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        // 进行界面注解绑定
        holder.mUnbinder = ButterKnife.bind(holder,root);
        holder.callBack = this;
        return holder;
    }

    /**
     * 获得对应的ViewHolder
     * @param root  根布局
     * @param viewType  holder类型，约定为XML的ID
     * @return
     */
    protected abstract ViewHolder<DT> onCreateViewHolder(View root,int viewType);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<DT> holder, int position) {
        DT data = mDataList.get(position);
        holder.bind(data,position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position,mDataList.get(position));
    }

    /**
     * 得到布局的类型
     * @param position
     * @param data
     * @return XML文件的id，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position,DT data);

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<DT> getItems(){
        return mDataList;
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder<DT> holder) {
        super.onViewRecycled(holder);
        // 回收
        Log.i("Recycler","onViewRecycled");
        if(holder.mUnbinder != Unbinder.EMPTY){
            holder.mUnbinder.unbind();
        }
    }

    public void add(DT data){
        mDataList.add(data);
        notifyItemInserted(getItemCount()-1);
    }

    public void add(DT... dataList){
        if(dataList != null&&dataList.length>0){
            int startPos = getItemCount();
            Collections.addAll(mDataList,dataList);
            notifyItemRangeInserted(startPos,dataList.length);
        }
    }

    public void add(Collection<DT> dataList){
        if(dataList != null&&dataList.size()>0){
            int startPos = getItemCount();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos,dataList.size());
        }
    }
    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void replace(Collection<DT> dataList){
        mDataList.clear();
        if(dataList == null || dataList.size() == 0){
            return;
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void update(DT data, ViewHolder<DT> holder) {
        // 得到当前ViewHolder的坐标
        int pos = holder.getAdapterPosition();
        if(pos >=0){
            // 进行数据移除和更新
            mDataList.remove(pos);
            mDataList.add(pos,data);
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if(this.mListener != null){
            int pos = viewHolder.getAdapterPosition();
            mListener.onItemClick(viewHolder,mDataList.get(pos));
        }
    }

    public void setListener(AdapterListener<DT> adapterListener){
        this.mListener = adapterListener;
    }
    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if(this.mListener != null){
            int pos = viewHolder.getAdapterPosition();
            mListener.onItemLongClick(viewHolder,mDataList.get(pos));
            return true;
        }
        return false;
    }

    public interface AdapterListener<DT>{
        void onItemClick(ViewHolder holder, DT data);
        void onItemLongClick(ViewHolder holder, DT data);
    }

    public interface OnClickListener<T>{
        void onItemClick(T data,int position);
    }

    /**
     * 自定义的ViewHolder
     * @param <DT>
     */
    public static abstract class ViewHolder<DT> extends RecyclerView.ViewHolder{
        private Unbinder mUnbinder;
        protected DT mData;
        private AdapterCallBack<DT> callBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         * @param data
         */
        void bind(DT data,int position){
            mData = data;
            onBind(data, position);
        }
        protected abstract void onBind(DT data,int position);

        /**
         * holder更新对应Data
         * @param data
         */
        public void updateData(DT data){
            if(this.callBack != null){
                this.callBack.update(data,this);
            }
        }
    }

    public static class AdapterListenerImpl<DT> implements AdapterListener<DT>{

        @Override
        public void onItemClick(ViewHolder holder, DT data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, DT data) {

        }
    }

}

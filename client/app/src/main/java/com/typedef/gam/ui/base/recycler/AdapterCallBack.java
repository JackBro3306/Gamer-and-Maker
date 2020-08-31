package com.typedef.gam.ui.base.recycler;

public interface AdapterCallBack<DT> {
    void update(DT data, RecyclerAdapter.ViewHolder<DT> holder);
}

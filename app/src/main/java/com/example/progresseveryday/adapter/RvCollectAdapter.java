package com.example.progresseveryday.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progresseveryday.R;
import com.example.progresseveryday.model.CollectDatasBean;
import com.example.progresseveryday.utils.ClickUtil;

import java.util.List;

public class RvCollectAdapter extends RecyclerView.Adapter<RvCollectAdapter.ViewHolder>{
    private List<CollectDatasBean> collectDatasBeanList;
    int pos;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private View mHeaderView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public RvCollectAdapter(List<CollectDatasBean> collectDatasBeanList) {
        this.collectDatasBeanList = collectDatasBeanList;
    }


    //第一步：自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v);
    }

    public RvCollectAdapter.OnItemClickListener mOnItemClickListener;//第二步：声明自定义的接口

    //第三步：定义方法并暴露给外面的调用者
    public void setOnItemClickListener(RvCollectAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //根据pos返回不同的ItemViewType
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    //在此根据ItemViewType来决定返回何种ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mHeaderView != null && i == TYPE_HEADER)
            return new ViewHolder(mHeaderView);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_system_article_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == TYPE_HEADER) return;

        pos = getRealPosition(viewHolder);
        CollectDatasBean collectDatasBean = collectDatasBeanList.get(pos);
        viewHolder.itemHomeAuthor.setText(collectDatasBean.getAuthor());
        viewHolder.itemHomeDate.setText(collectDatasBean.getNiceDate());
        viewHolder.itemHomeTitle.setText(collectDatasBean.getTitle());
        viewHolder.itemHomeType.setText(collectDatasBean.getChapterName());
        viewHolder.itemHomeLove.setVisibility(View.GONE);

    }

    public List<CollectDatasBean> getDatas() {
        return collectDatasBeanList;
    }


    public void clearData() {
        collectDatasBeanList.clear();
        notifyItemRangeRemoved(0, collectDatasBeanList.size());

    }

    public void addData(List<CollectDatasBean> datas) {
        addData(0, datas);

    }

    public void addData(int position, List<CollectDatasBean> datas) {
        if (datas != null && datas.size() > 0) {
            collectDatasBeanList.addAll(datas);
            notifyItemRangeChanged(0, collectDatasBeanList.size());
        }
    }


    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    //返回正确的item个数
    @Override
    public int getItemCount() {
        return mHeaderView == null ? collectDatasBeanList.size() : collectDatasBeanList.size() + 1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView itemHomeAuthor;
        private TextView itemHomeDate;
        private TextView itemHomeTitle;
        private TextView itemHomeType;
        private ImageView itemHomeLove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemHomeAuthor = itemView.findViewById(R.id.item_home_author);
            itemHomeDate = itemView.findViewById(R.id.item_home_date);
            itemHomeTitle = itemView.findViewById(R.id.item_home_title);
            itemHomeType = itemView.findViewById(R.id.item_home_type);
            itemHomeLove = itemView.findViewById(R.id.item_home_love);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {

//                if (getAdapterPosition() == 0){
//
//                }else {
                if (ClickUtil.isFastClick() == false)
                    mOnItemClickListener.onItemClick(v, getAdapterPosition());
//                }

            }
        }


    }
}

package com.example.admin.recyclesimple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2017/12/28.
 */

public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MultipleItemModel> itemList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private OnItemChildClickListener onItemChildClickListener;

    public MultipleItemAdapter(Context context, List<MultipleItemModel> list) {
        this.mContext = context;
        this.itemList = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == MultipleItemModel.SECOND_TYPE) {    //  条件选择，选择哪种View
            return new SecondViewHolder(layoutInflater.inflate(R.layout.item1, null, false));
        } else if (viewType == MultipleItemModel.THIRD_TYPE) {
            return new ThirdViewHolder(layoutInflater.inflate(R.layout.item2, null, false));
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case MultipleItemModel.SECOND_TYPE:
                SecondViewHolder secondViewHolder = (SecondViewHolder) holder;    //  加载ViewHolder
                secondViewHolder.textView.setText(itemList.get(position).getItemName());
                break;
            case MultipleItemModel.THIRD_TYPE:
                ThirdViewHolder thirdViewHolder = (ThirdViewHolder) holder;
                thirdViewHolder.textView1.setText(itemList.get(position).getItemName1());
                thirdViewHolder.textView2.setText(itemList.get(position).getItemName2());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (itemList != null) {
            return itemList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }

    public MultipleItemModel getItem(int position) {

        if (position < 0) {
            return null;
        } else if (position > itemList.size()) {
            return null;
        }
        return itemList.get(position);
    }

    public interface OnItemClickListener {   // 重写点击事件
        void onItemClick(View view, int position);
    }


    public void setItemClickLisenter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(View view, int position);
    }

    public void setItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }


    //  ViewHolder 实现Item复用，有几种不同的Item就要写几种ViewHolder

    public class SecondViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public SecondViewHolder(final View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.itemText11);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener) {
                        onItemClickListener.onItemClick(textView, getAdapterPosition());
                    }
                }
            });
        }
    }

    public class ThirdViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;

        public ThirdViewHolder(final View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.itemText21);
            textView2 = itemView.findViewById(R.id.itemText22);

            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemChildClickListener) {
                        onItemChildClickListener.onItemChildClick(textView1, getAdapterPosition());
                    }
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemChildClickListener) {
                        onItemChildClickListener.onItemChildClick(textView2, getAdapterPosition());
                    }
                }
            });
        }
    }
}

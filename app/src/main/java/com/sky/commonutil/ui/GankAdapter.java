package com.sky.commonutil.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sky.commonutil.R;
import com.sky.commonutil.model.GankEntity;

import java.util.List;

/**
 * Created by tonycheng on 2017/4/26.
 */

public class GankAdapter extends RecyclerView.Adapter {

    private static final int TYPE_PURE_TEXT = 1;
    private static final int TYPE_TEXT_WITH_IMAGE = 2;

    private Context mContext;
    private List<GankEntity> mGankEntityList;
    private LayoutInflater mLayoutInflater;

    public GankAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<GankEntity> gankEntityList) {
        mGankEntityList = gankEntityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PURE_TEXT) {
            View itemView = mLayoutInflater.inflate(R.layout.item_pure_text, parent, false);
            return new TextViewHolder(itemView);
        } else if (viewType == TYPE_TEXT_WITH_IMAGE) {
            View itemView = mLayoutInflater.inflate(R.layout.item_text_and_iamge, parent, false);
            return new ImageViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GankEntity gankEntity = mGankEntityList.get(position);

        if (gankEntity == null) {
            return;
        }

        final int itemViewType = getItemViewType(position);

        if (itemViewType == TYPE_PURE_TEXT) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            final String desc = gankEntity.getDesc();
            if (!TextUtils.isEmpty(desc)) {
                textViewHolder.mTvDesc.setText(desc);
            }
            final String author = gankEntity.getWho();
            if (!TextUtils.isEmpty(author)) {
                textViewHolder.mTvAuthor.setText(author);
            }
            final String publishedAt = gankEntity.getPublishedAt();
            if (!TextUtils.isEmpty(publishedAt)) {
                textViewHolder.mTvTime.setText(publishedAt);
            }
        } else if (itemViewType == TYPE_TEXT_WITH_IMAGE) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            final List<String> images = gankEntity.getImages();
            Glide.with(mContext).load(images.get(0)).into(imageViewHolder.mIvImage);
            final String desc = gankEntity.getDesc();
            if (!TextUtils.isEmpty(desc)) {
                imageViewHolder.mTvDesc.setText(desc);
            }
            final String author = gankEntity.getWho();
            if (!TextUtils.isEmpty(author)) {
                imageViewHolder.mTvAuthor.setText(author);
            }
            final String publishedAt = gankEntity.getPublishedAt();
            if (!TextUtils.isEmpty(publishedAt)) {
                imageViewHolder.mTvTime.setText(publishedAt);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        final GankEntity gankEntity = mGankEntityList.get(position);
        if (gankEntity != null) {
            if (gankEntity.getImages() != null && gankEntity.getImages().size() > 0) {
                return TYPE_TEXT_WITH_IMAGE;
            } else {
                return TYPE_PURE_TEXT;
            }
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mGankEntityList == null ? 0 : mGankEntityList.size();
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvDesc;
        public TextView mTvAuthor;
        public TextView mTvTime;

        private TextViewHolder(View itemView) {
            super(itemView);
            mTvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            mTvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    private class ImageViewHolder extends TextViewHolder {

        private ImageView mIvImage;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mIvImage = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}

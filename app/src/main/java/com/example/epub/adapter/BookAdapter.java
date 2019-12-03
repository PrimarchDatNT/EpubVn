package com.example.epub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.epub.R;
import com.example.epub.model.DetailInformation;
import com.example.epub.ultis.Utils;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<DetailInformation> detailInformationList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public BookAdapter(Context context, List<DetailInformation>detailInformationList){
        this.context = context;
        this.detailInformationList = detailInformationList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(view, onItemClickListener);
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
      DetailInformation model = detailInformationList.get(position);
      RequestOptions requestOptions = new RequestOptions();
      requestOptions.placeholder(Utils.getRandomDrawableColor());
      requestOptions.error(Utils.getRandomDrawableColor());
      requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
      requestOptions.centerCrop();
      Glide.with(context).load(model.getCover()).apply(requestOptions)
                           .listener(new RequestListener<Drawable>() {
                               @Override
                               public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                   holder.progressBar.setVisibility(View.GONE);
                                   return false;
                               }

                               @Override
                               public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                   holder.progressBar.setVisibility(View.GONE);
                                   return false;
                               }
                           }).transition(DrawableTransitionOptions.withCrossFade()).into(holder.ivBookCover);
      holder.tvBookName.setText(model.getName());
      holder.tvAuthor.setText(model.getAuthor());
      holder.tvBookRate.setText(model.getRatingNumbers().toString());

    }

    @Override
    public int getItemCount() {
        return detailInformationList.size();
    }

    public void setItemOnclickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvBookName, tvAuthor, tvBookRate;
        ProgressBar progressBar;
        ImageView ivBookCover;
        OnItemClickListener onItemClickListener;

        BookViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvBookName = itemView.findViewById(R.id.tv_book_name);
            tvBookRate = itemView.findViewById(R.id.tv_book_rate);
            tvAuthor = itemView.findViewById(R.id.tv_book_author);
            ivBookCover = itemView.findViewById(R.id.iv_book_cover);
            progressBar = itemView.findViewById(R.id.progress_load_image);
            tvBookName.setSelected(true);
            tvAuthor.setSelected(true);
            this.onItemClickListener = mOnItemClickListener;
        }

        @Override
        public void onClick(View view) {

            onItemClickListener.onClick(view,getAdapterPosition(),false);

        }
    }
}

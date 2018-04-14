package com.felipeshiba.lab.nytimes.articles.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipeshiba.lab.nytimes.articles.R;
import com.felipeshiba.lab.nytimes.articles.list.Article;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class SearchArticlesAdapter extends RecyclerView.Adapter<SearchArticlesAdapter.ArticleViewHolder> {

    private List<Article> articles = Collections.emptyList();

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.title.setText(articles.get(position).getTitle());
        holder.pubDate.setText(articles.get(position).getPubDate());
        holder.summary.setText(articles.get(position).getSummary());
        Picasso.get()
                .load(articles.get(position).getPicture())
                .noPlaceholder()
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void addArticles(List<Article> articles) {
        int itemCount = getItemCount();
        this.articles.addAll(articles);
        notifyItemRangeInserted(itemCount, articles.size());
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView pubDate;
        TextView summary;
        ImageView picture;

        ArticleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            pubDate = itemView.findViewById(R.id.text_pub_date);
            summary = itemView.findViewById(R.id.text_summary);
            picture = itemView.findViewById(R.id.image_thumbnail);
        }
    }
}

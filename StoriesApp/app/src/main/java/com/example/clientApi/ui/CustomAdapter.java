package com.example.clientApi.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.clientApi.R;
import com.example.clientApi.model.Users;
import com.example.clientApi.model.database.Authors;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * Class to display the data with customer adapter where
 * @dataList attribute containing the data from url,
 * @authorList list of authors object from database
 * */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Users> dataList;
    private Context context;
    private List<Authors> authorsList;

    public CustomAdapter(Context context, List<Users> dataList, List<Authors> authorsList) {
        this.dataList = dataList;
        this.context = context;
        this.authorsList = authorsList;
    }
    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle,txtAuthor;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            coverImage = mView.findViewById(R.id.coverImage);
            txtAuthor = mView.findViewById(R.id.author);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        if (authorsList == null) {
            holder.txtTitle.setText(dataList.get(position).getTitle());
            holder.txtAuthor.setText(dataList.get(position).getAuthor().getAutherName());
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(dataList.get(position).getCoverImage())
                    .into(holder.coverImage);
        }else{
            holder.txtTitle.setText(authorsList.get(position).getArticleAuthor());
            holder.txtAuthor.setText(authorsList.get(position).getArticleTitle());
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(R.drawable.error)
                    .into(holder.coverImage);
        }


    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        }else{
            return authorsList.size();
        }

    }

}
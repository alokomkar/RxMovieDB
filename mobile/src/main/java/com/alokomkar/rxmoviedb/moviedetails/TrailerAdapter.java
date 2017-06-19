package com.alokomkar.rxmoviedb.moviedetails;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alokomkar.rxmoviedb.R;
import com.alokomkar.rxmoviedb.moviedetails.model.Result;
import com.bumptech.glide.Glide;

import java.util.List;

import static android.provider.MediaStore.Images.ImageColumns.ORIENTATION;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context mContext;
    private List<Result> mTrailerResults;
    private String YOU_TUBE_TRAILER_BASE_URL = "http://img.youtube.com/vi/";
    private OnTrailerClick listener;


    public TrailerAdapter(Context context, List<Result> results,OnTrailerClick onTrailerClick )
    {
        this.mContext=context;
       this. mTrailerResults=results;
        this.listener=onTrailerClick;

    }


    public interface  OnTrailerClick
    {
        void onTrailerClick(int position,List<Result> mTrailerResults);
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View grid=  LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_row,parent,false);
        return new TrailerViewHolder(grid,parent.getContext());
    }

    @Override
    public void onBindViewHolder(final TrailerViewHolder holder, int position) {

      String url=  YOU_TUBE_TRAILER_BASE_URL+mTrailerResults.get(position).getKey()+"/0.jpg";

        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .into(holder.trailerPoster);
    }

    @Override
    public int getItemCount() {
        return mTrailerResults.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView trailerPoster;

        public  TrailerViewHolder(View itemView, Context context)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            trailerPoster=(ImageView) itemView.findViewById(R.id.trailerImage);

        }


        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        listener.onTrailerClick(position,mTrailerResults);
                    }
        }
    }


}
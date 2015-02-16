package app.com.example.karthik.homework4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karthik on 2/12/2015.
 */


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Map<String,?>> mDataSet;
    private Context mContext;
    MovieData movieData=new MovieData();
    private  List<Map<String,?>> movieList=movieData.getMoviesList();
    OnItemClickListener mItemClickListener;


    //Constructor
    public MyRecyclerViewAdapter(Context myContext, List<Map<String,?>> myDataSet)
    {
        mContext=myContext;
        mDataSet=myDataSet;
    }

    public class ViewHolder_LowRating extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public CheckBox vCheckBox;
        public RatingBar vRatingBar;


        //Creating A ViewHolder Class
        public ViewHolder_LowRating(View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.movieimage);
            vTitle = (TextView) v.findViewById(R.id.movietitle);
            vDescription = (TextView) v.findViewById(R.id.moviedesc);
            vCheckBox = (CheckBox) v.findViewById(R.id.checkbox);
            vRatingBar = (RatingBar) v.findViewById(R.id.rating);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null)

                        mItemClickListener.OnItemClick(v, getPosition());
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null)
                        mItemClickListener.OnItemLongClick(v, getPosition());
                    return false;
                }


            });

            vCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HashMap movie = (HashMap) mDataSet.get(getPosition());
                    movie.put("selection", vCheckBox.isChecked());
                }
            });
        }
        public void bindMovieData(Map<String,?> movie){
            vTitle.setText((String)movie.get("name"));
            vIcon.setImageResource((Integer)movie.get("image"));
            vDescription.setText((String)movie.get("description"));
            float ratingvalue = Float.parseFloat(movie.get("rating").toString())/2;
            vRatingBar.setRating(ratingvalue);

            if(movie.get("selection")!=null)
                vCheckBox.setChecked((Boolean) movie.get("selection"));
        }
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Map<String,?> movie=mDataSet.get(position);

        switch(getItemViewType(position)){

            case 0: ViewHolder_HighRating v1=(ViewHolder_HighRating)holder;
                    v1.bindMovieData(movie);
                    break;
            case 1: ViewHolder_LowRating v2=(ViewHolder_LowRating) holder;
                    v2.bindMovieData(movie);
                    break;

        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnItemClickListener{
        public void OnItemClick(View v,int position);
        public void OnItemLongClick(View v, int position);

    }

    public void setOnClickListener(final OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener=mItemClickListener;
    }

    @Override
    public int getItemViewType(int position){
        int viewType;
        Double rating=(Double) mDataSet.get(position).get("rating");
        double base=8.0;
        int compare=Double.compare(rating.doubleValue(),base);
        if(compare>0)
            viewType=0;
        else
            viewType=1;
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder vh;
        if(viewType==0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_cardview, parent, false);
            vh=new ViewHolder_HighRating(v);
        }
        else{
        v=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cardview_best, parent, false);
        vh=new ViewHolder_LowRating(v);
        }


        return vh;
    }

    public Object getItem(int position) {
        return movieList.get(position);
    }





    public class ViewHolder_HighRating extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public CheckBox vCheckBox;
        public RatingBar vRatingBar;

        //Creating A ViewHolder Class
        public ViewHolder_HighRating(View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.movieimage);
            vTitle = (TextView) v.findViewById(R.id.movietitle);
            vDescription = (TextView) v.findViewById(R.id.moviedesc);
            vCheckBox = (CheckBox) v.findViewById(R.id.checkbox);
            vRatingBar = (RatingBar) v.findViewById(R.id.rating);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null)

                        mItemClickListener.OnItemClick(v, getPosition());
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null)
                        mItemClickListener.OnItemLongClick(v, getPosition());
                    return false;
                }


            });

            vCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HashMap movie = (HashMap) mDataSet.get(getPosition());
                    movie.put("selection", vCheckBox.isChecked());
                }
            });
        }





        public void bindMovieData(Map<String,?> movie){
            vTitle.setText((String)movie.get("name"));
            vIcon.setImageResource((Integer)movie.get("image"));
            vDescription.setText((String)movie.get("description"));
            float ratingvalue = Float.parseFloat(movie.get("rating").toString())/2;
            vRatingBar.setRating(ratingvalue);
            if(movie.get("selection")!=null)
                vCheckBox.setChecked((Boolean) movie.get("selection"));
        }

    }


}


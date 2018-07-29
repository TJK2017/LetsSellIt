package tk.seller.trevor.letssellit;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private Context mContext;
    private List<Upload> uploads;

    public MyAdapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_images, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Upload upload = uploads.get(position);


        //holder.textViewName.setText(upload.getName());
        holder.mImageView.setScaleType(ImageView.ScaleType.FIT_XY);


            //Loads images into image view
        Glide.with(mContext)
                .load(upload.getUrl())
                .into(holder.mImageView);

        // When an image is clicked it is passed onto the details ativity.
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mContext,DetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Url", upload.getUrl());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //public TextView textViewName;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            // textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}

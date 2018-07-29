package tk.seller.trevor.letssellit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<CreateList> galleryList;
        private Context context;

        public Adapter(Context context, ArrayList<CreateList> galleryList) {
            this.galleryList = galleryList;
            this.context = context;
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.details_activity, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(Adapter.ViewHolder viewHolder, int i) {
            viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Image",Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return galleryList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView title;
            private ImageView img;
            public ViewHolder(View view) {
                super(view);

                img = (ImageView) view.findViewById(R.id.img);
            }
        }

}

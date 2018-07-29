package tk.seller.trevor.letssellit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;



public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<categories_class> mCategoriesclassList;

    onGridItemClickListener mOnGridItemClickListener;

    //interface for the click events
    public interface onGridItemClickListener {
        void onItemClick(View v, int postion);
    }

    public void setOnItemClickListener(RecyclerGridAdapter.onGridItemClickListener onGridItemClickListener) {
        this.mOnGridItemClickListener = onGridItemClickListener;
    }

    public RecyclerGridAdapter(Context context, List<categories_class> categoriesclassList) {
        this.mContext = context;
        this.mCategoriesclassList = categoriesclassList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new RecyclerItemViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerItemViewHolder) {
            categories_class categoriesclass = mCategoriesclassList.get(position);
            ((RecyclerItemViewHolder) holder).tvTitle.setText(categoriesclass.getListName());
            ((RecyclerItemViewHolder) holder).contentParent.setBackgroundColor(categoriesclass.getListColor());
            ((RecyclerItemViewHolder) holder).ivContent.setImageDrawable(mContext.getResources().getDrawable(categoriesclass.getListImage()));
        }
    }

    @Override
    public int getItemCount() {
        return mCategoriesclassList.size();
    }

    //ViewHolder Class
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView ivContent;
        private View contentParent;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvSetting);
            ivContent = (ImageView) itemView.findViewById(R.id.ivSetting);
            contentParent = (View) itemView.findViewById(R.id.llItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnGridItemClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
}

package wx.it.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import wx.it.mall.R;
import wx.it.mall.config.Constant;
import wx.it.mall.listener.OnItemClickListener;
import wx.it.mall.pojo.Product;

public class CategoryRightAdapter extends RecyclerView.Adapter<CategoryRightAdapter.ProductViewHolder>implements View.OnClickListener{
    private Context context;
    private List<Product> mData;
    //private OnItemClickListener onItemClickListener;
    public CategoryRightAdapter(Context context, List<Product> mData) {
        this.context = context;
        this.mData = mData;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_category_right_list_item,null,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mData.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice()+"");
        Glide.with(context).load(Constant.API.BASE_URL+product.getIconUrl()).into(holder.icon_url);
        //
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {

    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public View itemView;
        public TextView name;
        public TextView price;
        public ImageView icon_url;
        public ProductViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = (TextView)itemView.findViewById(R.id.name);
            price =(TextView)itemView.findViewById(R.id.price);
            icon_url=(ImageView)itemView.findViewById(R.id.icon_url);
        }
    }
}

package wx.it.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.List;
import wx.it.mall.R;

import wx.it.mall.pojo.Param;

public class HomeTopBannerAndParamAdapter extends DelegateAdapter.Adapter<HomeTopBannerAndParamAdapter.BannerAndParamViewHolder>{
    public static final int TYPE_HEADER=0;
    public static final int TYPE_NORMAL=1;

    private View mHeadView;//头部的Banner
    private List<Param> mData;
    private Context context;
    private LayoutHelper layoutHelper;

    //构造函数
    public HomeTopBannerAndParamAdapter(List<Param> mData, Context context, LayoutHelper layoutHelper) {
        this.mData = mData;
        this.context = context;
        this.layoutHelper = layoutHelper;
    }

    public void setHeadeView(View headeView){
        this.mHeadView = headeView;
    }

    public LayoutHelper onCreateLayoutHelper() {
        return this.layoutHelper;
    }

   //根据位置返回不同的视图类型
    public int getItemViewType(int position) {
        if(this.mHeadView==null)
            return TYPE_NORMAL;
        if(position==0){
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    public HomeTopBannerAndParamAdapter.BannerAndParamViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(mHeadView!=null && viewType ==TYPE_HEADER)
            return new BannerAndParamViewHolder(mHeadView);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home_params_list_item,null,false);
        return new BannerAndParamViewHolder(view);
    }

    //数据绑定
    public void onBindViewHolder(HomeTopBannerAndParamAdapter.BannerAndParamViewHolder holder, int position) {
        if(getItemViewType(position)==TYPE_HEADER){
            return;
        }
        final int pos = getRealPosition(holder);
        holder.tv.setText(mData.get(pos).getName());
    }

    ///读取数据的位置
    private  int getRealPosition(RecyclerView.ViewHolder holder){
        int pos = holder.getLayoutPosition();
        return mHeadView == null?pos:pos-1;
    }

    //返回一共有多少数据
    public int getItemCount() {
        return mHeadView==null?mData.size():mData.size()+1;
    }

    public static  class  BannerAndParamViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public BannerAndParamViewHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.item_tv);
        }
    }
}

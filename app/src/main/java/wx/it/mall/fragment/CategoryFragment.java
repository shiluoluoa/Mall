package wx.it.mall.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import wx.it.mall.adapter.CategoryLeftAdapter;
import wx.it.mall.adapter.CategoryRightAdapter;
import wx.it.mall.config.Constant;
import wx.it.mall.pojo.Param;
import wx.it.mall.R;
import wx.it.mall.pojo.Product;
import wx.it.mall.pojo.ResponeCode;
import wx.it.mall.pojo.SverResponse;
import wx.it.mall.utils.JSONUtils;
import wx.it.mall.utils.Utils;

public class CategoryFragment extends Fragment {
    //左侧列表组件
    private RecyclerView leftRecyclerView ;
    //左侧分类参数
    private List<Param> leftCategoryData;
    //分类适配器
    private CategoryLeftAdapter categoryLeftAdapter;
    private RecyclerView rightRecyclerView ;
    private List<Product> rightProductData ;
    private CategoryRightAdapter categoryRightAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        loadParams();
        //bindRefreshLinstener();
        return view;
    }
    private void initView(View view){
        //初始化
        leftRecyclerView =(RecyclerView)view.findViewById(R.id.category_rv) ;
        leftCategoryData = new ArrayList<>();
        categoryLeftAdapter = new CategoryLeftAdapter(getActivity(),leftCategoryData);
        rightRecyclerView = (RecyclerView)view.findViewById(R.id.product_rv);
        rightProductData = new ArrayList<>();
        categoryRightAdapter = new CategoryRightAdapter(getActivity(),rightProductData);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        leftRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        leftRecyclerView.setAdapter(categoryLeftAdapter);

        //网格布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        //rightRecyclerView.addItemDecoration(new SpaceItemDecoration(Utils.dp2px(getActivity(),10),Utils.dp2px(getActivity(),5)));
        rightRecyclerView.setLayoutManager(gridLayoutManager);
        rightRecyclerView.setAdapter(categoryRightAdapter);
    }
    private void loadParams() {
        //加载产品分类参数
        OkHttpUtils.get().url(Constant.API.CATEGORY_PARAM_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("获取分类失败", "onError: "+e);
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("获取分类成功", "onResponse: "+response);
                final Type type = new TypeToken<SverResponse<List<Param>>>() {
                }.getType();
                SverResponse<List<Param>> result = JSONUtils.fromJson(response, type);
                Log.i("状态", "onResponse: "+result.getStatus());
                if (result.getStatus() == ResponeCode.SUCESS.getCode()) {
                    Log.i("状态", "onResponse: ");
                    if (result.getData() == null)
                        return;
                    leftCategoryData.addAll(result.getData());
                    String  typeId = leftCategoryData.get(0).getId()+"";
                    leftCategoryData.get(0).setPressed(true);
                    findProductByParam(typeId,1,10,true);
                    categoryLeftAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void findProductByParam(String productTypeId, int pageNum, int pageSize, final boolean flag){
        OkHttpUtils.get()
                .url(Constant.API.CATEGORY_PRODUCT_URL)
                .addParams("productTypeId",productTypeId)
                .addParams("partsId",0+"")
                .addParams("pageNum",pageNum+"")
                .addParams("pageSize",pageSize+"")
                .build()
                .execute(new StringCallback(){
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final Type type = new TypeToken<SverResponse<List<Product>>>(){}.getType();
                        SverResponse<List<Product>> result = JSONUtils.fromJson(response, type);
                        if (result.getStatus() == ResponeCode.SUCESS.getCode()){
                            if(result.getData()!=null){
                                rightProductData.addAll(result.getData());
                                categoryRightAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

}

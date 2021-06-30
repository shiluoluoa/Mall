package wx.it.mall.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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
import wx.it.mall.config.Constant;
import wx.it.mall.pojo.Param;
import wx.it.mall.R;
import wx.it.mall.pojo.ResponeCode;
import wx.it.mall.pojo.SverResponse;
import wx.it.mall.utils.JSONUtils;

public class CategoryFragment extends Fragment {
    private RecyclerView leftRecyclerView ;         //左侧列表组件
    private List<Param> leftCategoryData;           //左侧分类参数
    private CategoryLeftAdapter categoryLeftAdapter; //分类适配器
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
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        leftRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        leftRecyclerView.setAdapter(categoryLeftAdapter);

    }
    private void loadParams() {
        //加载产品分类参数
        OkHttpUtils.get()
                .url(Constant.API.CATEGORY_PARAM_URL)
                .build()
                .execute(new StringCallback() {
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
                            categoryLeftAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }
}

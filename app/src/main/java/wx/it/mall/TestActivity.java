package wx.it.mall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import wx.it.mall.config.Constant;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView img= (ImageView) findViewById(R.id.img);
                final TextView txt=(TextView)findViewById(R.id.showText);
                Glide.with(TestActivity.this).load("https://ae01.alicdn.com/kf/H37257983b3ce4be89517937875be05d2z.jpg")
                        .into(img);
                OkHttpUtils.get().url(Constant.API.CATEGORY_PARAM_URL).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
            }
        });
    }
}

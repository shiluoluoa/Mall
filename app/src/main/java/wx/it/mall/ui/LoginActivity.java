package wx.it.mall.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;

import okhttp3.Call;
import wx.it.mall.R;
import wx.it.mall.config.Constant;
import wx.it.mall.pojo.ResponeCode;
import wx.it.mall.pojo.SverResponse;
import wx.it.mall.pojo.User;
import wx.it.mall.utils.JSONUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText accountEdit;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountEdit = (EditText)findViewById(R.id.account);
        passwordEdit = (EditText)findViewById(R.id.password);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                //调用注册方法
                register();
                break;
        }
    }

    private void login(){
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if(TextUtils.isEmpty(account)){
            Toast.makeText(this,"请输入登陆账号",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入登陆密码",Toast.LENGTH_LONG).show();
            return;
        }
        OkHttpUtils.post()
                .url(Constant.API.USER_LOGIN_URL)
                .addParams("account",account)
                .addParams("password",password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(LoginActivity.this,"网络问题，请稍后重试!",Toast.LENGTH_SHORT).show();;

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Type type = new TypeToken<SverResponse<User>>(){}.getType();
                        SverResponse<User> result = JSONUtils.fromJson(response,type);
                        if(result.getStatus()== ResponeCode.SUCESS.getCode()){
                            //发送本地广播
                            Intent intent = new Intent(Constant.ACTION.LOAD_CART_ACTION);
                            LocalBroadcastManager.getInstance(LoginActivity.this).sendBroadcast(intent);
                            LoginActivity.this.finish();

                        }else{
                            Toast.makeText(LoginActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();;
                        }

                    }
                });
    }


    public void register(){
        //跳转注册界面

        Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
        startActivity(intent);
    }
}

package wx.it.mall.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class RegistActivity extends AppCompatActivity {
    private EditText accountEdit;
    private EditText passwordEdit;
    private EditText emailEdit;
    private EditText phoneEdit;
    private EditText questionEdit;
    private EditText aswEdit;
    private EditText ageEdit;
    private RadioGroup regist_sex;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        accountEdit=findViewById(R.id.regist_account);
        passwordEdit=findViewById(R.id.regist_password);
        emailEdit=findViewById(R.id.regist_email);
        phoneEdit=findViewById(R.id.regist_phone);
        questionEdit=findViewById(R.id.regist_question);
        aswEdit=findViewById(R.id.regist_asw);
        ageEdit=findViewById(R.id.regist_age);
        regist_sex=findViewById(R.id.regist_sex);

        regist_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.m){
                    sex=1+"";
                }else {
                    sex=0+"";
                }
            }
        });

        findViewById(R.id.bt_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                String email=emailEdit.getText().toString();
                String phone=phoneEdit.getText().toString();
                String question=questionEdit.getText().toString();
                String asw=aswEdit.getText().toString();
                String age=ageEdit.getText().toString();

                if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)||TextUtils.isEmpty(email)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(question)||TextUtils.isEmpty(asw)){


                    Toast.makeText(RegistActivity.this,"请将内容填写完整",Toast.LENGTH_LONG).show();
                    return;
                }

                OkHttpUtils.post()
                        .url(Constant.API.USER_REGIST_URL)
                        .addParams("account",account)
                        .addParams("password",password)
                        .addParams("email",email)
                        .addParams("phone",phone)
                        .addParams("question",question)
                        .addParams("asw",asw)
                        .addParams("age",age)
                        .addParams("sex",sex)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(RegistActivity.this,"网络问题，请稍后重试!",Toast.LENGTH_SHORT).show();;

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Type type = new TypeToken<SverResponse<User>>(){}.getType();
                                SverResponse<User> result = JSONUtils.fromJson(response,type);
                                if(result.getStatus()== ResponeCode.SUCESS.getCode()){
                                    //跳转登陆界面

                                    Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(RegistActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();;
                                }

                            }
                        });

            }
        });


    }
}

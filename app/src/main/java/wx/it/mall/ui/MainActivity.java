package wx.it.mall.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import wx.it.mall.R;
import wx.it.mall.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup;

    //定义tab对应的Fragment
    private HomeFragment homeFragment;
    //private CategoryFragment categoryFragment;
    //private Fragment cartFragment;
    //private Fragment userFragment;

    private RadioButton mRadioButtonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initFragment();
        //bindEvent();
    }

    //初始化Fragment
    private void initFragment(){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        homeFragment = new HomeFragment();
        ft.add(R.id.container,homeFragment,"home");
        /*categoryFragment = new CategoryFragment();
        ft.add(R.id.container,categoryFragment,"category");
        cartFragment = new CartFragment();
        ft.add(R.id.container,cartFragment,"cart");
        userFragment = new UserFragment();
        ft.add(R.id.container,userFragment,"user");
        ft.show(homeFragment).hide(categoryFragment).hide(cartFragment).hide(userFragment).commit();*/
    }

    //监听change事件
    private void bindEvent(){
        //查找控件
        mRadioGroup = (RadioGroup)findViewById(R.id.radio_group_button);
        mRadioButtonHome = (RadioButton)findViewById(R.id.radio_button_home);
        //监听change事件，切换Fragment
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch(checkedId){
                    case R.id.radio_button_home:
                        //ft.show(homeFragment).hide(categoryFragment).hide(cartFragment).hide(userFragment).commit();
                        ft.show(homeFragment);
                        break;
                    /*case R.id.radio_button_category:
                        ft.show(categoryFragment).hide(homeFragment).hide(cartFragment).hide(userFragment).commit();

                        break;
                    case R.id.radio_button_cart:
                        ft.show(cartFragment).hide(categoryFragment).hide(homeFragment).hide(userFragment).commit();

                        break;
                    case R.id.radio_button_user:
                        ft.show(userFragment).hide(categoryFragment).hide(cartFragment).hide(homeFragment).commit();

                        break;*/
                }
            }
        });

        mRadioButtonHome.setChecked(true);
    }

    //@param savedInstanceState
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for(int i = 0;i<mRadioGroup.getChildCount();i++){
            RadioButton mTab = (RadioButton)mRadioGroup.getChildAt(i);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag((String)mTab.getTag());
            FragmentTransaction ft = fm.beginTransaction();
            if(fragment!=null){
                if(!mTab.isChecked()){
                    ft.hide(fragment);
                }
            }
            ft.commit();
        }
    }
}

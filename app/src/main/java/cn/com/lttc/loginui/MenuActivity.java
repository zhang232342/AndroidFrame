package cn.com.lttc.loginui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static cn.com.lttc.loginui.R.id.id_tab_1;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{
    //声明ViewPager
    private ViewPager mViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;

    //四个Tab对应的布局
    private LinearLayout mTab1;
    private LinearLayout mTab2;
    private LinearLayout mTab3;
    private LinearLayout mTabSetting;

    //四个Tab对应的ImageButton
    private ImageButton mImgTab1;
    private ImageButton mImgTab2;
    private ImageButton mImgTab3;
    private ImageButton mImgSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
        mImgTab1.setImageResource(R.mipmap.tab1_green);

    }
    private void initDatas() {
        mFragments = new ArrayList<>();
        //将四个Fragment加入集合中
        mFragments.add(new FirstFragment());
        mFragments.add(new SecondFragment());
        mFragments.add(new ThirdFragment());
        mFragments.add(new SettingFragment());

        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }

        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        //设置ViewPager的切换监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                mViewPager.setCurrentItem(position);
                resetImgs();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置四个Tab的点击事件
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);

    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mTab1 = (LinearLayout) findViewById(R.id.id_tab_1);
        mTab2 = (LinearLayout) findViewById(R.id.id_tab_2);
        mTab3 = (LinearLayout) findViewById(R.id.id_tab_3);
        mTabSetting = (LinearLayout) findViewById(R.id.id_tab_setting);

        mImgTab1 = (ImageButton) findViewById(R.id.id_tab_1_img);
        mImgTab2 = (ImageButton) findViewById(R.id.id_tab_2_img);
        mImgTab3 = (ImageButton) findViewById(R.id.id_tab_3_img);
        mImgSetting = (ImageButton) findViewById(R.id.id_tab_setting_img);

    }
    @Override
    public void onClick(View view) {
        //先将四个ImageButton置为灰色
        resetImgs();

        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
        switch (view.getId()) {
            case R.id.id_tab_1:
                selectTab(0);
                break;
            case R.id.id_tab_2:
                selectTab(1);
                break;
            case R.id.id_tab_3:
                selectTab(2);
                break;
            case R.id.id_tab_setting:
                selectTab(3);
                break;
        }
    }
    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为绿色
        switch (i) {
            case 0:
                mImgTab1.setImageResource(R.mipmap.tab1_green);
                break;
            case 1:
                mImgTab2.setImageResource(R.mipmap.tab2_green);
                break;
            case 2:
                mImgTab3.setImageResource(R.mipmap.tab3_green);
                break;
            case 3:
                mImgSetting.setImageResource(R.mipmap.setting_green);
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //将四个ImageButton设置为灰色
    private void resetImgs() {
        mImgTab1.setImageResource(R.mipmap.tab1);
        mImgTab2.setImageResource(R.mipmap.tab2);
        mImgTab3.setImageResource(R.mipmap.tab3);
        mImgSetting.setImageResource(R.mipmap.setting);
    }
}

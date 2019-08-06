package cn.com.lttc.loginui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;


public class PerMsgActivity extends FragmentActivity implements View.OnClickListener{
    private TextView textView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.per_msg);
        initView();
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        String account = bundle.getString("account");
        String nickName = bundle.getString("nickName");
        textView.setText(account+nickName);

    }
    //初始化视图
    private void initView() {
        textView = findViewById(R.id.person_msg);
        textView.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

    }
}

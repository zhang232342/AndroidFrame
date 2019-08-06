package cn.com.lttc.loginui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import utils.HttpLogin;
import utils.PathName;

public class SettingFragment  extends Fragment {
    //按钮
    private Button mPerMsgBut;//个人信息按钮
    private Button mExitBut;//退出按钮
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_tab, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPerMsgBut = getActivity().findViewById(R.id.per_msg);//绑定按钮
        mExitBut = getActivity().findViewById(R.id.exit);//绑定按钮
        mPerMsgBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
                queryPer();

            }
        });

    }
    public void queryPer(){//查询个人信息方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences preference = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
                    Log.d("开始","开始进入方法");
                    String str = HttpLogin.QueryPerMsg(preference);
                    Log.d("1",str);
                    Log.d("返回值",str);
                    Log.d("结束","结束查询");
                    Bundle bundle = new Bundle();
                    bundle.putString("result",str);
                    Message msg = new Message();
                    msg.setData(bundle);
                    JSONObject jsonObject = new JSONObject(str);
                    String status = jsonObject.getString("status");
                    if(PathName.SUCCESS.equals(status)){
                    String account = jsonObject.getString("account");
                    String nickName = jsonObject.getString("nick_name");
                    Intent intent = new Intent(getActivity(),PerMsgActivity.class);
                    //用Bundle携带数据
                    Bundle bundles=new Bundle();
                    //传递参数
                    bundles.putString("account", account);
                    bundles.putString("nickName", nickName);
                    intent.putExtras(bundles);
                    startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

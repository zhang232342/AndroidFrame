package utils;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import cn.com.lttc.loginui.MainActivity;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.System.in;


public class HttpLogin {
    private static String LOGIN_URL =  PathName.PATH_ADDRESS;
    private static String PERMSG_URL =  PathName.PER_MSG;
    private static SharedPreferences preference;
    private static SharedPreferences.Editor editor;
    public static String LoginByPost(String username,String passwd){
        Log.d(MainActivity.TAG,"启动登录线程");
        String msg = "";
        try {
            //初始化URL
            URL url = new URL(LOGIN_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d(MainActivity.TAG,"11111");
            //设置请求方式
            conn.setRequestMethod("POST");

            //设置超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            //设置允许输入
            conn.setDoInput(true);
            //设置允许输出
            conn.setDoOutput(true);

            //post方式不能设置缓存，需手动设置为false
            conn.setUseCaches(false);

            //我们请求的数据
            String data = "password="+ URLEncoder.encode(passwd,"UTF-8")+
                    "&username="+URLEncoder.encode(username,"UTF-8");

            //獲取輸出流
            OutputStream out = conn.getOutputStream();

            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();
            Log.d("信息：",conn.getResponseCode()+"");

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                msg = new String(message.toByteArray());

                return msg;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(MainActivity.TAG,"exit");
        return msg;
    }

    public static String QueryPerMsg(SharedPreferences pre){
        Log.d(MainActivity.TAG,"启动查询个人信息线程");
        String msg = "";
        try {
            //初始化URL
            URL url = new URL(PERMSG_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //设置请求方式
            con.setRequestMethod("GET");
            //注意，把存在本地的cookie值加在请求头上
            con.setRequestProperty("Cookie", pre.getString("tokens", ""));
            InputStream is=con.getInputStream();
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            byte[]buffer=new byte[1024];
            int len=0;
            while((len=is.read(buffer))>0){
                bos.write(buffer,0,len);
            }
            bos.flush();
            is.close();
            byte []result=bos.toByteArray();
             msg = new String(result);
             return msg;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(MainActivity.TAG,"exit");
        return msg;
    }
}

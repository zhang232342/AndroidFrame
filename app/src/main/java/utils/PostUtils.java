package utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
public class PostUtils {
    private static String PATH = PathName.PATH_ADDRESS;
    private static URL url;

    static {
        try {
            url = new URL(PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过给定的请求参数和编码格式，获取服务器返回的数据
     * @param params 请求参数
     * @param encode 编码格式
     * @return 获得的字符串
     */
    public static String sendPostMessage(Map<String, String> params,
                                         String encode) {
        StringBuffer buffer = new StringBuffer();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    buffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), encode))
                            .append("&");//请求的参数之间使用&分割。
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
            buffer.deleteCharAt(buffer.length() - 1);
            System.out.println(buffer.toString());
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();
                urlConnection.setConnectTimeout(3000);
                //设置允许输入输出
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                byte[] mydata = buffer.toString().getBytes();
                //设置请求报文头，设定请求数据类型
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                //设置请求数据长度
                urlConnection.setRequestProperty("Content-Length",
                        String.valueOf(mydata.length));
                //设置POST方式请求数据
                urlConnection.setRequestMethod("POST");
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(mydata);
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    return changeInputStream(urlConnection.getInputStream(),
                            encode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 把服务端返回的输入流转换成字符串格式
     * @param inputStream 服务器返回的输入流
     * @param encode 编码格式
     * @return 解析后的字符串
     */
    private static String changeInputStream(InputStream inputStream,
                                            String encode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result="";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data,0,len);
                }
                result=new String(outputStream.toByteArray(),encode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

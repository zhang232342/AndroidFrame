package utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class DownLoadUtils {
    public static long downLoadApk(Context context,String title,String url){

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS,"ausee.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置 Notification 信息
        request.setTitle(title);
        request.setDescription("下载完成后请点击打开");
        request.setVisibleInDownloadsUi(true);
        request.allowScanningByMediaScanner();
        request.setMimeType("application/vnd.android.package-archive");

        // 实例化DownloadManager 对象
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        final long refrence = downloadManager.enqueue(request);

        return refrence;
    }

}

package com.xckevin.download;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.xckevin.androiddownloadcomponent.R;

/**
 * Created by ty on 2015/11/18.
 */
public class DefaultDownloadListener implements DownloadListener {

    private final static String TAG = "DefaultDownloadListener" ;

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private final int notifyID = 101;

    private Context mContext;
    private int iconID;

    public DefaultDownloadListener(Context ctx, int iconID){
        this.mContext = ctx;
        this.iconID = iconID;
    }

    @Override
    public void onDownloadStart(DownloadTask task) {
        Log.d(TAG, "Start");
        callNotification(mContext, iconID, task.getName());
    }

    @Override
    public void onDownloadUpdated(DownloadTask task, long finishedSize, long trafficSpeed) {
        Long percent = task.getDownloadFinishedSize()*100 / task.getDownloadTotalSize();
        updateNotificationProgress(percent.intValue());
    }

    @Override
    public void onDownloadPaused(DownloadTask task) {
        Log.d(TAG, "Paused");
    }

    @Override
    public void onDownloadResumed(DownloadTask task) {
        Log.d(TAG, "Resumed");
    }

    @Override
    public void onDownloadSuccessed(DownloadTask task) {
        Log.d(TAG, "Successed:" + task.toString());
        finishNotification();
    }

    @Override
    public void onDownloadCanceled(DownloadTask task) {
        Log.d(TAG, "Canceled");
    }

    @Override
    public void onDownloadFailed(DownloadTask task) {
        Log.d(TAG, "Failed");
    }

    @Override
    public void onDownloadRetry(DownloadTask task) {
        Log.d(TAG, "Retry");
    }


    private void callNotification(Context context, int iconID, String name) {

        Log.d(TAG,"callNotification" + context.getClass().getName());
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 取得系統的通知服務
        notificationManager.cancelAll();
        notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(iconID)
                .setContentTitle("Start Download " + name)
                .setContentText("downloading...");

    }

    private void updateNotificationProgress(int p) {
        notificationBuilder.setProgress(100, p, false);
        // Displays the progress bar for the first time.
        notificationManager.notify(notifyID, notificationBuilder.build());
    }

    private void finishNotification(){
        Log.d("test","finish");
        notificationBuilder.setContentText("Download complete")
                .setProgress(0,0,false);
        notificationManager.notify(notifyID, notificationBuilder.build());
    }


}

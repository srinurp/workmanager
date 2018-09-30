package zoftino.com.offers.workmanager;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class RefreshScheduler {

    public static void refreshCouponOneTimeWork(LifecycleOwner lifecycleOwner) {

        //worker input
        Data source = new Data.Builder()
                .putString("workType", "OneTime")
                .build();

        //One time work request
        OneTimeWorkRequest refreshCpnWork =
                new OneTimeWorkRequest.Builder(RefreshLatestCouponWorker.class)
                        .setInputData(source)
                        .build();
        //enqueue the work request
        WorkManager.getInstance().enqueue(refreshCpnWork);

        //listen to status and data from worker
        WorkManager.getInstance().getStatusById(refreshCpnWork.getId())
                .observe(lifecycleOwner, status -> {
                    if (status != null && status.getState().isFinished()) {
                        String refreshTime = status.getOutputData().getString("refreshTime");
                        Log.i("refreshCouponWork","refresh time: "+refreshTime);
                    }
                });
    }

    public static void refreshCouponPeriodicWork() {

        //define constraints
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build();

        Data source = new Data.Builder()
                .putString("workType", "PeriodicTime")
                .build();

        PeriodicWorkRequest refreshCpnWork =
                new PeriodicWorkRequest.Builder(RefreshLatestCouponWorker.class, 16, TimeUnit.MINUTES)
                        .setConstraints(myConstraints)
                        .setInputData(source)
                        .build();

        WorkManager.getInstance().enqueue(refreshCpnWork);
    }
}

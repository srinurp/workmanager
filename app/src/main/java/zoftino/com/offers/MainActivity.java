package zoftino.com.offers;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import zoftino.com.offers.viewmodel.OffersViewModel;

public class MainActivity extends AppCompatActivity {

    private OffersViewModel viewModel;
    private TextView latestCpn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latestCpn = findViewById(R.id.latest_coupon);
        viewModel = ViewModelProviders.of(this,
                new OffersViewModel.OffersViewModelFactory(this))
                .get(OffersViewModel.class);

        viewModel.getLatestCoupon().observe(this, lcpn -> {
            if(lcpn != null) {
                latestCpn.setText(lcpn.getStore() + " " + lcpn.getOffer());
            }
        });
    }

    public void schedulePeriodicWork(View v){
        setupCouponRefreshPeriodicTask();
    }

    private void setupCouponRefreshPeriodicTask(){

        SharedPreferences preferences = PreferenceManager.
                 getDefaultSharedPreferences(this);

        //schedule recurring task only once
        if(!preferences.getBoolean("refreshTask", false)){
            viewModel.setupPeriodicCpnRefreshWork();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("refreshTask", true);
            editor.commit();
        }
    }
}

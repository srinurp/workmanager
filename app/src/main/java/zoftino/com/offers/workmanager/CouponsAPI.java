package zoftino.com.offers.workmanager;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import zoftino.com.offers.data.LocalRepository;
import zoftino.com.offers.data.Offer;
import zoftino.com.offers.data.OfferDAO;

public class CouponsAPI {
    private String TAG = "CouponsAPI";
    private static final String couponService = "LatestCoupons";
    private String couponsServiceUrl = "";

    private Context context;

    public CouponsAPI(String url, Context ctx){
        couponsServiceUrl = url+couponService;
        context = ctx;
    }
    //get latest coupons from remote service using okhttp
    public void getLatestCoupon() {

        OkHttpClient httpClient = new OkHttpClient();

        HttpUrl.Builder httpBuilder =
                HttpUrl.parse(couponsServiceUrl).newBuilder();

        Request request = new Request.Builder().
                url(httpBuilder.build()).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Log.e(TAG, "failed to get coupons");
            }
            @Override public void onResponse(Call call, Response response){
                ResponseBody responseBody = response.body();
                String resp = "";
                if (!response.isSuccessful()) {
                    Log.e(TAG, "failed response");
                }else {
                    try {
                        resp = responseBody.string();
                        saveOffer(resp);
                    } catch (IOException e) {
                        Log.e(TAG, "failed to read response " + e);
                    }
                }
            }
        });
    }

    //save latest coupon to room db on the device
    private void saveOffer(String resp){
        OfferDAO offerDAO = LocalRepository.getOfferDatabase(context).offerDAO();
        offerDAO.deleteOffers();

        offerDAO.insertLatestOffer(prepareDataObj(resp));
    }

    private Offer prepareDataObj(String resp){
        String[] offer = resp.trim().split("\\|");
        return new Offer(offer[0], offer[1]);
    }
}

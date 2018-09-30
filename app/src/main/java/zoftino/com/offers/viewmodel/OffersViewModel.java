package zoftino.com.offers.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import zoftino.com.offers.data.LocalRepository;
import zoftino.com.offers.data.Offer;
import zoftino.com.offers.data.OfferDAO;
import zoftino.com.offers.workmanager.RefreshScheduler;

public class OffersViewModel extends ViewModel {
    public LiveData<Offer> offer;
    private OfferDAO offerDAO;

    public OffersViewModel(Context ctx){
        offerDAO = LocalRepository.getOfferDatabase(ctx).offerDAO();

        //start one time task using work manager
        RefreshScheduler.refreshCouponOneTimeWork((LifecycleOwner)ctx);
    }

    public LiveData<Offer> getLatestCoupon(){
        if(offer == null){
            offer = offerDAO.getLatestOffer();
        }
        return offer;
    }

    public void setupPeriodicCpnRefreshWork(){
        RefreshScheduler.refreshCouponPeriodicWork();
    }
    public static class OffersViewModelFactory extends
            ViewModelProvider.NewInstanceFactory {
        private Context context;
        public OffersViewModelFactory(Context ctx) {
            context = ctx;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> viewModel) {
            return (T) new OffersViewModel(context);
        }
    }
}

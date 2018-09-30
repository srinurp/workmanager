package zoftino.com.offers.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface OfferDAO {
    @Query("SELECT * FROM Offer")
    public LiveData<Offer> getLatestOffer();

    @Insert
    public void insertLatestOffer(Offer offer);

    @Query("DELETE FROM Offer")
    public void deleteOffers();
}

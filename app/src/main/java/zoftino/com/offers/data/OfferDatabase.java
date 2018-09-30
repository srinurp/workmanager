package zoftino.com.offers.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
@Database(entities = {Offer.class}, version = 1)
public abstract class OfferDatabase extends RoomDatabase {
    public abstract OfferDAO offerDAO();
}


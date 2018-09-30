package zoftino.com.offers.data;

import android.arch.persistence.room.Room;
import android.content.Context;

public class LocalRepository {
    private static OfferDatabase offerDB;
    private static final Object LOCK = new Object();
    private static Context ctx;

    public synchronized static OfferDatabase getOfferDatabase(Context context) {
        if (offerDB == null) {
            ctx = context;
            synchronized (LOCK) {
                if (offerDB == null) {
                    offerDB = Room.databaseBuilder(context,
                            OfferDatabase.class, "Offer Database").build();
                }
            }
        }
        return offerDB;
    }
}

package zoftino.com.offers.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Offer {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private String store;
    private String offer;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Offer(String store, String offer){
        this.store = store;
        this.offer = offer;
    }
    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}

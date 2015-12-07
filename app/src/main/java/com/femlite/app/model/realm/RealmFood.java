package com.femlite.app.model.realm;

import com.femlite.app.model.Food;
import com.femlite.app.model.Portion;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class RealmFood extends RealmObject implements Food {

    @PrimaryKey
    private String id;

    private String title;

    private RealmList<RealmPortion> realmPortions;

    @Ignore
    private List<Portion> portions;

    public RealmFood() {
    }

    public RealmFood(String id, String title, RealmList<RealmPortion> portions) {
        this.id = id;
        this.title = title;
        this.realmPortions = portions;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<Portion> getPortions() {
        if (portions == null && getRealmPortions() != null) {
            portions = new ArrayList<>();
            for (RealmPortion realmPortion : getRealmPortions()) {
                portions.add(realmPortion);
            }
        }
        return portions;
    }

    public RealmList<RealmPortion> getRealmPortions() {
        return realmPortions;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRealmPortions(RealmList<RealmPortion> realmPortions) {
        this.realmPortions = realmPortions;
    }

    public void setPortions(List<Portion> portions) {
        this.portions = portions;
    }
}

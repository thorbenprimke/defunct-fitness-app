package com.femlite.app.model.realm;

import com.femlite.app.model.Portion;
import com.femlite.app.model.fddb.Serving;

import io.realm.RealmObject;

/**
 * Created by thorben2 on 12/6/15.
 */
public class RealmPortion extends RealmObject implements Portion {

    private String title;

    private int calories;

    private double servingSize;

    public RealmPortion() {
    }

    public RealmPortion(Serving serving, int baseAmount, int baseCalorie) {
        title = serving.getName();
        servingSize = Double.parseDouble(serving.getWeight_gram());
        calories = (int) (((double) servingSize / (double) baseAmount) * baseCalorie);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getCalories() {
        return calories;
    }

    @Override
    public double getServingSize() {
        return servingSize;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }
}

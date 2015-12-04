package com.femlite.app;

import android.os.Bundle;
import android.util.Log;

import com.femlite.app.model.fddb.FddbResponse;
import com.femlite.app.network.FbbdService;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FoodTrackerMainActivity extends FemliteDrawerActivity {

    @Inject
    FbbdService fbbdService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getComponent().inject(this);
    }

    @Override
    public int getContentLayoutResId() {
        return R.layout.content_calorie_tracker_overview;
    }

    @OnClick(R.id.food_tracker_button)
    public void handleFootTrackerButtonClicked() {
//        startActivity(new Intent(this, FoodTrackerAddFoodActivity.class));

        Observable<FddbResponse> observable = fbbdService.search("GXQN0ME0NFN7K0DBBU1LHH9X", "de", "banane");
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        foodItem -> {
                            foodItem.toString();
                            Log.d("network", "success");
                        },
                        throwable -> {
                            Log.d("network", "fail: " + throwable.toString());
                        },
                        () -> {

                        });

    }
}

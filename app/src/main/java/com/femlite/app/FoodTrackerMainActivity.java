package com.femlite.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.femlite.app.model.fddb.FddbResponse;
import com.femlite.app.network.FbbdService;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

//        Observable<FddbResponse> observable = fbbdService.search("GXQN0ME0NFN7K0DBBU1LHH9X", "de", "banane");
//        observable
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(
//                        foodItem -> {
//                            foodItem.toString();
//                            Log.d("network", "success");
//                        },
//                        throwable -> {
//                            Log.d("network", "fail: " + throwable.toString());
//                        },
//                        () -> {
//
//                        });

        Map<String, Date> params = new HashMap<>();
        params.put("date", new Date(new Date().getTime() - (1 * 24 * 60 * 60 * 1000)));

        ParseCloud.callFunctionInBackground(
                "consumedFoodByDate",
                params,
                new FunctionCallback<Object>() {
                    @Override
                    public void done(Object object, ParseException e) {
                        Toast.makeText(getApplicationContext(), "callback: " + ((HashMap) object).get("total"), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}

package com.femlite.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.femlite.app.dialog.PortionPicker;
import com.femlite.app.model.Food;
import com.femlite.app.model.Portion;
import com.femlite.app.model.fddb.FddbResponse;
import com.femlite.app.model.fddb.Item;
import com.femlite.app.model.fddb.Serving;
import com.femlite.app.model.fddb.Servings;
import com.femlite.app.model.realm.RealmFood;
import com.femlite.app.model.realm.RealmPortion;
import com.femlite.app.network.FbbdService;
import com.femlite.app.views.FoodItemView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchView;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import io.realm.Realm;
import io.realm.RealmList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FoodTrackerAddFoodActivity extends FemliteBaseActivity {

    @Inject
    FbbdService fbbdService;

    @Bind(R.id.search_view)
    RealmSearchView realmSearchView;

    private Realm realm;
    private FoodRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker_add_food);
        ButterKnife.bind(this);
        getComponent().inject(this);

        realm = Realm.getDefaultInstance();

        adapter = new FoodRecyclerViewAdapter(this, realm, "title");
        realmSearchView.setAdapter(adapter);
    }

    private void fetchMore() {
        Observable<FddbResponse> observable = fbbdService.search(
                "GXQN0ME0NFN7K0DBBU1LHH9X",
                "de",
                realmSearchView.getSearchBarText());
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        results -> {
                            List<Item> items = results.getItems().getItem();
                            realm.beginTransaction();
                            for (Item item : items) {
                                int amount = Integer.parseInt(item.getData().getAmount());
                                int kcal = (int) Double.parseDouble(item.getData().getKcal());

                                Servings servings = item.getServings();
                                RealmList<RealmPortion> portions = new RealmList<RealmPortion>();
                                for (Serving serving : servings.getServing()) {
                                    portions.add(new RealmPortion(serving, amount, kcal));
                                }

                                RealmFood food = new RealmFood(item.getId(), item.getDescription().getName(), portions);
                                realm.copyToRealmOrUpdate(food);
                            }
                            realm.commitTransaction();
                            adapter.notifyDataSetChanged();

                            results.toString();
                            Log.d("network", "success");
                        },
                        throwable -> {
                            Log.d("network", "fail: " + throwable.toString());
                        },
                        () -> {

                        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    public class FoodRecyclerViewAdapter
            extends RealmSearchAdapter<RealmFood, FoodRecyclerViewAdapter.ViewHolder> {

        public FoodRecyclerViewAdapter(
                Context context,
                Realm realm,
                String filterColumnName) {
            super(context, realm, filterColumnName);
        }

        public class ViewHolder extends RealmSearchViewHolder {

            private FoodItemView foodItemView;

            public ViewHolder(FoodItemView foodItemView) {
                super(foodItemView);
                this.foodItemView = foodItemView;
            }

            public ViewHolder(FrameLayout container, TextView footerTextView) {
                super(container, footerTextView);
            }
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            ViewHolder vh = new ViewHolder(new FoodItemView(viewGroup.getContext()));
            return vh;
        }

        @Override
        public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
            final Food food = realmResults.get(position);
            viewHolder.foodItemView.bind(food);

            viewHolder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            PortionPicker dialog = new PortionPicker();
                            dialog.setClickListener(
                                    new PortionPicker.PortionSelectedListener() {
                                        @Override
                                        public void onPortionSelected(Portion portion) {
                                            // Add to parse and then close the screen

                                            ParseObject userWorkoutRelation = new ParseObject("ConsumedFood");
                                            userWorkoutRelation.put("title", portion.getTitle());
                                            userWorkoutRelation.put("calories", portion.getCalories());
                                            userWorkoutRelation.put("consumedOn", new Date(System.currentTimeMillis()));

                                            userWorkoutRelation.saveInBackground(
                                                    new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            Toast.makeText(getApplicationContext(), "Added to tracker", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                            );


                                        }
                                    }
                            );
                            dialog.setPortions(food.getPortions());
                            dialog.show(getSupportFragmentManager(), "PortionPicker");
                        }
                    }
            );
        }

        @Override
        public ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup) {
            View v = inflater.inflate(co.moonmonkeylabs.realmsearchview.R.layout.footer_view, viewGroup, false);
            ViewHolder vh = new ViewHolder(
                    (FrameLayout) v,
                    (TextView) v.findViewById(co.moonmonkeylabs.realmsearchview.R.id.footer_text_view));
            return vh;
        }
        @Override
        public void onBindFooterViewHolder(ViewHolder holder, int position) {
            super.onBindFooterViewHolder(holder, position);
            holder.footerTextView.setText("Search for items...");
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fetchMore();
                        }
                    }
            );
        }
   }
}

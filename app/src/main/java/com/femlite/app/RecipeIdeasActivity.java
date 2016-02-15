package com.femlite.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.femlite.app.manager.DataManager;
import com.femlite.app.manager.UiStorageHelper;
import com.femlite.app.model.Recipe;
import com.femlite.app.model.Workout;
import com.femlite.app.model.realm.RealmWorkout;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import rx.functions.Action1;

/**
 * Activity for the recipes list
 */
public class RecipeIdeasActivity extends FemliteDrawerActivity {

    @Bind(R.id.recipe_ideas_list)
    RealmRecyclerView realmRecyclerView;

    @Inject
    UiStorageHelper uiStorageHelper;

    @Inject
    DataManager dataManager;

    private Adapter adapter;

    @Override
    public int getContentLayoutResId() {
        return R.layout.recipe_ideas_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        getComponent().inject(this);

        uiStorageHelper.onCreate();

        // Get the results.

        adapter = new Adapter();
        realmRecyclerView.setAdapter(adapter);

        dataManager.getRecipes(
                new Action1<List<Recipe>>() {
                    @Override
                    public void call(List<Recipe> recipes) {
                        if (recipes == null) {

                        } else {
                            adapter.setItems(recipes);
                        }

                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiStorageHelper.onDestroy();
    }

    public class Adapter extends RealmBasedRecyclerViewAdapter<RealmWorkout, Adapter.ViewHolder> {

        private List<Recipe> recipes;

        public class ViewHolder extends RealmViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public Adapter() {
            super(RecipeIdeasActivity.this, null, false, false);
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(new TextView(viewGroup.getContext()));
        }

        @Override
        public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
            TextView itemView = (TextView) viewHolder.itemView;
            itemView.setText(recipes.get(i).getTitle());
        }

        public void setItems(List<Recipe> recipes) {
            this.recipes = recipes;
            notifyDataSetChanged();
        }
    }
}

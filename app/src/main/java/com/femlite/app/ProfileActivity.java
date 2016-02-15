package com.femlite.app;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.tapglue.Tapglue;
import com.tapglue.model.TGEvent;
import com.tapglue.model.TGFeed;
import com.tapglue.networking.requests.TGRequestCallback;
import com.tapglue.networking.requests.TGRequestErrorType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tester on 1/2/16.
 */
public class ProfileActivity extends FemliteDrawerActivity {

    @Bind(R.id.profile_event_list)
    RecyclerView eventList;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        eventList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter();
        eventList.setAdapter(adapter);

        Tapglue.feed().retrieveFeedForCurrentUser(
                new TGRequestCallback<TGFeed>() {
                    @Override
                    public boolean callbackIsEnabled() {
                        return true;
                    }

                    @Override
                    public void onRequestError(TGRequestErrorType cause) {

                    }

                    @Override
                    public void onRequestFinished(TGFeed output, boolean changeDoneOnline) {
                        adapter.setEventList(output.getEvents());
                    }
                }
        );
    }

    @Override
    public int getContentLayoutResId() {
        return R.layout.profile_layout;
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        private List<TGEvent> eventList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String visiblity = eventList.get(position).getVisibility().toString();

            String metadata = "nothing";
            JsonElement metadataJson = eventList.get(position).getMetadata();
            if (metadataJson != null) {
                if (metadataJson instanceof JsonPrimitive) {
                    JsonPrimitive jsonPrimitive = (JsonPrimitive) metadataJson;
                    metadata = jsonPrimitive.getAsString();
                } else if (metadataJson instanceof JsonObject) {
                    JsonObject jsonObject = (JsonObject) metadataJson;
                    if (jsonObject.has("category")) {
                        metadata = jsonObject.get("category").getAsString();
                    }
                }

            }

//            JsonElement jsonElement = new JsonPrimitive("some string");
            ((TextView) holder.itemView).setText(eventList.get(position).getType() + " " + visiblity + " " + metadata);
        }

        @Override
        public int getItemCount() {
            return eventList == null ? 0 : eventList.size();
        }

        public void setEventList(List<TGEvent> eventList) {
            this.eventList = eventList;
            notifyDataSetChanged();
        }
    }
}

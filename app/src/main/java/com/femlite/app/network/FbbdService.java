package com.femlite.app.network;

import com.femlite.app.model.fddb.FddbResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Service interface for Fbbd nutrition database
 */
public interface FbbdService {

    @GET("search/item.xml")
    Observable<FddbResponse> search(
            @Query("apikey") String apiKey,
            @Query("lang") String language,
            @Query("q") String query
    );
}

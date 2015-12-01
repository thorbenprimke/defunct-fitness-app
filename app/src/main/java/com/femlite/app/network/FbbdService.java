package com.femlite.app.network;

import retrofit.http.GET;

/**
 * Service interface for Fbbd nutrition database
 */
public interface FbbdService {

    @GET("/api/v14/search/item.xml")
    void search();
}

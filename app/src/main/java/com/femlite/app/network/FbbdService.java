package com.femlite.app.network;

import retrofit.http.GET;

/**
 * Created by thorben on 11/28/15.
 */
public interface FbbdService {

    @GET("/api/v14/search/item.xml")
    String
}

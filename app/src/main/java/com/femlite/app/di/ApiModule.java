package com.femlite.app.di;

import com.femlite.app.network.FbbdService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * The ApiModule for debug builds which includes the mocked {@link FbbdService} for
 * instrumentation tests.
 */
@Module(includes = ApiBaseModule.class)
public class ApiModule {

    @Provides
    @Singleton
    FbbdService provideFbbdApiService(
            @Named("FbbdRetrofit") Retrofit retrofit/*,
            MockCommerceApiService mockCommerceApiService*/) {
//        if (ApiBaseModule.isInstrumentation()) {
//            return MockRestAdapter
//                    .from(retrofit)
//                    .create(CommerceApiService.class, mockCommerceApiService);
//        }
        return retrofit.create(FbbdService.class);
    }
}
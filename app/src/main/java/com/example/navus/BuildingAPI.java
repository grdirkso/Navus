package com.example.navus;

import androidx.core.content.res.FontResourcesParserCompat;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BuildingAPI {

    @POST("./")
    @FormUrlEncoded
    Call<List<ClassRoom>> buildingDirections(@Field("building") String building,
                                             @Field("room") int room);

}

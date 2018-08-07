package serio.tim.android.com.recipepicker;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import serio.tim.android.com.recipepicker.model.MainPojo;

public interface EdamameClient {
    @GET("/search")
    Call<MainPojo> recipesForSearch(@QueryMap Map<String, Object> map);
}

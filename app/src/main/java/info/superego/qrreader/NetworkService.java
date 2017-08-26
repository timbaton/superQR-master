package info.superego.qrreader;

import info.superego.qrreader.models.Model;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

;

/**
 * Created by Timur on 16.04.2017.
 */
public class NetworkService {

    private static final String baseUrl = "https://super-ego.info";

    public interface qrApi {
        @GET("/api/check-ticket")
        Call<Model> getData(@Query("key") String keyValue, @Query("hash") String hashValue);
    }

    public qrApi getAPI() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(qrApi.class);
    }
}

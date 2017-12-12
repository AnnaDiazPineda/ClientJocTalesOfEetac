package dsa.eetac.upc.edu.clientjoc.inputOutput;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by anita on 03/12/2017.
 */

public class ApiAdapter {
    private static ApiService API_SERVICE;

    public static ApiService getApiService() {



        if (API_SERVICE == null) {

            // Creamos un interceptor y le indicamos el log level a usar
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Asociamos el interceptor a las peticiones
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            String baseUrl = "http://10.0.2.2:8080/myapp/json/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(ApiService.class);
        }

        return API_SERVICE;
    }
}

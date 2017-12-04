package dsa.eetac.upc.edu.clientjoc.inputOutput;

import dsa.eetac.upc.edu.clientjoc.ClassesClon.Jugador;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by anita on 03/12/2017.
 */

public interface ApiService {


    @GET("Login")
    Call<Jugador> getLogin(
            @Query("mail") String mail,
            @Query("password") String password
    );
}

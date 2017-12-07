package dsa.eetac.upc.edu.clientjoc.inputOutput;

import dsa.eetac.upc.edu.clientjoc.ClassesClon.Jugador;
import dsa.eetac.upc.edu.clientjoc.inputOutput.Response.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by anita on 03/12/2017.
 */

public interface ApiService {


    @POST("Jugador/{email}")
    Call<Jugador> getLogin(@Path("email") String user, @Body Login loginBody);


    @POST("newJugador/{email}")
    Call<Jugador> getNouJugador(@Path("email")String user, @Body Login loginBody);


}

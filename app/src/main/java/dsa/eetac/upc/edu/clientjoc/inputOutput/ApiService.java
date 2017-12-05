package dsa.eetac.upc.edu.clientjoc.inputOutput;

import dsa.eetac.upc.edu.clientjoc.ClassesClon.Jugador;
import dsa.eetac.upc.edu.clientjoc.inputOutput.Response.LoginBody;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by anita on 03/12/2017.
 */

public interface ApiService {


    @POST("Jugador/{email}")
    Call<Jugador> getLogin(@Path("email") String user, @Body String loginBody);


}

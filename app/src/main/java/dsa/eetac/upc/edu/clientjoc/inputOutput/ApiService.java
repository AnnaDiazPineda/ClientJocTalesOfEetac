package dsa.eetac.upc.edu.clientjoc.inputOutput;

import dsa.eetac.upc.edu.clientjoc.ClassesClon.Jugador;
import dsa.eetac.upc.edu.clientjoc.inputOutput.Response.LoginBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by anita on 03/12/2017.
 */

public interface ApiService {


    @POST("Login")
    Call<Jugador> getLogin(@Body LoginBody loginBody);


}

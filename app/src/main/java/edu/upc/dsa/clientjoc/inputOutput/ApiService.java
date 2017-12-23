package edu.upc.dsa.clientjoc.inputOutput;

import java.util.ArrayList;
import java.util.Collection;

import edu.upc.dsa.beans.Answer;
import edu.upc.dsa.beans.Jugador;

import edu.upc.dsa.clientjoc.inputOutput.Response.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by anita on 03/12/2017.
 */

public interface ApiService {

    //calls relacionats amb la gestió d'usuaris
    @POST("Jugador/{email}")
    Call<Jugador> getLogin(@Path("email") String user, @Body Login loginBody);
    @POST("update/{email}")
    Call<Answer> updateJugador(@Path("email") String user, @Body Login loginBody);//enviem email, nova contrassenya
    @POST("newJugador/{email}")
    Call<Jugador> getNouJugador(@Path("email")String user, @Body Login loginBody);
    @POST("delete/{email}")
    Call<Answer> deleteJugador(@Path("email") String user, @Body Login loginBody);



    @GET("Mapa")
    Call<String> getMapa();





}

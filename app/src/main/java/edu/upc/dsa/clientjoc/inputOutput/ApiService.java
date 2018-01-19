package edu.upc.dsa.clientjoc.inputOutput;

import java.util.ArrayList;

import edu.upc.dsa.DAOG.DAOMapa;
import edu.upc.dsa.beans.Answer;
import edu.upc.dsa.beans.Jugador;

import edu.upc.dsa.beans.Login;
import edu.upc.dsa.beans.Objeto;
import edu.upc.dsa.beans.Partida;
import edu.upc.dsa.beans.Personatge;

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
    @POST("/newPartida/")
    Call<Integer> addNewPartida(@Body Partida nueva);//1 correctamente creada
    @GET ("Ranking")
    Call<ArrayList<Partida>> getRanking();//llistat 5 partides ranking
    @GET("Mapa/{idJugador}")
    Call<String> getMapa(@Path("idJugador")int id);

    @POST("NewMapa/{idJugador}")
    Call<String> newMapa(@Path("idJugador")int id, @Body Personatge personatge);
    @POST("addObject/{idpersonatge}")
    Call<String> addNewObject(@Path("idpersonatge")int id, @Body Objeto myobject);
    @POST("Jugador/{nomPersonatge}/{tipus}/{idjugador}")
    Call<Personatge> newPersonaje(@Path("nomPersonatge") String nomPersonatge, @Path("tipus") String type, @Path("idjugador") String id);
    @POST("NewMapa/{idJugador}")
    Call<String>getNewMapa(Personatge personatgeEnviatPelClient, @Path("idJugador")int idJugador);
    @POST("saveMapa/{idJugador}")
    Call<Integer>saveMapa(@Body  DAOMapa mapa, @Path("idJugador")int idJugador);

}

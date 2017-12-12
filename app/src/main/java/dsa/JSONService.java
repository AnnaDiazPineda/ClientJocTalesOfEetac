package dsa;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/json")//porta
public class JSONService {

    //iiPara los metodos de añadir jugador/personaje/objeto se podrian quitar los parametros y llamar al constructor?
    //Añadir comprobaciones y comprobar comportamiento en caso de error/que no exista algo

    Mundo miMundo;

    public JSONService() {
        miMundo = Mundo.getIntanceMundo();



    }

    //************REST APUNTANT BASE DE DADES****************

    //servei d'autentificació login funcionant
    @POST
    @Path("/Jugador/{email}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Jugador Login(@PathParam("email") String email,Login login){
        System.out.println(login.getPassword());
        Jugador j = new Jugador();

        try{
            j.select("email",email);
            j.toString();
        }
        catch(Exception e)
        {
            return null;
        }
        if(j.getContrasenya().equals(login.getPassword()))
        {
            return j;
        }
        return null;
    }

    //nou usuari
    @POST
    @Path("/newJugador/{email}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Jugador newJugador(@PathParam("email") String email,Login login){
        System.out.println("registrem a "+login.getNom());
        Jugador j = new Jugador(login.getNom(),login.getPassword(),email);
        try {
            j.insert();
        } catch (Exception e) {
            return null;
        }
        return j;
    }

    //elimina usuari
    @POST
    @Path("/delete/{email}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Answer deleteJugador(@PathParam("email") String email, Login login){
        System.out.println("registrem a "+login.getNom());
        Jugador j = new Jugador(login.getNom(),login.getPassword(),email);
        Answer mianswer = new Answer();
        if(j.delete()==true){
            mianswer.setResposta("OK");
            return mianswer;}
            mianswer.setResposta("KO");
        return mianswer;

    }

    //actualitzar contrassenya o nom
    @POST
    @Path("/update/{email}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Answer updateJugador(@PathParam("email") String email, Login login){
        System.out.println(email);

        Jugador j = new Jugador(login.getNom(),login.getPassword(),email);
        Answer mianswer = new Answer();
        mianswer.setResposta("KO");
        try {
            if(j.update() == true)
            {
                mianswer.setResposta("OK");
                return mianswer;
            }

            else{
                return mianswer;}
        } catch (Exception e) {
            e.printStackTrace();
            return mianswer;

        }

    }



    //********************************************************


    //Creacio personatgeeeee
    @POST
    @Path("/Jugador/{nomPersonatge}/{idjugador}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public void newPersonaje(@PathParam("nomPersonatge") String nomPersonatge,@PathParam("idjugador") String id){
        System.out.println(nomPersonatge);

        //GestorDePErsonatges.addPersonatge(nomPersonatge, id);
        Personatge p = new Personatge();
        p.setNombre(nomPersonatge);
        try {
            p.insert();
            RelacioPersonatgeJugador r = new RelacioPersonatgeJugador(Integer.parseInt(id),p.getId());
            r.insert();


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }




    //******************************************************************
    @GET
    @Path("/Jugador/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Jugador buscarJugador(@PathParam("email") String email) {
        return miMundo.getMapaJugadores().get(email.hashCode());
    }

    @GET
    @Path("/Personaje/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Personatge buscarPersonaje(@PathParam("nombre") String nombre) {
        return miMundo.getMapaPersonajes().get(nombre);
    }

    @GET
    @Path("/objetosPersonaje/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Objeto> objetosPersonaje(@PathParam("nombre") String nombre) {
        return miMundo.getMapaPersonajes().get(nombre).getArrMisObjetos();
    }

    @GET
    @Path("/Objeto/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Objeto buscarObjeto(@PathParam("nombre") String nombre) {
        return miMundo.getMapaObjetos().get(nombre);
    }

    //Expecificar como se quiere añadir un jugador y con que campos, de momento le pasas el email y le asigna el email y la id sin mas
    @POST
    @Path("/nuevoJugador/{jugadorEmail}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoJugador(@PathParam("jugadorEmail") String email) {
        Jugador jugador = new Jugador();
        jugador.setEmail(email);
        jugador.setId(email.hashCode());
        miMundo.getMapaJugadores().put(jugador.getId(), jugador);
        return Response.status(201).entity("Añadido jugador con correo:" +email).build();
    }

    @POST
    @Path("/eliminarJugador/{emailJugador}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarJugador(@PathParam("emailJugador") String email) {
        miMundo.getMapaJugadores().remove(email.hashCode());
        return Response.status(201).entity("Eliminado jugador con correo:" +email).build();
    }


    @POST
    @Path("/nuevoPersonaje/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoPersonaje(@PathParam("personajeNombre") String nombre) {
        Personatge personaje = new Personatge();
        personaje.setNombre(nombre);
        miMundo.getMapaPersonajes().put(nombre, personaje);
        return Response.status(201).entity("Añadido personaje con nombre:" +nombre).build();
    }
    /*
    @POST
    @Path("/nuevoPersonaje/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoPersonaje(Personatge personaje, @PathParam("personajeNombre") String nombre) {
        personaje.setNombre(nombre);
        miMundo.getMapaPersonajes().put(nombre, personaje);
        return Response.status(201).entity("Añadido personaje con nombre:" +nombre).build();
    }
    /*/

    @POST
    @Path("/eliminarPersonaje/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarPersonaje(@PathParam("personajeNombre") String nombre) {
        miMundo.getMapaPersonajes().remove(nombre);
        return Response.status(201).entity("Eliminado personaje con nombre:" +nombre).build();
    }

    @POST
    @Path("/nuevoObjeto/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoObjeto(@PathParam("objetoNombre") String nombre) {
        Objeto objeto = new Objeto();
        objeto.setNombre(nombre);
        miMundo.getMapaObjetos().put(nombre, objeto);
        return Response.status(201).entity("Añadido objeto con nombre:" +nombre).build();
    }

    /*@POST
    @Path("/nuevoObjeto/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoObjeto(Objeto objeto, @PathParam("objetoNombre") String nombre) {
        objeto.setNombre(nombre);
        miMundo.getMapaObjetos().put(nombre, objeto);
        return Response.status(201).entity("Añadido objeto con nombre:" +nombre).build();
    }*/

    @POST
    @Path("/eliminarObjeto/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarObjeto(@PathParam("objetoNombre") String nombre) {
        miMundo.getMapaObjetos().remove(nombre);
        return Response.status(201).entity("Eliminado objeto con nombre:" +nombre).build();
    }

    @POST
    @Path("/nuevoObjetoPersonaje/{personajeNombre}/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoObjetoPersonaje(@PathParam("personajeNombre") String nombrePersonaje,
                                         @PathParam("objetoNombre") String nombreObjeto) {

        //Añadir comprobaciones de errores y tal
        miMundo.getMapaPersonajes().get(nombrePersonaje).getArrMisObjetos().add(miMundo.getMapaObjetos().get(nombreObjeto));

        return Response.status(201).entity("Añadido objeto con nombre:" +nombreObjeto +"a personaje" +nombrePersonaje).build();
    }

    @POST
    @Path("/transferirObjeto/{personajeNombre1}/{personajeNombre2}/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transferirObjeto(@PathParam("personajeNombre1") String nombrePersonaje,
                                     @PathParam("personajeNombre2") String nombrePersonaje2,
                                     @PathParam("objetoNombre") String nombreObjeto) {
        //Comprobacion o dara nullpointer probablemente
        miMundo.getMapaPersonajes().get(nombrePersonaje2).getArrMisObjetos().add(
                miMundo.buscarObjeto(miMundo.mapaPersonajes.get(nombrePersonaje).arrMisObjetos, nombreObjeto));
        miMundo.getMapaPersonajes().get(nombrePersonaje).getArrMisObjetos().remove(
                miMundo.buscarObjeto(miMundo.mapaPersonajes.get(nombrePersonaje).arrMisObjetos, nombreObjeto));

        return Response.status(201).entity("Transferido el objeto" +nombreObjeto +"a personaje" +nombrePersonaje).build();
    }


    //Ya existente
    @POST
    @Path("/nuevoPersonajeJugador/{jugadorEmail}/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoPersonajeJugador(@PathParam("jugadorEmail") String jugadorEmail,
                                          @PathParam("personajeNombre") String personajeNombre) {

        //Añadir comprobaciones de errores y tal
        miMundo.getMapaJugadores().get(jugadorEmail.hashCode()).getPersonatges().add(miMundo.getMapaPersonajes().get(personajeNombre));

        return Response.status(201).entity("Añadido personaje con nombre:" +personajeNombre +"a jugador con email" +jugadorEmail).build();
    }

    @POST
    @Path("/perderResistencia/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void perderResistencia(@PathParam("personajeNombre") String personajeNombre){
        miMundo.getMapaPersonajes().get(personajeNombre).setResistencia(miMundo.getMapaPersonajes().get(personajeNombre).getResistencia()-1);
    }



    @GET
    @Path("/Personaje/{nombrePersonaje}/{idJugador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Personatge buscarPersonaje(@PathParam("nombrePersonaje") String nombrePersonaje, @PathParam("idJugador") int id) {
        return new Personatge("Hamlet",1,1,1,1);
    }

    @GET
    @Path("/Mapa")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMapa() throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return mapper.writeValueAsString( miMundo.mapa); //  mirate esta linea MARTA por que he cambiado el singletone
    }

    /*
    public List<Partida> partidas () {
        List<Partida> partides = new ArrayList<Partida>();
        partides.add(new Partida("1", "PArtida 1", 7777));
        partides.add(new Partida("2", "PArtida 2", 3));
        partides.add(new Partida("3", "PArtida 3",3333));
    }*/

}





package edu.upc.dsa.clientjoc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Random;

import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.beans.Objeto;
import edu.upc.dsa.beans.Partida;
import edu.upc.dsa.beans.Personatge;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;

public class Juego extends AppCompatActivity {

    private int mainPosition;
    private int direccion;
    ArrayList<Integer> mapa = new ArrayList<>();
    ArrayList<Button> mapaBotones = new ArrayList<>();
    Button button;

    Jugador jugadorTest;
    Personatge personatgeTest;
    Partida partida;
    ApiService mRestAdapter;

    private static final int FIL = 10;
    public static final int COL = 10;

    Context context;
    Toast toast;
    int duration = Toast.LENGTH_SHORT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_botones);

        partida = new Partida();
        context = getApplicationContext();

        iniciarlizarMapa();
        inicializarMapaBotones();
        generateRandomFloor();

        mRestAdapter =  ApiAdapter.getApiService();

        Intent intent = getIntent();
        String value = intent.getStringExtra("jugador");

        ObjectMapper mapper = new ObjectMapper();
        try {
            jugadorTest = mapper.readValue(value, Jugador.class);
            if (jugadorTest != null)
            {
                Toast.makeText(this, "Se ha cargado correctamente el jugador", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Error en el cargado del jugador", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        personatgeTest = new Personatge("croxas", 1, 10, 10, 10, 0);
        Objeto objetoTest = new Objeto("Espada", "arma", "espada de madera", 1, 1, "https://vignette.wikia.nocookie.net/clubpenguin/images/3/3b/Espada_de_Madera_icono.png/revision/latest?cb=20141121011021&path-prefix=es");
        personatgeTest.getArrMisObjetos().add(objetoTest);
    }

    public void iniciarlizarMapa(){

        // 0 casilla despejada
        // -1 casilla con muro
        // 1 casilla con nuestro personaje
        // 2 casilla con slime
        // 3 casilla con item
        // 5 casilla con boss
        // 6 casilla con mago
        // 7 casilla con fuente

        //Inicializamos el mapa a vacio
        for (int x = 0; x<FIL; x++){
            for(int y = 0; y<COL; y++){
                if (x == 0 || x == 9 || y == 0 || y == 9){
                    //Ponemos muro en los bordes
                    mapa.add(-1);
                }else{
                    //Rellenamos a vacio el resto
                    mapa.add(0);
                }
            }
        }
    }

    public int posicionInteriorRandom(){
        Random r = new Random();
        int random = -1;
        do {
            int x = r.nextInt(8 - 1) + 1;
            int y = r.nextInt(8 - 1) + 1;
            random = x*10+y;
        }while (mapa.get(random)!=0);
        return random;
    }

    public int posicionBordeRandom(){
        Random r = new Random();
        int x,y;
        int al = r.nextInt(3-0);
        int random = 0;
        switch (al){
            case 0:
                x = 0;
                y = r.nextInt(8 - 1) + 1;
                random = x*10+y;
                break;
            case 1:
                x = 9;
                y = r.nextInt(8 - 1) + 1;
                random = x*10+y;
                break;
            case 2:
                x = r.nextInt(8 - 1) + 1;
                y = 0;
                random = x*10+y;
                break;
            case 3:
                x = r.nextInt(8 - 1) + 1;
                y = 9;
                random = x*10+y;
                break;
        }
        return random;
    }

    public void resetSala(){
        //Inicializamos el mapa a vacio
        for (int x = 0; x<FIL; x++){
            for(int y = 0; y<COL; y++){
                if (x == 0 || x == 9 || y == 0 || y == 9){
                    //Ponemos muro en los bordes
                    mapa.set(x*10+y,-1);
                    mapaBotones.get(x*10+y).setBackgroundResource(R.drawable.piedra);
                }else{
                    //Rellenamos a vacio el resto
                    mapa.set(x*10+y,0);
                    mapaBotones.get(x*10+y).setBackgroundResource(R.drawable.tierra);
                }
            }
        }
    }

    public void colocarTierra(int posicion){
        mapa.set(posicion,0);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.tierra);
    }

    public void colocarHeroe(int posicion){
        if(mainPosition != 0) mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.tierra);
        mapa.set(posicion,1);
        mainPosition = posicion;
        mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.hero);
    }

    public void colocarBoss(int posicion){
        mapa.set(posicion,5);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.boss_troll);
    }

    public void colocarPuerta(int posicion){
        mapa.set(posicion,10);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.door);
    }

    public void colocarFuente(int posicion){
        mapa.set(posicion,7);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.fountain);
    }

    public void colocarMago(int posicion){
        mapa.set(posicion,6);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.mage);
    }

    public void colocarRoca(int posicion){
        mapa.set(posicion,-1);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.piedra);
    }

    public void colocarCaja(int posicion){
        mapa.set(posicion,8);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.box);
    }

    public void colocarMoneda(int posicion){
        mapa.set(posicion,11);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.coin);
    }

    public void colocarSlime(int posicion){
        mapa.set(posicion,2);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.slime);
    }

    public void colocarCalavera(int posicion){
        mapa.set(posicion,-2);
        mapaBotones.get(posicion).setBackgroundResource(R.drawable.skull);
    }



    public void generateRandomFloor(){
        resetSala();
        colocarPuerta(posicionBordeRandom());
        colocarHeroe(posicionInteriorRandom());

        Random r = new Random();
        int numeroItems = r.nextInt(5-2)+2;
        for(int i = 0; i<numeroItems; i++){
            switch (r.nextInt(7-0)){
                case 0:
                    //if (partida.getPuntos() > 10)
                    colocarBoss(posicionInteriorRandom());
                    break;
                case 1:
                    colocarCaja(posicionInteriorRandom());
                    colocarCaja(posicionInteriorRandom());
                    colocarCaja(posicionInteriorRandom());
                    colocarCaja(posicionInteriorRandom());
                    break;
                case 2:
                    colocarFuente(posicionInteriorRandom());
                    break;
                case 3:
                    colocarRoca(posicionInteriorRandom());
                    break;
                case 4:
                    colocarSlime(posicionInteriorRandom());
                    break;
            }
        }
    }

    public void moverArriba(View view) {
        //Movemos el personaje en el mapa
        if (mapa.get(mainPosition-10) == 0){
            mapa.set(mainPosition,0);
            mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.tierra);
            mainPosition=mainPosition-10;
            mapa.set(mainPosition,1);
            mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.hero);
            setDireccion(-10);
        }else{
            setDireccion(-10);
        }
    }

    public void moverAbajo(View view) {
        //Movemos el personaje en el mapa
        if (mapa.get(mainPosition+10) == 0){
            mapa.set(mainPosition,0);
            mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.tierra);
            mainPosition=mainPosition+10;
            mapa.set(mainPosition,1);
            mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.hero);
            setDireccion(10);
        }else{
            setDireccion(10);
        }
    }

    public void moverIzquierda(View view) {
        //Movemos el personaje en el mapa
        if (mapa.get(mainPosition-1) == 0){
            mapa.set(mainPosition,0);
            mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.tierra);
            mainPosition=mainPosition-1;
            mapa.set(mainPosition,1);
            mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.hero);
            setDireccion(-1);
        }else{
            setDireccion(-1);
        }
    }

    public void moverDerecha(View view) {
        //Movemos el personaje en el mapa
        if (mapa.get(mainPosition+1) == 0){
            mapa.set(mainPosition,0);
            mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.tierra);
            mainPosition=mainPosition+1;
            mapa.set(mainPosition,1);
            mapaBotones.get(mainPosition).setBackgroundResource(R.drawable.hero);
            setDireccion(1);
        }else{
            setDireccion(1);
        }

    }

    public void levelUp(Personatge slime){
        if (muerto() == false){
            personatgeTest.setExperiencia(personatgeTest.getExperiencia() + slime.getExperiencia());
            if (personatgeTest.getExperiencia() >= personatgeTest.getNivel()){
                while(personatgeTest.getExperiencia() >= personatgeTest.getNivel()){
                    personatgeTest.setExperiencia(0 + personatgeTest.getExperiencia()- personatgeTest.getNivel());
                    personatgeTest.setNivel(personatgeTest.getNivel() + 1);
                }
            }
        }
    }

    public void luchar(int tipoEnemigo){
        switch (tipoEnemigo){
            case 0://slime
                //bucle infinito
                //Poner valores maximos en el personaje
                //Subir valores y curar cuando se sube de nivel

                Personatge slime = new Personatge("slime", 1,1,1,1,1);
                while(personatgeTest.getResistencia() > 0 && slime.getResistencia() > 0){
                    int dañoSlime = slime.getAtaque() - personatgeTest.getDefensa();
                    if (dañoSlime < 0) dañoSlime = 0;
                    int dañoPersonaje = personatgeTest.getAtaque() - slime.getDefensa();
                    if (dañoPersonaje < 0) dañoPersonaje = 0;

                    slime.setResistencia(slime.getResistencia() - dañoPersonaje);
                    personatgeTest.setResistencia(personatgeTest.getResistencia() - dañoSlime);
                }
                levelUp(slime);
                break;
            case 1://boss
                Personatge boss = new Personatge("slime", 100,100,100,100,100);
                while(personatgeTest.getResistencia() > 0 && boss.getResistencia() > 0){
                    int dañoBoss = boss.getAtaque() - personatgeTest.getDefensa();
                    if (dañoBoss < 0) dañoBoss = 0;
                    int dañoPersonaje = personatgeTest.getAtaque() - boss.getDefensa();
                    if (dañoPersonaje < 0) dañoPersonaje = 0;

                    boss.setResistencia(boss.getResistencia() - dañoPersonaje);
                    personatgeTest.setResistencia(personatgeTest.getResistencia() - dañoBoss);
                }
                levelUp(boss);
                break;
        }
    }

    public boolean muerto(){
        if (personatgeTest.getResistencia() <= 0){
            return true;
        }else{
            return false;
        }

    }

    public void restaurarHp(){
        personatgeTest.setResistencia(personatgeTest.getNivel());
    }

    public void setDireccion(int direccion){
        this.direccion = direccion;
    }

    public void interactuar(View view){
        // 0 casilla despejada
        // -1 casilla con muro
        // 1 casilla con nuestro personaje
        // 2 casilla con slime
        // 3 casilla con item
        // 4 roca rompible
        // 5 boss
        // 6 casilla con mago
        // 7 casilla con fuente
        // 8 casilla con caja
        // 9 casilla con mercader
        // 10 casilla con puerta
        // 11 casilla con moneda
        switch (mapa.get(mainPosition+direccion)){
            case -1://bloque
                toast = Toast.makeText(context, "Parece que esta roca es demasiado dura para romperla", duration);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
                break;
            case 2://slime
                CharSequence optionsSlime[] = new CharSequence[] {"Atacar", "Huir"};

                AlertDialog.Builder slimeDialog = new AlertDialog.Builder(this);
                slimeDialog.setTitle("El slime te mira con una gran sonrisa");
                slimeDialog.setItems(optionsSlime, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                luchar(0);
                                if (muerto() == false){
                                    colocarTierra(mainPosition+direccion);

                                    toast = Toast.makeText(context, "Easy!", duration);
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    toast.show();
                                }else{
                                    colocarCalavera(mainPosition);
                                    toast = Toast.makeText(context, "Has muerto!!", duration);
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    toast.show();
                                }

                                break;
                            case 1:
                                toast = Toast.makeText(context, "Pobre slime, en el fondo no ha hecho nada", duration);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                break;
                        }
                    }
                });
                slimeDialog.show();
                break;
            case 3://item
                break;
            case 4://roca rompible
                break;
            case 5://boss
                CharSequence optionsBoss[] = new CharSequence[] {"Luchar", "Huir"};

                AlertDialog.Builder bossDialog = new AlertDialog.Builder(this);
                bossDialog.setTitle("El boss te mira fijamente");
                bossDialog.setItems(optionsBoss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                luchar(1);
                                if (muerto() == false){
                                    colocarTierra(mainPosition+direccion);

                                    toast = Toast.makeText(context, "Easy!", duration);
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    toast.show();
                                }else{
                                    colocarCalavera(mainPosition);
                                    toast = Toast.makeText(context, "Has muerto!!", duration);
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    toast.show();
                                }
                                break;
                            case 1:
                                toast = Toast.makeText(context, "Buena decision, parecia realmente fuerte", duration);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                break;
                        }
                    }
                });
                bossDialog.show();
                break;

            case 7:
                CharSequence optionsFountain[] = new CharSequence[] {"Beber", "Mejor no beber por si acaso"};

                AlertDialog.Builder fountainDialog = new AlertDialog.Builder(this);
                fountainDialog.setTitle("Parece un agua bastante limpia y clara...");
                fountainDialog.setItems(optionsFountain, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                toast = Toast.makeText(context, "Te sientes mucho mejor despues de beber de la fuente!", duration);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                break;
                            case 1:
                                toast = Toast.makeText(context, "Mejor seguir con sed que estirar la pata...", duration);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                break;
                        }
                    }
                });
                fountainDialog.show();
                break;

            case 8://caja
                CharSequence optionsBox[] = new CharSequence[] {"Romper caja", "Mejor no tocarla por si acaso"};

                AlertDialog.Builder boxDialog = new AlertDialog.Builder(this);
                boxDialog.setTitle("Una caja un tanto sospechosa...");
                boxDialog.setItems(optionsBox, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Random r = new Random();
                                int random = r.nextInt(5-0);
                                switch (random){
                                    case 0:
                                        colocarMoneda(mainPosition+direccion);
                                        break;
                                    case 1:
                                        colocarBoss(mainPosition+direccion);
                                        break;
                                    case 2:
                                        colocarTierra(mainPosition+direccion);
                                        toast = Toast.makeText(context, "Al parecer no habia nada dentro...", duration);
                                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        toast.show();
                                        break;
                                    case 3:
                                        colocarTierra(mainPosition+direccion);
                                        toast = Toast.makeText(context, "Al parecer no habia nada dentro...", duration);
                                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        toast.show();
                                        break;
                                    case 4:
                                        colocarTierra(mainPosition+direccion);
                                        toast = Toast.makeText(context, "Al parecer no habia nada dentro...", duration);
                                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        toast.show();
                                        break;
                                }
                                break;
                            case 1:
                                toast = Toast.makeText(context, "A saber que habia dentro...", duration);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                break;
                        }
                    }
                });
                boxDialog.show();
                break;
            case 10://puerta
                CharSequence optionsDoor[] = new CharSequence[] {"Abrir. Alla vamos!", "Aun no estoy listo, mejor mas tarde"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Si la abrimos NO habra vuelta atras! Que debemos hacer?");
                builder.setItems(optionsDoor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                generateRandomFloor();

                                toast = Toast.makeText(context, "Parece que nos ha transportado a una nueva sala!", duration);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                break;
                            case 1:
                                toast = Toast.makeText(context, "Mejor ir poco a poco, nada de que avergonzarse", duration);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                break;
                        }
                    }
                });
                builder.show();
                break;
            case 11://monedas de oro
                Random r = new Random();
                int random = r.nextInt(10-1)+1;

                colocarTierra(mainPosition+direccion);
                toast = Toast.makeText(context, "Has recogido "+random+" monedas de oro!", duration);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
                break;

        }
    }

    public void inicializarMapaBotones(){

        button = (Button) findViewById(R.id.boton00); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton01); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton02); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton03); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton04); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton05); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton06); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton07); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton08); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton09); mapaBotones.add(button);

        button = (Button) findViewById(R.id.boton10); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton11); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton12); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton13); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton14); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton15); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton16); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton17); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton18); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton19); mapaBotones.add(button);

        button = (Button) findViewById(R.id.boton20); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton21); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton22); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton23); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton24); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton25); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton26); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton27); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton28); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton29); mapaBotones.add(button);

        button = (Button) findViewById(R.id.boton30); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton31); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton32); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton33); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton34); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton35); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton36); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton37); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton38); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton39); mapaBotones.add(button);

        button = (Button) findViewById(R.id.boton40); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton41); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton42); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton43); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton44); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton45); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton46); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton47); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton48); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton49); mapaBotones.add(button);

        button = (Button) findViewById(R.id.boton50); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton51); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton52); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton53); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton54); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton55); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton56); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton57); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton58); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton59); mapaBotones.add(button);

        button = (Button) findViewById(R.id.boton60); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton61); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton62); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton63); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton64); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton65); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton66); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton67); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton68); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton69); mapaBotones.add(button);

        button = (Button) findViewById(R.id.boton70); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton71); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton72); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton73); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton74); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton75); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton76); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton77); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton78); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton79); mapaBotones.add(button);

        button = (Button) findViewById(R.id.boton80); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton81); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton82); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton83); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton84); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton85); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton86); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton87); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton88); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton89); mapaBotones.add(button);

        button = (Button) findViewById(R.id.boton90); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton91); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton92); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton93); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton94); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton95); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton96); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton97); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton98); mapaBotones.add(button);
        button = (Button) findViewById(R.id.boton99); mapaBotones.add(button);

    }

    /*public void openBag(View view) {
        Intent intent = new Intent(getApplicationContext(), Bag.class);
        Bundle args = new Bundle();
        args.putSerializable("bag", personatgeTest.getArrMisObjetos());
        intent.putExtra("bundle", args);
        startActivity(intent);
    }

    public void openStats(View view) {
        Intent intent = new Intent(getApplicationContext(), Stats.class);
        Bundle args = new Bundle();
        args.putSerializable("stats", personatgeTest);
        intent.putExtra("bundle", args);
        startActivity(intent);
    }*/
}


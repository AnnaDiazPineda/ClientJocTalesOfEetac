package edu.upc.dsa.beans;

public class Answer {
    String resposta;
    public Answer(){}
    Answer(String respuesta){
        this.resposta= respuesta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}

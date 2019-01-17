package com.testmind.al.testmind;

public class Pregunta {
    private int id;
    private String enunciado;
    private String categoria;
    private String preguntaCorrecta;
    private String preguntaIncorrecta1;
    private String preguntaIncorrecta2;
    private String preguntaIncorrecta3;
    private String imagen;

    public Pregunta(String enunciado,String categoria,String preguntaCorrecta,String preguntaIncorrecta1,String preguntaIncorrecta2,String preguntaIncorrecta3, String imagen) {
       // this.id=id;
        this.enunciado=enunciado;
        this.categoria = categoria;
        this.preguntaCorrecta=preguntaCorrecta;
        this.preguntaIncorrecta1=preguntaIncorrecta1;
        this.preguntaIncorrecta2=preguntaIncorrecta2;
        this.preguntaIncorrecta3=preguntaIncorrecta3;
        this.imagen=imagen;
    }
    public Pregunta(int id,String enunciado,String categoria,String preguntaCorrecta,String preguntaIncorrecta1,String preguntaIncorrecta2,String preguntaIncorrecta3, String imagen) {
        this.id=id;
        this.enunciado=enunciado;
        this.categoria=categoria;
        this.preguntaCorrecta=preguntaCorrecta;
        this.preguntaIncorrecta1=preguntaIncorrecta1;
        this.preguntaIncorrecta2=preguntaIncorrecta2;
        this.preguntaIncorrecta3=preguntaIncorrecta3;
        this.imagen=imagen;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPreguntaCorrecta() {
        return preguntaCorrecta;
    }

    public void setPreguntaCorrecta(String preguntaCorrecta) {
        this.preguntaCorrecta = preguntaCorrecta;
    }

    public String getPreguntaIncorrecta1() {
        return preguntaIncorrecta1;
    }

    public void setPreguntaIncorrecta1(String preguntaIncorrecta1) {
        this.preguntaIncorrecta1 = preguntaIncorrecta1;
    }

    public String getPreguntaIncorrecta2() {
        return preguntaIncorrecta2;
    }

    public void setPreguntaIncorrecta2(String preguntaIncorrecta2) {
        this.preguntaIncorrecta2 = preguntaIncorrecta2;
    }

    public String getPreguntaIncorrecta3() {
        return preguntaIncorrecta3;
    }

    public void setPreguntaIncorrecta3(String preguntaIncorrecta3) {
        this.preguntaIncorrecta3 = preguntaIncorrecta3;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

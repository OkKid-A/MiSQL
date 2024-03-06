package edu.cunoc.Query;

public class Condicion {

    private String columna;
    private String tipo;
    private Object valor;

    public Condicion(String columna, String tipo, Object valor) {
        this.columna = columna;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}

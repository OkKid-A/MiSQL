package edu.cunoc.Query;

import java.util.ArrayList;
import java.util.Objects;

public class Logico {

    private ArrayList<Condicion> condiciones;
    private boolean single;
    private boolean and;

    public Logico(ArrayList<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    public Logico() {
    }

    public Boolean comparar(Object valor, Condicion condicion) throws ClassCastException{
        String tipo = condicion.getTipo();
        switch (tipo){
            case "<>":
                if (valor instanceof String){
                    return !valor.equals(condicion.getValor());
                } else {
                    return !Objects.equals(valor, condicion.getValor());
                }
            case "<=":
                return parseFloat(valor) <= parseFloat(condicion.getValor());
            case ">=":
                return parseFloat(valor) >= parseFloat(condicion.getValor());
            case ">":
                return parseFloat(valor) > parseFloat(condicion.getValor());
            case "<":
                return parseFloat(valor) < parseFloat(condicion.getValor());
            case "=":
                return Objects.equals(valor, condicion.getValor());
            default:
                return false;
        }
    }

    public boolean validateFila(int[] columnasComp, Object[] fila)throws ClassCastException{
        boolean valida = true;
        for (int i = 0; i < columnasComp.length; i++) {
            if (comparar(fila[columnasComp[i]],condiciones.get(i))){
                valida = true;
            } else {
                return false;
            }
        }
        return valida;
    }

    public Float parseFloat(Object o){
        if (o instanceof String){
            return Float.parseFloat((String) o);
        } else if (o instanceof Integer){
            return Float.valueOf((Integer) o);
        } else {
            return (Float) o;
        }
    }
    public void add(Condicion condicion){
        if (condiciones==null){
            condiciones =new ArrayList<Condicion>();
            condiciones.add(condicion);
            single = true;
        } else {
            condiciones.add(condicion);
            single = false;
        }

    }

    public ArrayList<Condicion> getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(ArrayList<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean isAnd() {
        return and;
    }

    public void setAnd(boolean and) {
        this.and = and;
    }
}

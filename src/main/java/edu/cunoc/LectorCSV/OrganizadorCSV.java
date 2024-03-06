package edu.cunoc.LectorCSV;

import edu.cunoc.Query.Condicion;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class OrganizadorCSV {

    private ArrayList<String> headers;
    private ArrayList<Object[]> filas;
    private int numColumnas;


    public boolean validateColumnas(ArrayList<String> columnas) throws FileNotFoundException {
        Boolean valid = false;
        for (int i = 1; i < columnas.size(); i++) {
            for (int j = 0; j < headers.size(); j++) {
                if (columnas.get(i).equals(headers.get(j))){
                    valid = true;
                    break;
                }
            }
            if (!valid){
                throw new FileNotFoundException();
            }
        }
        return valid;
    }

    public boolean validateCondiciones(ArrayList<Condicion> condiciones) throws FileNotFoundException {
        Boolean valid = false;
        for (int i = 0; i < condiciones.size(); i++) {
            for (int j = 0; j < headers.size(); j++) {
                if (condiciones.get(i).getColumna().equals(headers.get(j))){
                    valid = true;
                    break;
                }
            }
            if (!valid){
                throw new FileNotFoundException();
            }
        }
        return valid;
    }

    public boolean validateAsignaciones(ArrayList<Condicion> asignaciones) throws FileNotFoundException{
        Boolean valid = false;
        for (int i = 0; i < asignaciones.size(); i++) {
            for (int j = 0; j < headers.size(); j++) {
                if (asignaciones.get(i).getColumna().equals(headers.get(j))){
                    valid = true;
                    break;
                }
            }
            if (!valid){
                throw new FileNotFoundException();
            }
        }
        return valid;
    }

    public void validateValores(ArrayList<Object> valores) throws ArrayIndexOutOfBoundsException{
        if (valores.size()>numColumnas){
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int[] getNumColSelec(ArrayList<String> buscadas){
        int[] colSelec = new int[buscadas.size()];
        for (int i = 0; i < buscadas.size(); i++) {
            for (int j = 0; j < numColumnas; j++) {
                if (buscadas.get(i).equals(headers.get(j))){
                    colSelec[i] = j;
                }
            }
        }
        return colSelec;
    }

    public int[] getNumColCondic(ArrayList<Condicion> condicions){
        int[] colSelec = new int[condicions.size()];
        for (int i = 0; i < condicions.size(); i++) {
            ArrayList<String> headersFixed = headers;
            Collections.reverse(headersFixed);
            for (int j = 0; j < numColumnas; j++) {
                if (condicions.get(i).getColumna().equals(headersFixed.get(j))){
                    colSelec[i] = j;
                }
            }
        }
        return colSelec;
    }

    public String[] createFilaValida(int[] columnVal, Object[] fila){
        String[] filaForm = new String[columnVal.length];
        for (int i = 0; i < columnVal.length; i++) {
            filaForm[i] = String.valueOf(fila[columnVal[i]]);
        }
        return filaForm;
    }

    public String[] createFilaValida(Object[] fila){
        String[] filaForm = new String[fila.length];
        for (int i = 0; i < fila.length; i++) {
            filaForm[i] = String.valueOf(fila[i]);
        }
        return filaForm;
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
        this.numColumnas = headers.size();
    }

    public ArrayList<Object[]> getFilas() {
        return filas;
    }

    public void setFilas(ArrayList<Object[]> filas) {
        this.filas = filas;
    }

    public int getNumColumnas() {
        return numColumnas;
    }

    public void setNumColumnas(int numColumnas) {
        this.numColumnas = numColumnas;
    }
}

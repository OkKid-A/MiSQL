package edu.cunoc.Archivador;

import java.util.ArrayList;

public class Carpeta extends Location{

    private ArrayList<Archivo> archivos;
    private ArrayList<Carpeta> carpetas;

    public Carpeta(String name) {
        super(name);
        this.archivos = new ArrayList<>();
        this.carpetas = new ArrayList<>();
    }

    public Carpeta() {
        this.archivos = new ArrayList<>();
        this.carpetas = new ArrayList<>();
    }

    public void addArchivo(Archivo archivo){
        archivos.add(archivo);
    }

    public void addCarpeta(Carpeta carpeta){
        carpetas.add(carpeta);
    }

    public ArrayList<Archivo> getArchivos() {
        return archivos;
    }

    public void add(Location location){
        if (location instanceof Carpeta){
            addCarpeta((Carpeta) location);
        } else if (location instanceof Archivo){
            addArchivo((Archivo) location);
        }
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    public ArrayList<Carpeta> getCarpetas() {
        return carpetas;
    }

    public void setCarpetas(ArrayList<Carpeta> carpetas) {
        this.carpetas = carpetas;
    }

    @Override
    public String toString() {
        return getName();
    }
}

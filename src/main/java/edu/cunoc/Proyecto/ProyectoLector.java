package edu.cunoc.Proyecto;

import edu.cunoc.Archivador.Archivo;
import edu.cunoc.Archivador.Carpeta;
import edu.cunoc.Archivador.Location;
import org.apache.commons.csv.CSVParser;

import java.util.ArrayList;

public class ProyectoLector {

    private ArrayList<Carpeta> carpetas;
    private ArrayList<Archivo> archivos;
    private String path;
    private String nombre;

    public ArrayList<Carpeta> getCarpetas() {
        return carpetas;
    }

    public void add(Location location){
        if (location instanceof Carpeta){
            addCarpeta((Carpeta) location);
        } else if (location instanceof Archivo){
            addArchivo((Archivo) location);
        }
    }

    public void addCarpeta(Carpeta carpeta){
        if (carpetas!=null){
            this.carpetas.add(carpeta);
        } else {
            this.carpetas = new ArrayList<>();
            this.carpetas.add(carpeta);
        }
    }

    public void setCarpetas(ArrayList<Carpeta> carpetas) {
        this.carpetas = carpetas;
    }

    public ArrayList<Archivo> getArchivos() {
        return archivos;
    }

    public void addArchivo(Archivo archivo){
        if (archivos!=null){
            this.archivos.add(archivo);
        } else {
            this.archivos = new ArrayList<>();
            this.archivos.add(archivo);
        }
    }

    public void setArchivos(ArrayList<Archivo> archivos) {
        this.archivos = archivos;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

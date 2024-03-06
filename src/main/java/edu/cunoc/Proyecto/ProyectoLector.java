package edu.cunoc.Proyecto;

import edu.cunoc.Archivador.Archivo;
import edu.cunoc.Archivador.Carpeta;
import edu.cunoc.Archivador.Location;
import org.apache.commons.csv.CSVParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ProyectoLector {

    private ArrayList<Carpeta> carpetas;
    private ArrayList<Archivo> archivos;
    private String path;
    private String nombre;

    public Archivo buscarArchivobyPath(ArrayList<String> path) throws FileNotFoundException {
        ArrayList<Archivo> archivosTemp = archivos;
        ArrayList<Carpeta> carpetastemp = carpetas;
        String nombrePaht;
        boolean validNombre = path.get(0).equals(nombre);
        if (validNombre) {
            for (int i = 1; i < path.size() - 1; i++) {
                nombrePaht = path.get(i);
                for (int j = 0; j < carpetastemp.size(); j++) {
                    if (carpetas.get(j).getName().equals(nombrePaht)) {
                        Carpeta actual = carpetas.get(j);
                        carpetastemp = actual.getCarpetas();
                        archivosTemp = actual.getArchivos();
                        break;
                    } else {
                        throw new FileNotFoundException();
                    }
                }
            }
            nombrePaht = path.get(path.size() - 1);
            for (int i = 0; i < archivosTemp.size(); i++) {
                if (archivosTemp.get(i).getName().equals(nombrePaht)) {
                    return archivosTemp.get(i);
                }
            }
            throw new FileNotFoundException();
        } else {
            System.out.println(nombre);
            throw new FileNotFoundException();
        }
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

    public void addArchivo(Archivo archivo){
        if (archivos!=null){
            this.archivos.add(archivo);
        } else {
            this.archivos = new ArrayList<>();
            this.archivos.add(archivo);
        }
    }

    public ArrayList<Carpeta> getCarpetas() {
        return carpetas;
    }

    public void setCarpetas(ArrayList<Carpeta> carpetas) {
        this.carpetas = carpetas;
    }

    public ArrayList<Archivo> getArchivos() {
        return archivos;
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

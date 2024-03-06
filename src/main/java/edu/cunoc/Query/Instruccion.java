package edu.cunoc.Query;

import edu.cunoc.Archivador.Archivo;
import edu.cunoc.LectorCSV.OrganizadorCSV;
import edu.cunoc.Proyecto.ProyectoLector;
import edu.cunoc.UI.VentanaPrincipal;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Instruccion {

    protected ArrayList<String> path;

    public Instruccion(ArrayList<String> path) {
        Collections.reverse(path);
        this.path = path;
    }


    public boolean isValid(ProyectoLector proyectoLector) {
        return false;
    }

    public void Ejecutar(VentanaPrincipal ventanaPrincipal, ProyectoLector proyectoLector) {}


    public ArrayList<String> getPath() {
        return path;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }
}

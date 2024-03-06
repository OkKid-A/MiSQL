package edu.cunoc.Instructor;

import edu.cunoc.Proyecto.ProyectoLector;
import edu.cunoc.Query.Instruccion;
import edu.cunoc.UI.VentanaPrincipal;

import java.util.ArrayList;

public class InstructorSQL {

    private ArrayList<Instruccion> queries;
    private ProyectoLector proyectoLector;
    private ArrayList<String> errores;

    public InstructorSQL(ProyectoLector proyectoLector) {
        this.proyectoLector = proyectoLector;
        this.errores = new ArrayList<>();
    }

    public void addInstruccion(Instruccion instruccion){
        if (queries!=null){
            this.queries.add(instruccion);
        } else {
            this.queries = new ArrayList<>();
            this.queries.add(instruccion);
        }
    }

    public String getErrorAsString(){
        if (errores!=null){
            return String.join("\n",errores);
        } else {
            return "";
        }
    }

    public void addError(String error){
        errores.add(error);
    }
    public ArrayList<Instruccion> getQueries() {
        return queries;
    }

    public void setQueries(ArrayList<Instruccion> queries) {
        this.queries = queries;
    }

    public void procQueries(VentanaPrincipal ventanaPrincipal,ProyectoLector proyectoLector) {
        for (Instruccion query : queries) {
            query.Ejecutar(ventanaPrincipal,proyectoLector);
        }
    }
}

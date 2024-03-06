package edu.cunoc.Query;

import edu.cunoc.Archivador.Archivo;
import edu.cunoc.LectorCSV.CSVLexer;
import edu.cunoc.LectorCSV.CSVParser;
import edu.cunoc.LectorCSV.OrganizadorCSV;
import edu.cunoc.Proyecto.ProyectoLector;
import edu.cunoc.UI.VentanaPrincipal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Insert extends Instruccion{

    private ArrayList<String> columnas;
    private ArrayList<Object> valores;

    public Insert(ArrayList<String> path, ArrayList<String> columnas, ArrayList<Object> valores) {
        super(path);
        this.columnas = columnas;
        this.valores = valores;
    }

    @Override
    public void Ejecutar(VentanaPrincipal ventanaPrincipal, ProyectoLector proyectoLector) {

    }

    @Override
    public boolean isValid(ProyectoLector proyectoLector) {
        try {
            Archivo encontrado = proyectoLector.buscarArchivobyPath(path);
            CSVLexer lexer = new CSVLexer(new StringReader(Files.readString(Path.of(encontrado.getPath()))));
            CSVParser parser = new CSVParser(lexer);
            OrganizadorCSV organizadorCSV = parser.getorganizadorCSV();
            if (columnas!=null) {
                organizadorCSV.validateColumnas(columnas);
            }
            organizadorCSV.validateValores(valores);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

package edu.cunoc.Query;

import edu.cunoc.Archivador.Archivo;
import edu.cunoc.LectorCSV.CSVLexer;
import edu.cunoc.LectorCSV.CSVParser;
import edu.cunoc.LectorCSV.OrganizadorCSV;
import edu.cunoc.Proyecto.ProyectoLector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Delete extends Instruccion{

    private Logico logico;

    public Delete(ArrayList<String> path, Logico logico) {
        super(path);
        this.logico = logico;
    }

    @Override
    public boolean isValid(ProyectoLector proyectoLector) {
        try {
            Archivo encontrado = proyectoLector.buscarArchivobyPath(path);
            CSVLexer lexer = new CSVLexer(new StringReader(Files.readString(Path.of(encontrado.getPath()))));
            CSVParser parser = new CSVParser(lexer);
            OrganizadorCSV organizadorCSV = parser.getorganizadorCSV();
            if (logico.getCondiciones()!=null) {
                organizadorCSV.validateCondiciones(logico.getCondiciones());
            }
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

package edu.cunoc.Archivador;

import edu.cunoc.Proyecto.ProyectoLector;
import edu.cunoc.Proyecto.ProyectoLectorLexer;
import edu.cunoc.Proyecto.ProyectoLectorParser;
import edu.cunoc.Proyecto.sym;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Proyecto {



    public Proyecto() {
    }

    public ProyectoLector actualizarProyecto(Path ruta) throws Exception {
        String contenido = Files.readString(ruta);
        StringReader reader = new StringReader(contenido);
        ProyectoLectorLexer lexer  =  new ProyectoLectorLexer(reader);
        ProyectoLectorParser parser = new ProyectoLectorParser(lexer);
        parser.parse();
        return parser.getProyectoLector();
    }
}

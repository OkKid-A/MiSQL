package edu.cunoc.Query;

import edu.cunoc.Archivador.Archivo;
import edu.cunoc.LectorCSV.CSVLexer;
import edu.cunoc.LectorCSV.CSVParser;
import edu.cunoc.LectorCSV.OrganizadorCSV;
import edu.cunoc.Proyecto.ProyectoLector;
import edu.cunoc.UI.VentanaPrincipal;
import edu.cunoc.UI.VentanaTabla;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Select extends Instruccion{

    private ArrayList<String> columnas;
    private  Logico logico;

    public Select(ArrayList<String> path, ArrayList<String> columnas, Logico logico) {
        super(path);
        this.columnas = columnas;
        this.logico = logico;
    }

    @Override
    public void Ejecutar(VentanaPrincipal ventanaPrincipal, ProyectoLector proyectoLector) {
        try {
            Archivo encontrado = proyectoLector.buscarArchivobyPath(path);
            CSVLexer lexer = new CSVLexer(new StringReader(Files.readString(Path.of(encontrado.getPath()))));
            CSVParser parser = new CSVParser(lexer);
            parser.parse();
            OrganizadorCSV organizadorCSV = parser.getorganizadorCSV();
            ArrayList<String[]> filasFound  = new ArrayList<>();
            String[] headers;
            int[] colCondicion = organizadorCSV.getNumColCondic(logico.getCondiciones());
            if (columnas!=null) {
                int[] colSelec = organizadorCSV.getNumColSelec(columnas);
                for (int i = 0; i < organizadorCSV.getFilas().size(); i++) {
                    Object[] fila = organizadorCSV.getFilas().get(i);
                    if (logico.validateFila(colCondicion,fila)){
                        filasFound.add(organizadorCSV.createFilaValida(colSelec,fila));
                    }
                }
                headers = organizadorCSV.createFilaValida(colSelec,
                        organizadorCSV.getHeaders().toArray(new String[organizadorCSV.getHeaders().size()]));
            } else {
                for (int i = 0; i < organizadorCSV.getFilas().size(); i++) {
                    Object[] fila = organizadorCSV.getFilas().get(i);
                    if (logico.validateFila(colCondicion,fila)){
                        filasFound.add(organizadorCSV.createFilaValida(fila));
                    }
                }
                headers = organizadorCSV.createFilaValida(
                        organizadorCSV.getHeaders().toArray(new String[organizadorCSV.getHeaders().size()]));
            }
            String[][] datos  = filasFound.toArray(new String[filasFound.size()][]);
            VentanaTabla ventanaTabla = new VentanaTabla(headers,datos);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(ventanaPrincipal,"No se encontro el archivo con la ruta descrita.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ventanaPrincipal,"Se encontro el archivo a seleccionar pero no se pudo abrir.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValid(ProyectoLector proyectoLector) {
        try {
            Archivo encontrado = proyectoLector.buscarArchivobyPath(path);
            CSVLexer lexer = new CSVLexer(new StringReader(Files.readString(Path.of(encontrado.getPath()))));
            CSVParser parser = new CSVParser(lexer);
            parser.parse();
            OrganizadorCSV organizadorCSV = parser.getorganizadorCSV();
            if (columnas!=null) {
                organizadorCSV.validateColumnas(columnas);
            }
            organizadorCSV.validateCondiciones(logico.getCondiciones());
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getColumnas() {
        return columnas;
    }

    public void setColumnas(ArrayList<String> columnas) {
        this.columnas = columnas;
    }


}

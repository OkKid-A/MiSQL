package edu.cunoc.Query;

import edu.cunoc.Archivador.Archivo;
import edu.cunoc.LectorCSV.CSVLexer;
import edu.cunoc.LectorCSV.CSVParser;
import edu.cunoc.LectorCSV.OrganizadorCSV;
import edu.cunoc.Proyecto.EscritorProyecto;
import edu.cunoc.Proyecto.ProyectoLector;
import edu.cunoc.UI.VentanaPrincipal;
import edu.cunoc.UI.VentanaTabla;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class Update extends Instruccion{

    private final ArrayList<Condicion> asignaciones;
    private final Logico condiciones;

    public Update(ArrayList<String> path, ArrayList<Condicion> asignaciones, Logico condiciones) {
        super(path);
        this.asignaciones = asignaciones;
        this.condiciones = condiciones;
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
            String[] headers = new String[0];
            ArrayList<String> lineasValidas = new ArrayList<>();
            int contador = 0;
            if (condiciones!=null) {
                int[] colCondicion = organizadorCSV.getNumColCondic(condiciones.getCondiciones());
                int[] colSelec = organizadorCSV.getNumColCondic(asignaciones);
                for (int i = 0; i < organizadorCSV.getFilas().size(); i++) {
                    Object[] fila = organizadorCSV.getFilas().get(i);
                    if (condiciones.validateFila(colCondicion,fila)){
                        for (int j = 0; j < colSelec.length; j++) {
                            fila[colSelec[i]] = asignaciones.get(i).getValor();
                            i++;
                        }
                        filasFound.add(organizadorCSV.createFilaValida(fila));
                    } else {
                        filasFound.add(organizadorCSV.createFilaValida(fila));
                    }
                }
                headers = organizadorCSV.createFilaValida(
                        organizadorCSV.getHeaders().toArray(new String[organizadorCSV.getHeaders().size()]));
            } else {
                int[] colSelec = organizadorCSV.getNumColCondic(asignaciones);
                for (int i = 0; i < organizadorCSV.getFilas().size(); i++) {
                    Object[] fila = organizadorCSV.getFilas().get(i);
                    for (int j = 0; j < colSelec.length; j++) {
                        fila[colSelec[i]] = asignaciones.get(i).getValor();
                        i++;
                    }
                    filasFound.add(organizadorCSV.createFilaValida(fila));
                }
                ArrayList<String> temp = organizadorCSV.getHeaders();
                Collections.reverse(temp);
                headers = organizadorCSV.createFilaValida(
                        temp.toArray(new String[temp.size()]));
            }
            String[][] datos  = filasFound.toArray(new String[filasFound.size()][]);
            updateCSVFile(headers,datos,encontrado);
            JOptionPane.showMessageDialog(ventanaPrincipal,"Actualizacion completada para el archivo "+encontrado.getName()+
            ". Se actualizaron " + contador+" lineas.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ventanaPrincipal,"No se pudo actualizar el archivo.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCSVFile(String [] headers, String[][]datos, Archivo archivo) throws IOException {
        FileWriter fileWriter = new FileWriter(archivo.getPath());
        fileWriter.write(String.join(",",headers)+System.lineSeparator());
        for (int i = 0; i < datos.length; i++) {
            fileWriter.write(String.join(",",datos[i])+System.lineSeparator());
        }
        fileWriter.close();
    }

    @Override
    public boolean isValid(ProyectoLector proyectoLector) {
        try {
            Archivo encontrado = proyectoLector.buscarArchivobyPath(path);
            CSVLexer lexer = new CSVLexer(new StringReader(Files.readString(Path.of(encontrado.getPath()))));
            CSVParser parser = new CSVParser(lexer);
            parser.parse();
            OrganizadorCSV organizadorCSV = parser.getorganizadorCSV();
            if (condiciones!=null) {
                organizadorCSV.validateCondiciones(condiciones.getCondiciones());
            }
            organizadorCSV.validateAsignaciones(asignaciones);
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
}

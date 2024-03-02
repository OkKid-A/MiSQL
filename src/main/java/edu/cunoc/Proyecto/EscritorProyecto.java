package edu.cunoc.Proyecto;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class EscritorProyecto {

    private FileWriter fileWriter;
    private String path;
    private String nombre;
    private File proyectoFile;
    private ProyectoLector proyectoLector;

    public EscritorProyecto(ProyectoLector proyectoLector){
        this.proyectoLector = proyectoLector;
        this.path = proyectoLector.getPath();
        this.nombre = proyectoLector.getNombre();
        this.proyectoFile = new File(path);
    }

    public void crearProyecto() throws IOException {
        new File(path+File.separator+nombre).mkdir();
        fileWriter = new FileWriter(path+File.separator+nombre+File.separator+nombre+".ide");
        System.out.println(proyectoFile.getAbsolutePath());
        fileWriter.write("<PROYECTO nombre=\""+nombre+"\">\n" +
                "</PROYECTO>");
        fileWriter.close();
    }

    public void crearCarpeta(TreeNode[] treePath, String name) throws IOException {
        ArrayList<String> lineasValidas = new ArrayList<>();
        String linea;
        int posLinea = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(proyectoFile))) {
            while ((linea = br.readLine()) != null) {
                lineasValidas.add(linea);
            }
        }
        posLinea = getPosLinea(treePath, lineasValidas, posLinea);
        lineasValidas.add(posLinea+1, "<CARPETA nombre=\"" + name + "\">");
        lineasValidas.add(posLinea+2,"</CARPETA>");
        fileWriter = new FileWriter(proyectoFile.getAbsolutePath());
        for (String string : lineasValidas){
            fileWriter.write(string+System.lineSeparator());
        }

        fileWriter.close();
    }

    public void crearArchivo(TreeNode[] treePath, String name, String ubicacion) throws IOException {
        ArrayList<String> lineasValidas = new ArrayList<>();
        String linea;
        int posLinea = 0;
        File nuevo = new File(ubicacion);
        if (!nuevo.createNewFile()){
            throw new FileAlreadyExistsException(name+" ya existe");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(proyectoFile))) {
            while ((linea = br.readLine()) != null) {
                lineasValidas.add(linea);
            }
        }
        posLinea = getPosLinea(treePath, lineasValidas, posLinea);
        lineasValidas.add(posLinea+1, "<ARCHIVO nombre=\"" + name + "\" ubicacion=\""+ubicacion+"\"/>");
        fileWriter = new FileWriter(proyectoFile.getAbsolutePath());
        for (String string : lineasValidas){
            fileWriter.write(string+System.lineSeparator());
        }

        fileWriter.close();
    }

    private int getPosLinea(TreeNode[] treePath, ArrayList<String> lineasValidas, int posLinea) throws FileNotFoundException {
        if (treePath.length>1) {
            for (int i = 1; i < treePath.length; i++) {
                String nombre = (String) ((DefaultMutableTreeNode)treePath[i]).getUserObject();
                for (int j = posLinea; j < lineasValidas.size(); j++) {
                    if (lineasValidas.get(j).replaceAll("\\s","").equals("<CARPETAnombre=\"" + nombre + "\">")||
                            (lineasValidas.get(j).replaceAll("\\s","").contains("<ARCHIVOnombre=")&&i==treePath.length-1)){
                        break;
                    } else if (i==1&&lineasValidas.get(j).replaceAll("\\s","").equals("</PROYECTO>")||
                            i>1&&lineasValidas.get(j).replaceAll("\\s","").equals("</CARPETA>")){
                        throw new FileNotFoundException();
                    } else {
                        posLinea++;
                    }
                }
            }
        } else {
            for (int i = 0; i < lineasValidas.size(); i++) {
                if (lineasValidas.get(i).replaceAll("\\s","").equals("<PROYECTOnombre=\"" + nombre + "\">")){
                    break;
                } else if (i==0&&lineasValidas.get(i).trim().equals("</PROYECTO>")){
                    throw new FileNotFoundException();
                } else {
                    posLinea++;
                }
            }
        }
        return posLinea;
    }


    public ProyectoLector getProyectoLector() {
        return proyectoLector;
    }



}

package edu.cunoc.UI;

import edu.cunoc.Archivador.Proyecto;
import edu.cunoc.Componentes.Arbol;
import edu.cunoc.Componentes.Tabla;
import edu.cunoc.Proyecto.ProyectoLector;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Path;

public class VentanaPrincipal extends JFrame{
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem open, create;
    private JPanel globalPanel, consolaPanel, erroresPanel;
    private JTree arbol;
    private JTabbedPane archivosTabbed;
    private JTextArea queryTextArea;
    private JScrollPane resultadosScrollPane, arbolScrollPane, queryPane, erroresScrollPane;
    private JTable erroresTable;
    private Tabla tabla;
    private ProyectoLector proyectoLector;
    private Path path;

    public VentanaPrincipal(ProyectoLector proyectoLector){
        this.tabla = new Tabla();
        this.proyectoLector = proyectoLector;
        fixComponents();
    }

    private void setMenuBar(JFrame jFrame){
        menuBar = new JMenuBar();
        menu = new JMenu("Abrir");
        open = new JMenuItem("Abrir Proyecto");

        create = new JMenuItem("Nuevo Proyecto");
        menu.add(open);
        menu.add(create);
        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);
    }

    private void setButtons(){
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("IDE file", "ide");
                fileChooser.addChoosableFileFilter(filtro);
                int valido = fileChooser.showOpenDialog(null);
                if (valido == JFileChooser.APPROVE_OPTION){
                    path = Path.of(fileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        updateProyecFromFile();
                        Arbol arbolMan = new Arbol(proyectoLector, (VentanaPrincipal) redundar());
                        arbolScrollPane.remove(arbol);
                        JTree tree = arbolMan.dibujarArbol();
                        arbolMan.addActionListener(tree);
                        arbolScrollPane.add(tree);
                        setArbol(tree);
                        arbolScrollPane.getViewport().add(tree);
                        arbolScrollPane.revalidate();
                        arbolScrollPane.repaint();
                    } catch (FileNotFoundException ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(new JFrame(),"No se encontro el directorio.\n" +
                                "No se creo el proyecto");
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"Este archivo no es valido.");
                }
            }
        });
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaNewProyect ventanaNewProyect = new VentanaNewProyect(proyectoLector, (VentanaPrincipal) redundar());            }
        });
    }

    public void fixComponents(){
        JFrame frame = new JFrame("Ventana Principal");
        frame.setContentPane(globalPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMenuBar(frame);
        setButtons();
        frame.pack();
        frame.setVisible(true);
    }

    private JFrame redundar() { return this; }

    public void setArbol(JTree arbol) {
        this.arbol = arbol;
        redundar().pack();
    }

    public JScrollPane getArbolScrollPane() {
        return arbolScrollPane;
    }

    public JTree getArbol() {
        return arbol;
    }

    public void setProyectoLector(ProyectoLector proyectoLector) {
        this.proyectoLector = proyectoLector;
    }

    public void updateProyecFromFile() throws Exception {
        setProyectoLector(new Proyecto().actualizarProyecto(path));
        proyectoLector.setPath(path.toString());
    }

    public ProyectoLector getProyectoLector() {
        return proyectoLector;
    }
}

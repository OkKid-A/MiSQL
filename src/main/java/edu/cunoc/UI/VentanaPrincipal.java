package edu.cunoc.UI;

import edu.cunoc.Archivador.Proyecto;
import edu.cunoc.Componentes.Arbol;
import edu.cunoc.Componentes.Tabla;
import edu.cunoc.Instructor.InstructorSQL;
import edu.cunoc.Instructor.SQLParser;
import edu.cunoc.Instructor.SQLexer;
import edu.cunoc.Proyecto.ProyectoLector;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Path;

public class VentanaPrincipal extends JFrame{
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem open, create;
    private JPanel globalPanel, consolaPanel;
    private JTree arbol;
    private JTabbedPane archivosTabbed;
    private JTextArea queryTextArea;
    private JScrollPane resultadosScrollPane, arbolScrollPane, queryPane;
    private JTextArea resultadosTextArea;
    private JPanel resultPanel;
    private Tabla tabla;
    private ProyectoLector proyectoLector;
    private Path path;

    public VentanaPrincipal(){
        this.tabla = new Tabla();
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

        int cuando = JComponent.WHEN_FOCUSED;
        InputMap inputMap = queryTextArea.getInputMap(cuando);
        ActionMap actionMap = queryTextArea.getActionMap();

        KeyStroke enterPres = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);
        String enterKey = (String) inputMap.get(enterPres);
        actionMap.put(enterKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (proyectoLector!=null) {
                    SQLexer lexer = new SQLexer(new StringReader(queryTextArea.getText()));
                    SQLParser sqlParser = new SQLParser(lexer,proyectoLector);
                    try {
                        sqlParser.parse();
                        InstructorSQL instructorSQL = sqlParser.getInstructorSQL();
                        if (instructorSQL.getErrorAsString().isEmpty()){
                            instructorSQL.procQueries((VentanaPrincipal) redundar(),proyectoLector);
                        }
                        String textoConsola = instructorSQL.getErrorAsString();
                        resultPanel.removeAll();
                        resultPanel.setLayout(new BorderLayout());
                        resultPanel.add(new PanelCSV(textoConsola, (VentanaPrincipal) redundar()).getCsvPanel());
                        resultPanel.repaint();
                        redundar().revalidate();
                        redundar().pack();
                        System.out.println(instructorSQL.getQueries());
                    } catch (Exception ex) {
                        InstructorSQL instructorSQL = sqlParser.getInstructorSQL();
                        String textoConsola = instructorSQL.getErrorAsString();
                        resultPanel.removeAll();
                        resultPanel.setLayout(new BorderLayout());
                        try {
                            resultPanel.add(new PanelCSV(textoConsola, (VentanaPrincipal) redundar()).getCsvPanel());
                        } catch (IOException exc) {
                            throw new RuntimeException(exc);
                        }
                        resultPanel.revalidate();
                        resultPanel.repaint();
                        redundar().revalidate();
                        redundar().pack();
                        System.out.println(instructorSQL.getQueries());
                    }
                } else {
                    JOptionPane.showMessageDialog(redundar(),"No se ha seleccionado un proyecto");
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

    public JTabbedPane getArchivosTabbed() {
        return archivosTabbed;
    }
}

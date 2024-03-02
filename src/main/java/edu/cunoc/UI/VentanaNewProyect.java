package edu.cunoc.UI;

import edu.cunoc.Componentes.Arbol;
import edu.cunoc.Proyecto.EscritorProyecto;
import edu.cunoc.Proyecto.ProyectoLector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VentanaNewProyect extends JFrame{
    private JPanel globalPanel;
    private JButton selecRutaButton, crearProyectoButton;
    private JTextField nombreField;
    private JLabel rutaLabel;
    private ProyectoLector proyectoLector;
    private String ruta;
    private JScrollPane jframe;
    private JTree jTree;
    private Arbol arbol;
    private JFrame frame;
    private VentanaPrincipal ventanaPrincipal;

    public VentanaNewProyect(ProyectoLector proyectoLector, VentanaPrincipal ventanaPrincipal) {
        setButtons();
        fixComponents();
        this.proyectoLector = proyectoLector;
        this.jframe = ventanaPrincipal.getArbolScrollPane();
        this.jTree = ventanaPrincipal.getArbol();
        this.arbol = new Arbol(proyectoLector,ventanaPrincipal);
        this.ventanaPrincipal = ventanaPrincipal;
    }

    private void setButtons(){
        crearProyectoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ruta!=null && nombreField.getText()!=null){
                    proyectoLector.setNombre(nombreField.getText());
                    proyectoLector.setPath(ruta);
                    EscritorProyecto escritorProyecto = new EscritorProyecto(proyectoLector);
                    ventanaPrincipal.setProyectoLector(proyectoLector);
                    try {
                        JTree tree = arbol.dibujarArbol();
                        arbol.addActionListener(tree);
                        escritorProyecto.crearProyecto();
                        ventanaPrincipal.setArbol(tree);
                        jframe.remove(jTree);
                        jframe.add(tree);
                        jframe.getViewport().add(tree);
                        jframe.revalidate();
                        jframe.repaint();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(new JFrame(),"No se encontro el directorio.\n" +
                                "No se creo el proyecto");
                    }
                    getFrame().dispose();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"No se encontro el directorio o el nombre no es valido.");
                }
            }
        });
        selecRutaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.showSaveDialog(null);
                ruta = fileChooser.getSelectedFile().getAbsolutePath();
                rutaLabel.setText("Ruta: "+ruta);
                redundar().pack();
            }
        });
    }

    public void fixComponents(){
        this.frame = new JFrame("Nuevo Proyecto");
        frame.setContentPane(globalPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JFrame redundar(){
        return this;
    }

    public JFrame getFrame() {
        return frame;
    }
}

package edu.cunoc.UI;

import edu.cunoc.Componentes.Arbol;
import edu.cunoc.Proyecto.EscritorProyecto;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VentanaNewFolder extends JFrame{
    private JPanel globalPanel;
    private JLabel rutaLabel;
    private JButton selecRutaButton;
    private JTextField nombreField;
    private JButton crearCarpetaButton;
    private JPanel folderPanel;
    private JFrame frame;
    private EscritorProyecto escritorProyecto;
    private String ruta;
    private TreeNode[] depth;
    private VentanaPrincipal ventanaPrincipal;
    private JScrollPane scrollPane;
    public VentanaNewFolder(EscritorProyecto escritorProyecto, TreeNode[] depth, VentanaPrincipal ventanaPrincipal) throws HeadlessException {
        this.escritorProyecto = escritorProyecto;
        this.depth = depth;
        this.ventanaPrincipal = ventanaPrincipal;
        this.scrollPane = ventanaPrincipal.getArbolScrollPane();
        setButtons();
        fixComponents();
    }

    private void setButtons(){
        crearCarpetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    escritorProyecto.crearCarpeta(depth, nombreField.getText());
                    ventanaPrincipal.updateProyecFromFile();
                    Arbol arbol= new Arbol(ventanaPrincipal.getProyectoLector(), ventanaPrincipal);
                    JTree tree = arbol.dibujarArbol();
                    arbol.addActionListener(tree);
                    scrollPane.remove(ventanaPrincipal.getArbol());
                    ventanaPrincipal.setArbol(new JTree());
                    scrollPane.add(tree);
                    scrollPane.getViewport().add(tree);
                    scrollPane.revalidate();
                    scrollPane.repaint();
                    getFrame().dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(),"No se encontro la direccion.\n" +
                            "No se creo la carpeta");;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(),"No se encontro el archivo IDE");
                }
            }
        });

    }

    public void fixComponents(){
        this.frame = new JFrame("Nueva Carpeta");
        frame.setContentPane(globalPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame redundar(){
        return this;
    }

    public JFrame getFrame() {
        return frame;
    }
}

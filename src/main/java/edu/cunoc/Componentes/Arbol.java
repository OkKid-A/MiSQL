package edu.cunoc.Componentes;

import edu.cunoc.Archivador.Archivo;
import edu.cunoc.Archivador.Carpeta;
import edu.cunoc.Proyecto.EscritorProyecto;
import edu.cunoc.Proyecto.ProyectoLector;
import edu.cunoc.UI.PanelCSV;
import edu.cunoc.UI.VentanaNewFile;
import edu.cunoc.UI.VentanaNewFolder;
import edu.cunoc.UI.VentanaPrincipal;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Arbol extends JTree {

    private ProyectoLector proyectoLector;
    private EscritorProyecto escritorProyecto;
    private VentanaPrincipal ventanaPrincipal;
    public Arbol(ProyectoLector proyectoLector, VentanaPrincipal ventanaPrincipal) {
        this.proyectoLector = proyectoLector;
        this.escritorProyecto = new EscritorProyecto(proyectoLector);
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public JTree dibujarArbol(){
        ArrayList<Archivo> archivos = proyectoLector.getArchivos();
        ArrayList<Carpeta> carpetas = proyectoLector.getCarpetas();
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(proyectoLector.getNombre());
        dibujarArchivos(archivos,raiz);
        dibujarCarpeta(carpetas,raiz);
        JTree dibujado = new JTree(raiz);
        dibujado.setCellRenderer(new ArbolRenderer());
        return dibujado;
    }

    private void dibujarArchivos(ArrayList<Archivo> archivos, DefaultMutableTreeNode node){
        if (archivos!=null) {
            for (Archivo a : archivos) {
                node.add(new DefaultMutableTreeNode(a));
            }
        }
    }

    private void dibujarCarpeta(ArrayList<Carpeta> carpetas, DefaultMutableTreeNode node){
        if (carpetas!=null) {
            for (Carpeta c : carpetas) {
                DefaultMutableTreeNode carpeta = new DefaultMutableTreeNode(c);
                node.add(carpeta);
                dibujarArchivos(c.getArchivos(),carpeta);
                dibujarCarpeta(c.getCarpetas(),carpeta);
            }
        }
    }


    public void addActionListener(JTree jTree){
        jTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()==1) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        int selected = jTree.getRowForLocation(e.getX(), e.getY());
                        TreePath rutaNodo = jTree.getPathForLocation(e.getX(), e.getY());
                        if (selected != -1) {
                            if (rutaNodo != null) {
                                JPopupMenu popupMenu = setPopUpNuevo(rutaNodo);
                                popupMenu.show(jTree, e.getX(), e.getY());
                            }
                        }
                    }
                } else if (e.getClickCount() == 2) {
                    int selected = jTree.getRowForLocation(e.getX(), e.getY());
                    TreePath rutaNodo = jTree.getPathForLocation(e.getX(), e.getY());
                    if (selected!=-1) {
                        if (rutaNodo!=null) {
                            DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) rutaNodo.getLastPathComponent();
                            if (nodoSeleccionado.getUserObject() instanceof Archivo) {
                                Archivo archivo = (Archivo) nodoSeleccionado.getUserObject();
                                try {
                                    ventanaPrincipal.getArchivosTabbed().addTab(archivo.getName(),
                                            new PanelCSV(archivo,ventanaPrincipal).getCsvPanel());
                                    ventanaPrincipal.getArchivosTabbed().setTabPlacement(JTabbedPane.LEFT);
                                    ventanaPrincipal.revalidate();
                                    ventanaPrincipal.pack();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private JPopupMenu setPopUpNuevo(TreePath rutaNodo) {
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) rutaNodo.getLastPathComponent();
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem archivo = new JMenuItem("Nuevo Archivo");
        archivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeNode[] treePath =
                        ((DefaultMutableTreeNode) nodoSeleccionado).getPath();
                VentanaNewFile venFile = new VentanaNewFile(new EscritorProyecto(proyectoLector), treePath, ventanaPrincipal);
            }
        });
        JMenuItem carpeta = new JMenuItem("Nueva Carpeta");
        carpeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeNode[] treePath =
                        ((DefaultMutableTreeNode) nodoSeleccionado).getPath();
                VentanaNewFolder venFolder = new VentanaNewFolder(new EscritorProyecto(proyectoLector), treePath, ventanaPrincipal);
            }
        });
        popupMenu.add(archivo);
        popupMenu.add(carpeta);
        return popupMenu;
    }
}

package edu.cunoc.Componentes;

import edu.cunoc.Archivador.Carpeta;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class ArbolRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
            Object object = nodo.getUserObject();
            if (object instanceof Carpeta){
                setIcon(UIManager.getIcon("FileView.directoryIcon"));
            }
        }
        return this;
    }
}

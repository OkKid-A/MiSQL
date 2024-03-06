package edu.cunoc.Componentes;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Tabla {

    private JTable tabla;

    public Tabla(){

    }

    public JTable crearTablaSimple(Object[][] datosFilas, String[] datosHeader) {
        DefaultTableModel tableModel = new DefaultTableModel(datosFilas, datosHeader) {
            @Override
            public Object getValueAt(int row, int column) {
                return super.getValueAt(row, column);
            }
        };
        JTable jTable = new JTable(datosFilas, datosHeader);
        jTable.setModel(tableModel);
        return jTable;
    }

    public JTable recrearTabla(JPanel panel, JScrollPane jScrollPane, JFrame jFrame, Object[][] datosFilas, String[] datosHeader){
        panel.removeAll();
        panel.setLayout(new GridLayout());
        JTable resultadosTable = crearTablaSimple(datosFilas,datosHeader);
        return setTabla(panel, resultadosTable);
    }

    private JTable setTabla(JPanel panel, JTable resultadosTable) {
        JScrollPane jScrollPane;
        jScrollPane = new JScrollPane(resultadosTable);
        jScrollPane.add(resultadosTable);
        jScrollPane.setVisible(true);
        jScrollPane.getViewport().add(resultadosTable);
        jScrollPane.repaint();
        panel.add(jScrollPane);
        panel.revalidate();
        return  resultadosTable;
    }
}

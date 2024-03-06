package edu.cunoc.UI;

import edu.cunoc.Componentes.Tabla;

import javax.swing.*;
import java.awt.*;

public class VentanaTabla extends JFrame{
    private JPanel globalPanel;
    private JScrollPane tablaPane;
    private JTable tablaResul;
    private Tabla tabla;

    public VentanaTabla(String[] headers, String[][] filas) throws HeadlessException {
        this.tabla = new Tabla();
        fixComponents();
        tabla.recrearTabla(globalPanel,tablaPane,redundar(),filas,headers);
    }

    public void fixComponents(){
        JFrame frame = new JFrame("Resultados");
        frame.setContentPane(globalPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame redundar(){
        return this;
    }
}

package edu.cunoc.UI;

import edu.cunoc.Archivador.Archivo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class PanelCSV{

    private JPanel csvPanel;
    private JTextPane csvPane;
    private JScrollPane textScrollPane;
    private Archivo archivo;
    private String nameLabel;
    private static final int TAM = 500;
    private static final double MAX = 1 / 3d;
    private String texto;


    public PanelCSV(Archivo archivo, VentanaPrincipal ventanaPrincipal) throws IOException {
        this.texto = String.join("\n",Files.readAllLines(Path.of(archivo.getPath())));
        fixComponents();
    }

    public PanelCSV(String texto, VentanaPrincipal ventanaPrincipal) throws IOException {
        this.texto = texto;
        fixComponents();
    }

    public void fixComponents() throws IOException {
        csvPanel = new JPanel(new BorderLayout(5,5));
        csvPanel.setBorder(new EmptyBorder(4,4,4,4));
        csvPane = new JTextPane();
        String textArchivo = texto;
        textScrollPane = new JScrollPane(csvPane){
            @Override
            public Dimension getPreferredSize() {
                Dimension dimension = super.getPreferredSize();
                int preferido = (int) (csvPanel.getSize().height*MAX);
                int limite = Math.min(preferido, dimension.height);
                return new Dimension(TAM,limite);
            }
        };
        csvPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSize();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSize();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSize();
            }

            private void updateSize(){
                textScrollPane.revalidate();
            }
        });
        csvPane.setText(textArchivo);
        csvPanel.add(textScrollPane,BorderLayout.CENTER);
    }

    public JPanel getCsvPanel() {
        return csvPanel;
    }
}

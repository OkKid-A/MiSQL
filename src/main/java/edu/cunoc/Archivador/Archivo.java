package edu.cunoc.Archivador;

public class Archivo extends Location{

    private String path;
    private String localPath;

    public Archivo(String nombre) {
        super(nombre);
    }

    public Archivo(String name, String path) {
        super(name);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return getName();
    }
}

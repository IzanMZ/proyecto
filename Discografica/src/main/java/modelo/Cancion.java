/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isard
 */
public class Cancion {

    private int codigo;
    private static int contador;
    private int codigo_album;
    private int posicion;
    private String titulo;
    private String compositor;
    private String duracion;

    public Cancion(int codigo_album, int posicion, String titulo, String compositor, String duracion) {
        this.codigo = contador++;
        this.codigo_album = codigo_album;
        this.posicion = posicion;
        this.titulo = titulo;
        this.compositor = compositor;
        this.duracion = duracion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo_album() {
        return codigo_album;
    }

    public void setCodigo_album(int codigo_album) {
        this.codigo_album = codigo_album;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCompositor() {
        return compositor;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

}

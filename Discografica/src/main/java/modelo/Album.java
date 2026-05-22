/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isard
 */
public class Album {

    private int codigo;
    private static int contador;
    private String autor;
    private Integer codigo_banda;
    private Integer codigo_musico;
    private String titulo;
    private int anio_publicacion;
    private String tipo;
    private String duracion;

    public Album(String autor, Integer codigo_banda, Integer codigo_musico, String titulo, int anio_publicacion, String tipo, String duracion) {
        this.codigo = contador++;
        this.autor = autor;
        this.codigo_banda = codigo_banda;
        this.codigo_musico = codigo_musico;
        this.titulo = titulo;
        this.anio_publicacion = anio_publicacion;
        this.tipo = tipo;
        this.duracion = duracion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getCodigo_banda() {
        return codigo_banda;
    }

    public void setCodigo_banda(int codigo_banda) {
        this.codigo_banda = codigo_banda;
    }

    public Integer getCodigo_musico() {
        return codigo_musico;
    }

    public void setCodigo_musico(int codigo_musico) {
        this.codigo_musico = codigo_musico;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio_publicacion() {
        return anio_publicacion;
    }

    public void setAño_publicacion(int anio_publicacion) {
        this.anio_publicacion = anio_publicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

}

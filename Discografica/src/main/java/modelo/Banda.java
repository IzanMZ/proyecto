/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isard
 */
public class Banda {

    private int codigo ;
    private static int contador ;
    private String nombre;
    private String anos_actuacion;//
    private String lugar_origen;
    private String genero;

    public Banda( String nombre, String anos_actuacion, String lugar_origen, String genero) {
        this.codigo = contador++;
        this.nombre = nombre;
        this.anos_actuacion = anos_actuacion;
        this.lugar_origen = lugar_origen;
        this.genero = genero;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnios_actuacion() {
        return anos_actuacion;
    }

    public void setAnos_actuacion(String anos_actuacion) {
        this.anos_actuacion = anos_actuacion;
    }

    public String getLugar_origen() {
        return lugar_origen;
    }

    public void setLugar_origen(String lugar_origen) {
        this.lugar_origen = lugar_origen;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

}

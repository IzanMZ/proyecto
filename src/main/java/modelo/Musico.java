/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isard
 */
public class Musico {
      
    private  int codigo;
     private static int contador ;
    private Integer codigo_banda;
    private String nombre;
    private String fecha_nacimiento;
    private String lugar_residencia;
    private String nacionalidad;
    private String instrumento;

    public Musico( Integer codigo_banda, String nombre, String fecha_nac, String residencia, String nacionalidad, String instrumento) {
             this.codigo = contador++;
        this.codigo_banda = codigo_banda;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nac;
        this.lugar_residencia = residencia;
        this.nacionalidad = nacionalidad;
        this.instrumento = instrumento;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo_banda() {
        return codigo_banda;
    }

    public void setCodigo_banda(Integer codigo_banda) {
        this.codigo_banda = codigo_banda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_nacimineto() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nac) {
        this.fecha_nacimiento = fecha_nac;
    }

    public String getResidencia() {
        return lugar_residencia;
    }

    public void setResidencia(String residencia) {
        this.lugar_residencia = residencia;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

}

package dam.jlr.fichajes0.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
@Entity
@Table(name = "fichajes")
public class Fichaje implements java.io.Serializable {
    @Override
    public String toString() {
        return "Fichaje{" +
                "idfichaje=" + idfichaje +
                ", tipo='" + tipo + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", empleado=" + empleado.toString() +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idFichaje",nullable = false)
    private int idfichaje;
    @Column(name = "Tipo",nullable = false)

    private String tipo;
@Column(name = "Fecha")
    private Date fecha;
@Column(name = "Hora")
    private Time hora;



    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "id")
    private Empleado empleado;

    public Fichaje() {

    }

    public int getIdfichaje() {
        return idfichaje;
    }

    public void setIdfichaje(int idfichaje) {
        this.idfichaje = idfichaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }



    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Fichaje(String tipo, Date date, Time time,Empleado empleado) {
        this.tipo = tipo;
        this.fecha = date;
        this.hora = time;

        this.empleado = empleado;

    }
}
//getters and setters

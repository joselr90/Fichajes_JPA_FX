package dam.jlr.fichajes0.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
        private int id;
    @Column(name="Activo",nullable = false)
        private boolean activo;
@Column(name="Nombre",nullable = false)
    private String nombre;
@Column(name="Apellidos",nullable = false)
    private String apellido;




//    public String getFichjajess() {
//        return fichjajess;
//    }

//    @Column(name="Fichajes")
//    private String fichjajess=getFichajes();


    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "empleado", cascade = {CascadeType.REMOVE,CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})





    private Set<Fichaje> fichajes = new LinkedHashSet<Fichaje>();


    public Empleado(){

    }

    public Empleado(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;

    }

    public int getId() {
        return id;
    }

    public Set<Fichaje> getFichajes() {
        return (fichajes);
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFichajes(Set<Fichaje> fichajes) {
        this.fichajes = fichajes;
    }
public void agnadirFichaje(Fichaje fichaje){
    fichajes.add(fichaje);
}

    @Override
    public String toString() {
        return nombre;
    }
}

//    public Set<Fichaje> getFichajes() {
//        return fichajes;
//    }
//
//    public void setFichajes(Set<Fichaje> fichajes) {
//        this.fichajes = fichajes;
//    }

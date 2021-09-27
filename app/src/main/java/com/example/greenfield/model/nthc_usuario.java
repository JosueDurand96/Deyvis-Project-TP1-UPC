package com.example.greenfield.model;

public class nthc_usuario {
    private int id;
    private String Nombre, ApellidoMaterno, ApellidoPaterno, DNI, Correo, Telefono, Direccion, Usuario, Clave;
    private String FotoPerfil;

    public nthc_usuario(int id, String nombre, String apellidoMaterno, String apellidoPaterno, String DNI, String correo, String telefono, String direccion, String usuario, String clave, String fotoPerfil) {
        this.id = id;
        this.Nombre = nombre;
        this.ApellidoMaterno = apellidoMaterno;
        this.ApellidoPaterno = apellidoPaterno;
        this.DNI = DNI;
        this.Correo = correo;
        this.Telefono = telefono;
        this.Direccion = direccion;
        this.Usuario = usuario;
        this.Clave = clave;
        this.FotoPerfil = fotoPerfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        ApellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        ApellidoPaterno = apellidoPaterno;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getFotoPerfil() {
        return FotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        FotoPerfil = fotoPerfil;
    }
}

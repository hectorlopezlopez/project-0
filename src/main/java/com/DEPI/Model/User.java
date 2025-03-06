package com.DEPI.Model;

public class User {
    private int id;
    private String name;
    private String lastName;
    private String phone;
    private String address;
    private String mail;
    private String password;
    private int rol;

    public User() {
    }

    public User(String password) {
        this.password = password;
    }

    public User(int id, String name, String lastName, String phone, String address, String mail, String password, int rol) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.mail = mail;
        this.password = password;
        this.rol = rol;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
}

package com.lisis.charles.fastreport;

/**
 * Created by dsk_derecha on 30/05/2016.
 */
public class generarCuerpoMail {

    DB_User u;
    DB_Vehicle v;
    DB_Accident a;

    public generarCuerpoMail(DB_User u, DB_Vehicle v, DB_Accident a){

        this.u = u;
        this.v = v;
        this.a = a;

    }

    public String generarMail(){
        String mail;

        mail = "\n" + "Información sobre el accidente ocurrido en la fecha del dia " + a.getDate() +
                " a las " + a.getHour() + ".\n" + "\n" +
                "Información sobre el usuario accidentado:" + "\n" + "\n" +
                "Nombre: " + u.getName() + "\n" +
                "Apellidos: " + u.getLastname() + "\n" +
                "Dirección: " + u.getAddress() + "\n" +
                "Teléfono: " + u.getPhoneNumber() + "\n" +
                "Nº de permiso de conducir: " + u.getDriverLicense() + "\n" +
                "Fecha de vencimiento: " + u.getExpiration_date() + "\n" + "\n" +
                "Información sobre el vehículo accidentado:" + "\n" + "\n" +
                "Marca: " + v.getBrand() + "\n" +
                "Modelo: " + v.getModel() + "\n" +
                "Nº de Matrícula: " + v.getRegistrationNumber() + "\n" +
                "Aseguradora: " + v.getInsurance() + "\n" +
                "Nº de Poliza: " + v.getPolicyNumber() + "\n" + "\n" +
                "Mensaje enviado desde la aplicación FastReport."
        ;

        return mail;
    }

}

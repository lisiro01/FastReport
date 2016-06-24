package Extras;

import com.lisis.charles.fastreport.DB_Accident;
import com.lisis.charles.fastreport.DB_User;
import com.lisis.charles.fastreport.DB_Vehicle;

public class GenerateMailHelper {

    DB_User user;
    DB_Vehicle vehicle;
    DB_Accident accident;

    public GenerateMailHelper(DB_User u, DB_Vehicle v, DB_Accident a){

        this.user = u;
        this.vehicle = v;
        this.accident = a;

    }

    public String generarMail(){
        String mail;

        mail = "\n" + "Información sobre el accidente ocurrido en la fecha del día " + accident.getDate() +
                " a las " + accident.getHour() + ".\n" + "\n" +
                "Información sobre el conductor accidentado:" + "\n" + "\n" +
                "Nombre: " + user.getName() + "\n" +
                "Apellidos: " + user.getLastname() + "\n" +
                "Dirección: " + user.getAddress() + "\n" +
                "Teléfono: " + user.getPhoneNumber() + "\n" +
                "Nº de permiso de conducir: " + user.getDriverLicense() + "\n" +
                "Fecha de vencimiento: " + user.getExpiration_date() + "\n" + "\n" +
                "Información sobre el vehículo accidentado:" + "\n" + "\n" +
                "Marca: " + vehicle.getBrand() + "\n" +
                "Modelo: " + vehicle.getModel() + "\n" +
                "Nº de Matrícula: " + vehicle.getRegistrationNumber() + "\n" +
                "Aseguradora: " + vehicle.getInsurance() + "\n" +
                "Nº de Póliza: " + vehicle.getPolicyNumber() + "\n" + "\n" +
                "Mensaje enviado desde la aplicación FastReport."
        ;

        return mail;
    }

}

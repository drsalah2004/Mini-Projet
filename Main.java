package app;

import services.userservices;

public class Main {
    public static void main(String[] args) {

        userservices service = new userservices();

        service.register("Salah32", "salah2004@gmail.com", "Lolo");
        service.login("salah2004@gmail.com", "Lolo");
    }
}
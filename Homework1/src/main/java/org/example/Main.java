package org.example;

public class Main {
    public static void main(String[] args) {
        Server server=new Server();
        new Client(server,"Ivan","12345");
        new Client(server,"Petr","12345");
    }
}
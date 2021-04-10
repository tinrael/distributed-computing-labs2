package ua.knu.csc;

public class Main {

    public static void main(String[] args) {
        DOMBuilder domBuilder = new DOMBuilder();
        System.out.println(domBuilder.build("data/departments.xml"));
    }
}

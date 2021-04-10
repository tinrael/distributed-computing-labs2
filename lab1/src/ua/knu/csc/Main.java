package ua.knu.csc;

public class Main {

    public static void main(String[] args) {
        XMLValidator.validate("data/departments.xml", "data/departments.xsd");
        DOMBuilder domBuilder = new DOMBuilder();
        System.out.println(domBuilder.build("data/departments.xml"));
    }
}

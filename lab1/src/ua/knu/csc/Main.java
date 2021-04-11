package ua.knu.csc;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        XMLValidator.validate("data/departments.xml", "data/departments.xsd");

        Manager manager = new Manager();

        manager.loadFromFile("data/departments.xml");

        System.out.println("--- --- Before --- ---");
        manager.print();
        System.out.println("--- --- ---- --- ---");

        manager.addDepartment("d-2", "marketing");
        manager.addEmployee("e-3", "Olivia", "Brown", BigInteger.valueOf(1200), "d-2");

        manager.getEmployee("e-1").setSalary(BigInteger.valueOf(700));

        manager.deleteEmployee("e-2");

        manager.deleteDepartment("d-0");

        manager.saveToFile("data/departments-new.xml");

        manager.loadFromFile("data/departments-new.xml");

        System.out.println("--- --- After --- ---");
        manager.print();
        System.out.println("--- --- ---- --- ---");
    }
}

package ua.knu.csc;

import com.company.departments.Department;
import com.company.departments.Employee;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {
    private Manager manager;

    @BeforeEach
    void setUp() {
        manager = new Manager();
        manager.loadFromFile("tests-data/departments.xml");
    }

    @Test
    void addDepartment() {
        assertThrows(IllegalArgumentException.class, () -> manager.addDepartment("d-0", "marketing"));
        assertThrows(IllegalArgumentException.class, () -> manager.addDepartment("d-1", "marketing"));

        assertDoesNotThrow(() -> manager.addDepartment("d-2", "marketing"));

        assertThrows(IllegalArgumentException.class, () -> manager.addDepartment("d-2", "marketing"));
        assertThrows(IllegalArgumentException.class, () -> manager.addDepartment("d-2", "purchase"));
    }

    @Test
    void getDepartment() {
        assertDoesNotThrow(() -> manager.getDepartment("d-0"));

        Department department1 = assertDoesNotThrow(() -> manager.getDepartment("d-1"));
        assertEquals("d-1", department1.getId());

        assertThrows(IllegalArgumentException.class, () -> manager.getDepartment("d-2"));

        manager.addDepartment("d-2", "marketing");

        Department department2 = assertDoesNotThrow(() -> manager.getDepartment("d-2"));
        assertEquals("d-2", department2.getId());
        assertEquals("marketing", department2.getName());
    }

    @Test
    void deleteDepartment() {
        assertThrows(IllegalArgumentException.class, () -> manager.deleteDepartment("d-2"));

        assertDoesNotThrow(() -> manager.deleteDepartment("d-0"));

        assertThrows(IllegalArgumentException.class, () -> manager.getDepartment("d-0"));
        assertThrows(IllegalArgumentException.class, () -> manager.deleteDepartment("d-0"));

        assertDoesNotThrow(() -> manager.getDepartment("d-1"));
    }

    @Test
    void addEmployee() {
        assertThrows(IllegalArgumentException.class, () -> manager.addEmployee("e-0", "William", "Brown", BigInteger.valueOf(1200), "d-1"));
        assertThrows(IllegalArgumentException.class, () -> manager.addEmployee("e-3", "William", "Brown", BigInteger.valueOf(1200), "d-2"));

        assertDoesNotThrow(() -> manager.addEmployee("e-3", "William", "Brown", BigInteger.valueOf(1200), "d-0"));
    }

    @Test
    void getEmployee() {
        assertDoesNotThrow(() -> manager.getEmployee("e-0"));
        assertDoesNotThrow(() -> manager.getEmployee("e-1"));

        Employee employee2 = assertDoesNotThrow(() -> manager.getEmployee("e-2"));
        assertEquals("e-2", employee2.getId());

        assertThrows(IllegalArgumentException.class, () -> manager.getEmployee("e-3"));

        manager.addEmployee("e-3", "William", "Brown", BigInteger.valueOf(1200), "d-0");

        Employee employee3 = assertDoesNotThrow(() -> manager.getEmployee("e-3"));

        assertEquals("e-3", employee3.getId());
        assertEquals("William", employee3.getForename());
        assertEquals("Brown", employee3.getSurname());
        assertEquals(BigInteger.valueOf(1200), employee3.getSalary());
    }

    @Test
    void deleteEmployee() {
        assertThrows(IllegalArgumentException.class, () -> manager.deleteEmployee("e-3"));

        assertDoesNotThrow(() -> manager.deleteEmployee("e-2"));

        assertThrows(IllegalArgumentException.class, () -> manager.getEmployee("e-2"));
        assertThrows(IllegalArgumentException.class, () -> manager.deleteEmployee("e-2"));
    }
}
package pro.sky.hw25.service;

import org.springframework.stereotype.Service;
import pro.sky.hw25.domain.Employee;
import pro.sky.hw25.exception.EmployeeAlreadyAddedException;
import pro.sky.hw25.exception.EmployeeNotFoundException;
import pro.sky.hw25.exception.EmployeeStorageIsFullException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private final static int SIZE = 5;
    private final List<Employee> employees = new ArrayList<>(SIZE);

    @PostConstruct
    public void init() {
        employees.add(new Employee("Андрей", "Петухов"));
        employees.add(new Employee("Кристина", "Сухова"));
        employees.add(new Employee("Александр", "Марьенко"));
    }

    public Employee add(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.size() < SIZE) {
            if (employees.contains(employee)) {
                throw new EmployeeAlreadyAddedException("в массиве уже есть такой сотрудник");
            }
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException("массив сотрудников переполнен");
    }


    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.remove(employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException("сотрудник не найден");
    }


    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException("сотрудник не найден");
    }

    public List<Employee> List() {
        return Collections.unmodifiableList(employees);
    }
}
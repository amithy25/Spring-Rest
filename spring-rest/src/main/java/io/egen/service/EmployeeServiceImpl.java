package io.egen.service;

import io.egen.entity.Employee;
import io.egen.exception.EmployeeNotFoundException;
import io.egen.repository.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepositoryImpl employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findOne(String id) {
        Employee employee =  employeeRepository.findOne(id);
        if(employee == null) {
            throw new EmployeeNotFoundException("Employee with id: "+ id+ "Not Found.");
        }else{
            return employee;
        }
    }

    @Override
    @Transactional
    public Employee create(Employee emp) {
        Employee existing = employeeRepository.findByEmail(emp.getEmail());

        if(existing != null){
            throw new RuntimeException("Employee with email:"+ emp.getEmail() +" already exists!");
        }
        return employeeRepository.create(emp);

    }

    @Override
    @Transactional
    public Employee update(String id, Employee emp) {
        Employee existing = employeeRepository.findOne(id);

        if(existing != null){
            throw new RuntimeException("Employee with id:"+ id +" already exists!");
        }

        return employeeRepository.update(emp);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Employee existing = employeeRepository.findOne(id);

        if(existing == null){
            throw new RuntimeException("Employee with id:"+ id +" doesn't exists!");
        }

        employeeRepository.delete(existing);
    }
}

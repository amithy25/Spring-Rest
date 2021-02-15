package io.egen.repository;

import io.egen.entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> findAll() {

        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findAll", Employee.class);
        return query.getResultList();
    }

    @Override
    public Employee findOne(String id) {
        Employee employee = entityManager.find(Employee.class, id);

        return employee;
    }

    @Override
    public Employee findByEmail(String email) {
        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findByEmail", Employee.class);
        query.setParameter("paramEmail", email);

        List<Employee> resultList = query.getResultList();
        if(resultList != null && resultList.size() == 1)
            return resultList.get(0);
        return null;
    }

    @Override

    public Employee create(Employee emp) {
        entityManager.persist(emp);
        return emp;
    }

    @Override
    public Employee update(Employee emp) {
        return entityManager.merge(emp);
    }

    @Override
    public void delete(Employee emp) {
        entityManager.remove(emp);
    }


}

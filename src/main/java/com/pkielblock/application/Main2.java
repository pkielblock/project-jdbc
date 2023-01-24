package com.pkielblock.application;

import com.pkielblock.model.dao.DaoFactory;
import com.pkielblock.model.dao.DepartmentDao;
import com.pkielblock.model.entities.Department;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Test 1: Department findById ===");
        Department department = departmentDao.findById(3);
        System.out.println(department);

        System.out.println("\n=== Test 2: Department findAll ===");
        List<Department> list2 = departmentDao.findAll();
        for (Department obj : list2) {
            System.out.println(obj);
        }

        System.out.println("\n=== Test 3: Department insert ===");
        Department newDepartment = new Department(null, "Esport");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted, New ID = " + newDepartment.getId());

        System.out.println("\n=== Test 4: Department update ===");
        department = departmentDao.findById(1);
        department.setName("Compiuter");
        departmentDao.update(department);
        System.out.println("Update Completed");

        System.out.println("\n=== Test 5: Department delete ===");
        System.out.print("ID to delete: ");
        int id = sc.nextInt();
        departmentDao.deleteById(id);
    }
}

package com.pkielblock.application;

import com.pkielblock.model.dao.DaoFactory;
import com.pkielblock.model.dao.SellerDao;
import com.pkielblock.model.entities.Department;
import com.pkielblock.model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Test 1: Seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== Test 2: Seller findDepartmentById ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("\n=== Test 3: Seller findAll ===");
        List<Seller> list2 = sellerDao.findAll();
        for (Seller obj : list2) {
            System.out.println(obj);
        }

        System.out.println("\n=== Test 4: Seller insert ===");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted, New ID = " + newSeller.getId());

        System.out.println("\n=== Test 5: Seller update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Wayne");
        sellerDao.update(seller);
        System.out.println("Update Completed");

        System.out.println("\n=== Test 6: Seller delete ===");
        System.out.print("ID to delete: ");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
    }
}
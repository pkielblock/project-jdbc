package com.pkielblock.application;

import com.pkielblock.model.entities.Department;
import com.pkielblock.model.entities.Seller;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Department department = new Department(1, "Books");
        Seller seller = new Seller(1, "Pedro", "pedro@gmail.com", new Date(), 3000.0, department);

        System.out.println(seller);
    }
}
package com.pkielblock.application;

import com.pkielblock.model.dao.DaoFactory;
import com.pkielblock.model.dao.SellerDao;
import com.pkielblock.model.entities.Department;
import com.pkielblock.model.entities.Seller;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);
    }
}
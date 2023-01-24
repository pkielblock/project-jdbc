package com.pkielblock.model.dao;

import com.pkielblock.model.dao.implementation.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC();
    }
}
package com.pkielblock.model.dao;

import com.pkielblock.db.DB;
import com.pkielblock.model.dao.implementation.DepartmentDaoJDBC;
import com.pkielblock.model.dao.implementation.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
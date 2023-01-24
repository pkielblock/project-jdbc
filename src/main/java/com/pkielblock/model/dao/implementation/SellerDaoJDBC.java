package com.pkielblock.model.dao.implementation;

import com.pkielblock.db.DbException;
import com.pkielblock.model.dao.SellerDao;
import com.pkielblock.model.entities.Department;
import com.pkielblock.model.entities.Seller;
import com.pkielblock.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT seller.*,department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.Id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Department department = instatiateDepartment(rs);
                return instatiateSeller(rs, department);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }

    private Seller instatiateSeller(ResultSet rs, Department department) throws SQLException {
        return new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), department);
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT seller.*,department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id ORDER BY Name");
            rs = stmt.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instatiateSeller(rs, dep);

                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT seller.*,department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE DepartmentId = ? ORDER BY Name");
            stmt.setInt(1, department.getId());
            rs = stmt.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instatiateSeller(rs, dep);

                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }
}
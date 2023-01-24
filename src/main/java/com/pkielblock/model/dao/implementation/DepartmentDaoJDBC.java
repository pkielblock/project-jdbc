package com.pkielblock.model.dao.implementation;

import com.pkielblock.db.DB;
import com.pkielblock.db.DbException;
import com.pkielblock.db.DbIntegrityException;
import com.pkielblock.model.dao.DepartmentDao;
import com.pkielblock.model.entities.Department;
import com.pkielblock.model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(Department obj) {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, obj.getName());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected Error: No Rows Affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
            stmt.setString(1, obj.getName());
            stmt.setInt(2, obj.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Done, Deleted ID = " + id);
            } else {
                throw new DbException("Unexpected Error: No Rows Affected");
            }
        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return instatiateDepartment(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("Id"), rs.getString("Name"));
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM department ORDER BY Id");
            rs = stmt.executeQuery();

            List<Department> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("Id"));

                if (dep == null) {
                    dep = instatiateDepartment(rs);
                    map.put(rs.getInt("Id"), dep);
                }

                list.add(dep);
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

package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    @Override
    public void insert(Department obj) {

        Connection conn = null;
        PreparedStatement st = null;

        try{
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    "INSERT INTO department(Id, Name)" +
                            "VALUES " +
                            "(?, ?)"
            );

            st.setInt(1, obj.getId());
            st.setString(2, obj.getName());
            st.executeUpdate();

            conn.commit();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            conn = DB.getConnection();
            st = conn.prepareStatement(
                    "SELECT * FROM department WHERE Id = ?"
            );

            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()){
                return new Department(rs.getInt("Id"), rs.getString("Name"));
            }
            return null;
        }
        catch (SQLException e){
             throw new DbException(e.getMessage());
        }
        finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }
}

package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
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
        Connection conn = null;
        PreparedStatement st = null;

        try{
            conn = DB.getConnection();
            st = conn.prepareStatement(
                    "UPDATE department " +
                            "SET Name = ? " +
                            "WHERE Id = ?"
            );

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            st.executeUpdate();
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
        DB.closeStatement(st);
        DB.closeConnection();
        }

    }

    @Override
    public void deleteById(Integer id) {
        Connection conn = null;
        PreparedStatement st = null;

        try{
            conn = DB.getConnection();
            st = conn.prepareStatement(
                    "DELETE FROM department WHERE Id = ?"
            );

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeConnection();
        }

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
                return instanceDepartment(rs);
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

        List<Department> list = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try{
            conn = DB.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM department");

            while(rs.next()){
                Department department = instanceDepartment(rs);
                list.add(department);
            }
            return list;
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
        public Department instanceDepartment(ResultSet rs) throws SQLException{
            return new Department(rs.getInt("Id"), rs.getString("Name"));
        }
}

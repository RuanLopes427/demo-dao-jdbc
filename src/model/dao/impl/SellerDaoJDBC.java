package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    @Override
    public void insert(Seller obj) {
        Connection conn = null;
        PreparedStatement st = null;

        try{
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    "INSERT INTO seller(Id, Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                            "VALUES " +
                            "(?, ?, ?, ?, ?, ?)"
            );

            st.setInt(1, obj.getId());
            st.setString(2, obj.getName());
            st.setString(3, obj.getEmail());

            Date birthDate = Date.valueOf(obj.getBirthDate());
            st.setDate(4, birthDate);

            st.setDouble(5, obj.getBaseSalary());
            st.setInt(6, obj.getDepartment().getId());
            st.executeUpdate();

            conn.commit();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }


    @Override
    public Seller findById(Integer id) {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();

            st = conn.prepareStatement(
                    "SELECT s.*, d.Name AS DepName " +
                            "FROM seller s " +
                            "INNER JOIN department d " +
                            "ON s.DepartmentId = d.Id " +
                            "WHERE s.Id = ?"
            );

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {

                Integer sellerId = rs.getInt("Id");
                String name = rs.getString("Name");
                String email = rs.getString("Email");

                java.sql.Date sqlDate = rs.getDate("BirthDate");
                LocalDate birthDate = (sqlDate != null)
                        ? sqlDate.toLocalDate()
                        : null;

                Double baseSalary = rs.getDouble("BaseSalary");
                Integer departmentId = rs.getInt("DepartmentId");
                String depName = rs.getString("DepName");

                return new Seller(
                        sellerId,
                        name,
                        email,
                        birthDate,
                        baseSalary,
                        new Department(departmentId, depName)
                );
            }

            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }


    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}

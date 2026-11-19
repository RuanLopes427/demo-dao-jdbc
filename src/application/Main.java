package application;

import db.DbIntegrityException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        // ---------- Consultas (somente leitura) ----------

        System.out.println("=== TEST 1: seller findById ===");
        System.out.println(sellerDao.findById(3));

        System.out.println("\n=== TEST 2: seller findByDepartment ===");
        sellerDao.findByDepartment(new Department(2, null)).forEach(System.out::println);

        System.out.println("\n=== TEST 3: seller findAll ===");
        sellerDao.findAll().forEach(System.out::println);

        System.out.println("\n=== TEST 4: department findById ===");
        System.out.println(departmentDao.findById(1));

        System.out.println("\n=== TEST 5: department findAll ===");
        departmentDao.findAll().forEach(System.out::println);

        // ---------- Escrita (com dados temporários, apagados no final) ----------

        System.out.println("\n=== TEST 6: department insert ===");
        Department newDepartment = new Department(null, "Music");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id = " + newDepartment.getId());

        System.out.println("\n=== TEST 7: department update ===");
        newDepartment.setName("Games");
        departmentDao.update(newDepartment);
        System.out.println("Updated: " + departmentDao.findById(newDepartment.getId()));

        System.out.println("\n=== TEST 8: seller insert ===");
        Seller newSeller = new Seller(null, "Greg Yellow", "greg@gmail.com",
                LocalDate.of(1995, 6, 15), 4000.0, newDepartment);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("\n=== TEST 9: seller update ===");
        newSeller.setName("Greg White");
        newSeller.setBaseSalary(4500.0);
        sellerDao.update(newSeller);
        System.out.println("Updated: " + sellerDao.findById(newSeller.getId()));

        System.out.println("\n=== TEST 10: department deleteById with sellers (integrity check) ===");
        try {
            departmentDao.deleteById(newDepartment.getId());
        }
        catch (DbIntegrityException e) {
            System.out.println("Not deleted, the department still has sellers: " + e.getMessage());
        }

        System.out.println("\n=== TEST 11: seller deleteById ===");
        sellerDao.deleteById(newSeller.getId());
        System.out.println("Deleted! findById now returns: " + sellerDao.findById(newSeller.getId()));

        System.out.println("\n=== TEST 12: department deleteById ===");
        departmentDao.deleteById(newDepartment.getId());
        System.out.println("Deleted! findById now returns: " + departmentDao.findById(newDepartment.getId()));
    }
}

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public static void main(String[] args) {

    DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
    SellerDao sellerDao = DaoFactory.createSellerDao();

    Department Automotive = new Department(5, "Automotive");

    departmentDao.insert(Automotive);

}

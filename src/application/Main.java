import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public static void main(String[] args) {

    DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
    SellerDao sellerDao = DaoFactory.createSellerDao();

    System.out.println(departmentDao.findAll());
    System.out.println();
    System.out.println(sellerDao.findAll());
    System.out.println();
    System.out.println(sellerDao.findById(1));
}

import db.DB;
import model.entities.Department;
import model.entities.Seller;


public static void main(String[] args) {
    Department department = new Department(1, "Logistical");
    Seller seller = new Seller(1, "Ruan Lopes", "ruanestevao@gmail.com", LocalDate.now(), 2999.99, department);

    System.out.println(seller);
}

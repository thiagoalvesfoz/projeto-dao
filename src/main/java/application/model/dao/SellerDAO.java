package application.model.dao;

import application.model.entities.Department;
import application.model.entities.Seller;

import java.util.List;

public interface SellerDAO extends CRUD<Seller, Integer> {

    List<Seller> findByDepartment(Department department);
}
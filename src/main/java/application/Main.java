package application;

import application.model.dao.impl.DaoFactory;
import application.model.dao.SellerDAO;
import application.model.entities.Seller;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        SellerDAO sellerDao = DaoFactory.createSellerDao();

        System.out.println("##### BUSCA DE SELLER POR ID");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n##### BUSCA DE SELLER POR DEPARTMENT");
        List<Seller> sellerList = sellerDao.findByDepartment(seller.getDepartment());;
        sellerList.forEach(System.out::println);

        System.out.println("\n##### BUSCA TODOS OS SELLERS");
        sellerList.clear();
        sellerList = sellerDao.findAll();
        sellerList.forEach(System.out::println);

    }

}

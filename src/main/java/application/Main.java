package application;

import application.model.dao.impl.DaoFactory;
import application.model.dao.SellerDAO;
import application.model.entities.Seller;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        SellerDAO sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        List<Seller> sellerList = sellerDao.findByDepartment(seller.getDepartment());;

        sellerList.forEach(System.out::println);

    }

}

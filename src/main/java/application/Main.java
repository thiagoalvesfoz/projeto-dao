package application;

import application.model.dao.SellerDAO;
import application.model.dao.impl.DaoFactory;
import application.model.entities.Seller;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        SellerDAO sellerDao = DaoFactory.createSellerDao();

        System.out.println("##### BUSCA DE SELLER POR ID");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n##### BUSCA DE SELLER POR DEPARTMENT");
        List<Seller> sellerList = sellerDao.findByDepartment(seller.getDepartment());
        ;
        sellerList.forEach(System.out::println);

        System.out.println("\n##### BUSCA TODOS OS SELLERS");
        sellerList.clear();
        sellerList = sellerDao.findAll();
        sellerList.forEach(System.out::println);

        System.out.println("\n##### ADICIONAR SELLER");
        Seller sellerToSave = new Seller(
                null,
                "Karem Alves",
                "karem@karem.com",
                2500.00,
                LocalDate.of(1994, 7, 10),
                null
        );

        System.out.println("Antes de salvar: " + sellerToSave);
        System.out.println("Depois de salvo: " + sellerDao.insert(sellerToSave));
    }

}

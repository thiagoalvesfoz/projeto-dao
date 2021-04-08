package application;

import application.model.dao.DepartmentDAO;
import application.model.dao.SellerDAO;
import application.model.dao.impl.DaoFactory;
import application.model.entities.Department;
import application.model.entities.Seller;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        executeDaoImplementationSellers();
        executeDaoImplementationsDepartments();
    }

    public static void executeDaoImplementationSellers() {
        SellerDAO sellerDao = DaoFactory.createSellerDao();

        System.out.println("##### BUSCA DE SELLER POR ID");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n##### BUSCA DE SELLER POR DEPARTMENT");
        List<Seller> sellerList = sellerDao.findByDepartment(seller.getDepartment());
        sellerList.forEach(System.out::println);

        System.out.println("\n##### BUSCA TODOS OS SELLERS");
        sellerList.clear();
        sellerList = sellerDao.findAll();
        sellerList.forEach(System.out::println);

        System.out.println("\n##### ADICIONAR SELLER ");
        Seller sellerToSave = new Seller(
                null,
                "Thiago Alves",
                "thiago@thiago.com",
                2500.00,
                LocalDate.of(1997, 7, 13),
                null
        );

        System.out.println("Antes de salvar: " + sellerToSave);
        System.out.println("Depois de salvo: " + sellerDao.save(sellerToSave));

        System.out.println("\n##### ATUALIZAR SELLER");
        sellerToSave.setDepartment(new Department(1, null));
        sellerDao.save(sellerToSave);
        System.out.println("Seller atualizado para: " + sellerDao.findById(sellerToSave.getId()));

        System.out.println("\n##### DELETAR SELLER");
        sellerDao.deleteById(sellerToSave.getId());
        System.out.println("Lista de sellers após deletar");
        sellerList = sellerDao.findAll();
        sellerList.forEach(System.out::println);
    }

    public static void executeDaoImplementationsDepartments() {
        DepartmentDAO departmentDAO = DaoFactory.createDepartmentDao();


        System.out.println("##### BUSCA DE DEPARTMENT POR ID");
        System.out.println(departmentDAO.findById(1));

        System.out.println("\n##### BUSCA TODOS OS DEPARTMENTs");
        departmentDAO.findAll().forEach(System.out::println);

        System.out.println("\n##### INSERIR DEPARTMENT");
        Department department = new Department(null, "Calçados");
        System.out.println("Antes de salvar: " + department);
        System.out.println("Depois de salvo: " + departmentDAO.save(department));

        System.out.println("\n##### ATUALIZAR DEPARTMENT");
        department.setName("footwear");
        System.out.println("Depois de atualizar: " + departmentDAO.update(department));

        System.out.println("\n##### DELETAR DEPARTMENT");
        departmentDAO.deleteById(department.getId());
        System.out.println("Lista de departments após deletar");
        departmentDAO.findAll().forEach(System.out::println);
    }

}

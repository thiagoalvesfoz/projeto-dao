package application.model.dao.impl;

import infra.Database;

public class DaoFactory {

    public static SellerDaoJDBC createSellerDao() {
        return new SellerDaoJDBC(Database.getConnection());
    }

}

package application.model.dao.impl;

import application.model.dao.SellerDAO;
import application.model.entities.Department;
import application.model.entities.Seller;
import infra.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

class SellerDaoJDBC implements SellerDAO {

    private final Connection conn;

    SellerDaoJDBC(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void insert(Seller entity) {

    }

    @Override
    public void update(Seller entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT seller.*, department.name as depName FROM seller " +
                "INNER JOIN department ON department.id = seller.department_id WHERE seller.id = ?;";

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return getSeller(rs, getDepartment(rs));
            }

            return null;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            Database.closeStatement(st);
            Database.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT seller.*, department.name as depName FROM seller " +
                "INNER JOIN department ON department.id = seller.department_id " +
                "WHERE seller.department_id = ? ORDER BY name;";

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> listOfSellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("department_id"));

                if (dep == null) {
                    dep = getDepartment(rs);
                }

                listOfSellers.add(getSeller(rs, dep));
            }

            return listOfSellers;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            Database.closeStatement(st);
            Database.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        ResultSet rs = null;

        String sql = "SELECT seller.*, department.name as depName FROM seller " +
                "LEFT JOIN department ON department.id = seller.department_id " +
                "ORDER BY seller.name;";

        try {
            rs = conn.prepareStatement(sql).executeQuery();

            List<Seller> listAllSellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("department_id"));

                if (dep == null) {
                    dep = getDepartment(rs);
                }

                listAllSellers.add(getSeller(rs, dep));
            }

            return listAllSellers;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            Database.closeResultSet(rs);
        }
    }

    private Seller getSeller(ResultSet rs, Department department) throws SQLException {
        return new Seller(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getDouble("base_salary"),
                LocalDate.parse(rs.getString("birth_date"), ofPattern("yyyy-MM-dd")),
                department
        );
    }

    private Department getDepartment(ResultSet rs) throws SQLException {

        int idDepartment = rs.getInt("department_id");

        if (idDepartment != 0) {
            return new Department(
                    idDepartment,
                    rs.getString("depName")
            );
        }

        return null;
    }
}

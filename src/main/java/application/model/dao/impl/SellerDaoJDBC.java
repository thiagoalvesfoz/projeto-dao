package application.model.dao.impl;

import application.model.dao.SellerDAO;
import application.model.entities.Department;
import application.model.entities.Seller;
import infra.Database;

import java.sql.*;
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
    public Seller insert(Seller entity) {
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "INSERT INTO seller (name, email, birth_date, base_salary, department_id) " +
                "VALUES (?, ?, ?, ?, ?);";

        try {
            st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, entity.getName());
            st.setString(2, entity.getEmail());
            st.setString(3, entity.getBirthDate().format(ofPattern("yyyy-MM-dd")));
            st.setDouble(4, entity.getBaseSalary());

            if (entity.getDepartment() != null) {
                st.setInt(5, entity.getDepartment().getId());
            } else {
                st.setNull(5, Types.NULL);
            }

            int rowsAffected = st.executeUpdate();
            rs = st.getGeneratedKeys();

            if (rowsAffected != 0 && rs.next()) {

                entity.setId(rs.getInt(1));
                return entity;

            } else {
                throw new DaoException("Unexpected Error! No rows affected!");
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            Database.closeStatement(st);
            Database.closeResultSet(rs);
        }
    }

    @Override
    public Seller update(Seller entity) {
        PreparedStatement st = null;

        String sql = "UPDATE seller SET name = ?, email = ?, birth_date = ?, base_salary = ?, department_id = ? " +
                "WHERE id = ?";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(6, entity.getId());
            st.setString(1, entity.getName());
            st.setString(2, entity.getEmail());
            st.setString(3, entity.getBirthDate().format(ofPattern("yyyy-MM-dd")));
            st.setDouble(4, entity.getBaseSalary());

            if (entity.getDepartment() != null) {
                st.setInt(5, entity.getDepartment().getId());
            } else {
                st.setNull(5, Types.NULL);
            }

            if (st.executeUpdate() != 0) {
                return entity;
            }

            throw new DaoException("No rows affected!");

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        String sql = "DELETE FROM seller WHERE id = ?;";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DaoException("No rows affected!");
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            Database.closeStatement(st);
        }
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

package application.model.dao.impl;

import application.model.dao.DepartmentDAO;
import application.model.entities.Department;
import infra.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DepartmentDaoJDBC implements DepartmentDAO {

    private final Connection conn;

    DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Department save(Department entity) {

        if (entity.getId() != null) {
            return this.update(entity);
        }

        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "INSERT INTO department (name) VALUES (?);";

        try {
            st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, entity.getName());

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
    public Department update(Department entity) {
        PreparedStatement st = null;

        String sql = "UPDATE department SET name = ? WHERE id = ?;";

        try {
            st = conn.prepareStatement(sql);

            st.setString(1, entity.getName());
            st.setInt(2, entity.getId());

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
        String sql = "DELETE FROM department WHERE id = ?;";
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
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM department WHERE id = ?;";

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return getDepartment(rs);
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
    public List<Department> findAll() {
        ResultSet rs = null;

        String sql = "SELECT * FROM department;";

        try {
            rs = conn.prepareStatement(sql).executeQuery();

            List<Department> listAllDepartment = new ArrayList<>();

            while (rs.next()) {
                listAllDepartment.add(getDepartment(rs));
            }

            return listAllDepartment;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            Database.closeResultSet(rs);
        }
    }

    private Department getDepartment(ResultSet rs) throws SQLException {

        int idDepartment = rs.getInt("id");

        if (idDepartment != 0) {
            return new Department(
                    idDepartment,
                    rs.getString("name")
            );
        }

        return null;
    }

}

package potato.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import potato.models.Tea;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeaSQL implements TeaDAO {
    private static final String URL = "jdbc:postgresql://localhost:5430/postgres_db";
    private static final String USER = "postgres_user";
    private static final String PASSWORD = "postgres_password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public List<Tea> getAllTeas() throws SQLException {
        List<Tea> teas = new ArrayList<>();
        String sql = "SELECT * FROM teas";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                teas.add(mapRowToTea(rs));
            }
        }
        return teas;
    }

    @Override
    public Tea getTeaById(int id) throws SQLException {
        String sql = "SELECT * FROM teas WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRowToTea(rs) : null;
            }
        }
    }

    @Override
    public void addTea(Tea tea) throws SQLException {
        String sql = "INSERT INTO teas (name, type_id, description, country_of_origin, flavor_profile, image_url) " +
                "VALUES (?, ?, ?, ?, ?::jsonb, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setTeaParameters(stmt, tea);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tea.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void updateTea(Tea tea) throws SQLException {
        String sql = "UPDATE teas SET name = ?, type_id = ?, description = ?, " +
                "country_of_origin = ?, flavor_profile = ?::jsonb, image_url = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setTeaParameters(stmt, tea);
            stmt.setInt(7, tea.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteTea(int id) throws SQLException {
        String sql = "DELETE FROM teas WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Tea mapRowToTea(ResultSet rs) throws SQLException {
        Tea tea = new Tea();
        tea.setId(rs.getInt("id"));
        tea.setName(rs.getString("name"));
        tea.setType(rs.getString("type"));
        tea.setDescription(rs.getString("description"));

        try {
            String flavorJson = rs.getString("flavor_profile");
            tea.setFlavorProfileFromString(flavorJson != null ? flavorJson : "{}");
        } catch (JsonProcessingException e) {
            throw new SQLException("Error parsing flavor profile JSON", e);
        }

        tea.setQuantity(rs.getInt("quantity"));
        return tea;
    }

    private void setTeaParameters(PreparedStatement stmt, Tea tea) throws SQLException {
        stmt.setString(1, tea.getName());
        stmt.setString(2, tea.getType());
        stmt.setString(3, tea.getDescription());
        stmt.setString(5, tea.getFlavorProfileAsString()); // Используем строковое представление
        stmt.setInt(6, tea.getQuantity());
    }
}
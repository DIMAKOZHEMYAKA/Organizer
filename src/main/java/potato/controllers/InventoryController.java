package potato.controllers;


import potato.Database;
import potato.models.InventoryItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryController {
    public List<InventoryItem> getInventoryItems() throws SQLException {
        List<InventoryItem> items = new ArrayList<>();
        String sql = "SELECT tea_id, quantity_in_grams, expiration_date FROM tea_inventory";
        Statement stmt = Database.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            items.add(new InventoryItem(
                    rs.getInt("tea_id"),
                    rs.getDouble("quantity_in_grams"),
                    rs.getDate("expiration_date").toLocalDate()
            ));
        }
        return items;
    }

    public void checkInventoryAndNotify() throws SQLException {
        for (InventoryItem item : getInventoryItems()) {
            if (item.isLowStock(50)) {
                System.out.println("Пополните запас чая ID=" + item.getTeaId());
            }
            if (item.isExpired()) {
                System.out.println("Чай ID=" + item.getTeaId() + " просрочен.");
            }
        }
    }
}
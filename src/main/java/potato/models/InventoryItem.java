package potato.models;

import java.time.LocalDate;

public class InventoryItem {
    private int teaId;
    private double quantityInGrams;
    private LocalDate expirationDate;

    public InventoryItem(int teaId, double quantityInGrams, LocalDate expirationDate) {
        this.teaId = teaId;
        this.quantityInGrams = quantityInGrams;
        this.expirationDate = expirationDate;
    }

    public boolean isLowStock(double threshold) {
        return quantityInGrams < threshold;
    }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }

    public int getTeaId() {
        return teaId;
    }

    public double getQuantityInGrams() {
        return quantityInGrams;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
package potato.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Класс {@code Tea} представляет модель данных для чая.
 * <p>
 * Содержит информацию о названии, типе, описании, вкусовом профиле и количестве.
 * Аннотация {@code @JsonIgnoreProperties} позволяет игнорировать неизвестные JSON-поля при десериализации.
 * </p>
 *
 * @author DIMAKOZHEMYAKA
 * @version 1.0
 * @since 2024
 * @see <a href="https://github.com/FasterXML/jackson">Jackson JSON Processor</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tea {

    /** Уникальный идентификатор чая. */
    private int id;

    /** Название чая. */
    private String name;

    /** Тип чая (например, чёрный, зелёный, улун). */
    private String type;

    /** Описание чая. */
    private String description;

    /** Вкусовой профиль чая. */
    private String flavor_profile;

    /** Доступное количество чая. */
    private int quantity;

    public Tea(int id, String name, String type, String description, String flavor_profile, int quantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.flavor_profile = flavor_profile;
        this.quantity = quantity;
    }

    public Tea(){ }
    /**
     * Возвращает уникальный идентификатор чая.
     *
     * @return идентификатор чая
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор чая.
     *
     * @param id идентификатор чая
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает название чая.
     *
     * @return название чая
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название чая.
     *
     * @param name название чая
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает тип чая.
     *
     * @return тип чая (например, "чёрный", "зелёный", "улун")
     */
    public String getType() {
        return type;
    }

    /**
     * Устанавливает тип чая.
     *
     * @param type тип чая
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Возвращает описание чая.
     *
     * @return описание чая
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает описание чая.
     *
     * @param description описание чая
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Возвращает доступное количество чая.
     *
     * @return количество чая
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Устанавливает доступное количество чая.
     *
     * @param quantity количество чая
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Возвращает вкусовой профиль чая.
     *
     * @return вкусовой профиль
     */
    public String getFlavor_profile() {
        return flavor_profile;
    }

    /**
     * Устанавливает вкусовой профиль чая.
     *
     * @param flavor_profile вкусовой профиль
     */
    public void setFlavor_profile(String flavor_profile) {
        this.flavor_profile = flavor_profile;
    }

    /**
     * Возвращает строковое представление объекта в виде названия чая.
     *
     * @return название чая
     */
    @Override
    public String toString() {
        return name;
    }
}
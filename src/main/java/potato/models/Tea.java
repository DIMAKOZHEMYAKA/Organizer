package potato.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Tea {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private int id;
    private String name;
    @JsonProperty("type_id")
    private int typeId;
    private String description;
    @JsonProperty("country_of_origin")
    private String countryOfOrigin;
    @JsonProperty("flavor_profile")
    private JsonNode flavorProfile;
    @JsonProperty("image_url")
    private String imageUrl;

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getTypeId() { return typeId; }
    public void setTypeId(int typeId) { this.typeId = typeId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCountryOfOrigin() { return countryOfOrigin; }
    public void setCountryOfOrigin(String countryOfOrigin) { this.countryOfOrigin = countryOfOrigin; }

    // Парсер для JsonNode
    public JsonNode getFlavorProfile() {
        return flavorProfile;
    }

    public void setFlavorProfile(JsonNode flavorProfile) {
        this.flavorProfile = flavorProfile;
    }
    public String getFlavorProfileAsString() {
        try {
            return flavorProfile != null ? objectMapper.writeValueAsString(flavorProfile) : "{}";
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public void setFlavorProfileFromString(String json) throws JsonProcessingException {
        this.flavorProfile = objectMapper.readTree(json);
    }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
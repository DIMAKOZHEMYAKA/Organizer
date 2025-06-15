package potato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import potato.models.Tea;

import static org.junit.jupiter.api.Assertions.*;

class TeaTest {

    private Tea tea;

    @BeforeEach
    void setUp() {
        tea = new Tea(1, "Assam", "Чёрный", "Крепкий индийский чай", "Мальт, насыщенность", 100);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1, tea.getId());
        assertEquals("Assam", tea.getName());
        assertEquals("Чёрный", tea.getType());
        assertEquals("Крепкий индийский чай", tea.getDescription());
        assertEquals("Мальт, насыщенность", tea.getFlavor_profile());
        assertEquals(100, tea.getQuantity());
    }

    @Test
    void testSetters() {
        tea.setId(2);
        tea.setName("Sencha");
        tea.setType("Зелёный");
        tea.setDescription("Японский зелёный чай");
        tea.setFlavor_profile("Трава, свежесть");
        tea.setQuantity(50);

        assertEquals(2, tea.getId());
        assertEquals("Sencha", tea.getName());
        assertEquals("Зелёный", tea.getType());
        assertEquals("Японский зелёный чай", tea.getDescription());
        assertEquals("Трава, свежесть", tea.getFlavor_profile());
        assertEquals(50, tea.getQuantity());
    }

    @Test
    void testToString() {
        assertEquals("Assam", tea.toString());
    }
}

package seedu.address.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Product(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Muffin,Cake"));
        assertThrows(IllegalArgumentException.class, () -> new Product("Muffin:3"));
        assertThrows(IllegalArgumentException.class, () -> new Product(""));
    }

    @Test
    public void isValidProductName() {
        assertFalse(Product.isValidProductName("")); // empty
        assertFalse(Product.isValidProductName("  ")); // blank
        assertFalse(Product.isValidProductName("Muffin,Cake")); // contains comma
        assertFalse(Product.isValidProductName("Muffin:3")); // contains colon
        assertTrue(Product.isValidProductName("Muffin"));
        assertTrue(Product.isValidProductName("Red Velvet Cake"));
        assertTrue(Product.isValidProductName("  Muffin  ")); // trimmed
    }

    @Test
    public void isSameProduct_caseInsensitive() {
        Product p = new Product("Muffin");
        assertTrue(p.isSameProduct(new Product("muffin")));
        assertTrue(p.isSameProduct(new Product("MUFFIN")));
        assertFalse(p.isSameProduct(new Product("Cookie")));
    }

    @Test
    public void equals_caseInsensitive() {
        assertEquals(new Product("Muffin"), new Product("muffin"));
    }

    @Test
    public void getName_normalizesSpaces() {
        assertEquals("Chocolate Cake", new Product("Chocolate  Cake").getName());
    }
}

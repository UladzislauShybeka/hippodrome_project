import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {

    @Test
    public void constructorShouldThrowExceptionWhenNameIsNull() {
       IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null,10.0, 5.0));
        assertEquals("Name cannot be null", e.getMessage() );
}

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void constructorShouldThrowExceptionWhenNameIsBlank(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, 10.0, 5.0);
        });

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void constructorShouldThrowExceptionWhenSpeedIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", -10.0, 5.0);
        });

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void constructorShouldThrowExceptionWhenDistanceIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", 10.0, -5.0);
        });

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }
    @Test
    public void testGetName() {
        Horse horse = new Horse("Name", 10.0, 5.0);
        assertEquals("Name", horse.getName());
    }

    @Test
    public void testGetSpeed() {
        Horse horse = new Horse("Name", 10.0, 5.0);
        assertEquals(10.0, horse.getSpeed());
    }

    @Test
    public void testGetDistance() {
        Horse horse = new Horse("Name", 10.0, 5.0);
        assertEquals(5.0, horse.getDistance());
    }

    @Test
    public void testGetDistanceWithTwoParamConstructor() {
        Horse horse = new Horse("Name", 10.0);
        assertEquals(0.0, horse.getDistance());
    }

    @Test
    public void testMove() {
        try (MockedStatic<Horse> mocked = Mockito.mockStatic(Horse.class)) {
            mocked.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);

            Horse horse = new Horse("Name", 10.0);
            horse.move();

            double expectedDistance = 10.0 * 0.5;
            assertEquals(expectedDistance, horse.getDistance());

            mocked.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
}

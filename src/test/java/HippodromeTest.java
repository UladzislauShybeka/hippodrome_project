import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    @Test
    public void constructorShouldThrowExceptionWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 10.0, 5.0);
        });

        assertEquals("Name cannot be null.", exception.getMessage());
    }
    @Test
    public void constructorShouldThrowExceptionWhenListIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });

        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorsesShouldReturnSameListAsPassedInConstructor() {
        List<Horse> horses = IntStream.range(0, 30)
                .mapToObj(i -> new Horse("Horse" + i, 10.0))
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(horses);

        Assertions.assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void moveShouldCallMoveOnAllHorses() {
        List<Horse> mockedHorses = IntStream.range(0, 50)
                .mapToObj(i -> mock(Horse.class))
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(mockedHorses);
        hippodrome.move();

        mockedHorses.forEach(horse -> verify(horse, times(1)).move());
    }

    @Test
    public void getWinnerShouldReturnHorseWithMaxDistance() {
        Horse horse1 = mock(Horse.class);
        Horse horse2 = mock(Horse.class);
        Horse horse3 = mock(Horse.class);

        when(horse1.getDistance()).thenReturn(1.0);
        when(horse2.getDistance()).thenReturn(3.0);
        when(horse3.getDistance()).thenReturn(2.0);

        Hippodrome hippodrome = new Hippodrome(Arrays.asList(horse1, horse2, horse3));

        Assertions.assertEquals(horse2, hippodrome.getWinner());
    }
}

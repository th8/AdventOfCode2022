package nl.th8.adventofcode2022;

import nl.th8.adventofcode2022.utils.PuzzleInputParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DayOneTest {

    @Mock
    private PuzzleInputParser puzzleInputParser;

    @InjectMocks
    private DayOne dayOne;

    @BeforeEach
    void setUp() {
        when(puzzleInputParser.getInputAsIntegerListWithNullSkips()).thenReturn(Arrays.asList(1, null, 3, null, 5, 2, null, 1));
    }

    @Test
    void solvePartOne() {
        assertEquals(7, dayOne.solvePartOne());
    }

    @Test
    void solvePart2() {
        assertEquals(11, dayOne.solvePart2());
    }
}
package nl.th8.adventofcode2022;

import nl.th8.adventofcode2022.utils.PuzzleInputParser;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayOne {
    private static final int PART_TWO_ELVES = 3;

    private final PuzzleInputParser puzzleInputParser;

    //For actual use
    public DayOne() {
        puzzleInputParser = new PuzzleInputParser(Path.of("src", "main", "resources", "input", "1.txt"));
    }

    //For Unittesting
    public DayOne(PuzzleInputParser puzzleInputParser) {
        this.puzzleInputParser = puzzleInputParser;
    }

    /**
     * To solve part one we simply print the first value from our sorted foodmap.
     */
    public int solvePartOne() {
        var highestFoodEntry = processFoodMap().get(0);
        System.out.printf("Elf %d has the highest amount of calories, which is: %d%n", highestFoodEntry.getKey(), highestFoodEntry.getValue());
        return highestFoodEntry.getValue();
    }

    /**
     * To solve part 2 of day one we simply need to sum the first three items from our sorted foodmap.
     */
    public int solvePart2() {
        int topXCalories = processFoodMap().stream().mapToInt(Map.Entry::getValue).limit(PART_TWO_ELVES).sum();
        System.out.printf("The top %d elves are carrying %d calories of food%n", PART_TWO_ELVES, topXCalories);
        return topXCalories;
    }


    /**
     * Takes day one input, maps calories to elves, using nulls as an indicator to move on to the next elf.
     * After creating the map, this function sorts it by calorie count from highest to lowest.
     *
     * @return a list of elf - calorie pairings sorted from highest to lowest amount of calories.
     */
    private List<Map.Entry<Integer, Integer>> processFoodMap() {

        var input = puzzleInputParser.getInputAsIntegerListWithNullSkips();

        Map<Integer, Integer> elfFoodMap = new HashMap<>();
        Integer elf = 1;

        for (Integer foodItem : input) {
            if (foodItem != null && elfFoodMap.containsKey(elf)) {
                elfFoodMap.replace(elf, elfFoodMap.get(elf) + foodItem);
            } else if (foodItem != null) {
                elfFoodMap.put(elf, foodItem);
            } else {
                elf++;
            }
        }

        return elfFoodMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).toList();
    }
}

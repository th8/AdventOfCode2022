package nl.th8.adventofcode2022;

import nl.th8.adventofcode2022.utils.PuzzleInputParser;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DayThree implements Day {

    private final PuzzleInputParser puzzleInputParser;

    //For actual use
    public DayThree() {
        puzzleInputParser = new PuzzleInputParser(Path.of("src", "main", "resources", "input", "3.txt"));
    }


    /**
     * To find duplicates items in the two compartments of each backpack we split the contents in half.
     * We convert the second compartment to a set for O(1) instead of O(n) for String::charAt or String::contains lookups for each character in compartment one.
     * Finally we encode it with our a1Z52 cypher
     *
     * @return sum of all a1Z52 encoded duplicated
     */
    public int solvePartOne() {
        var inputList = puzzleInputParser.getInputAsStringList();

        var result = inputList.stream().mapToInt(rucksack -> {
            var compartmentOne = rucksack.substring(0, rucksack.length() / 2);
            var compartmentTwo = rucksack.substring(rucksack.length() / 2);

            var compartmentTwoChars = compartmentTwo.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());

            return compartmentOne.chars().mapToObj(c -> (char) c)
                    .filter(compartmentTwoChars::contains)
                    .mapToInt(this::asciiToa1Z52)
                    .findAny().orElseThrow();
        }).sum();

        System.out.printf("The sum of priorities for all duplicated items is %d%n", result);
        return result;
    }

    /**
     * To find the common item between each group of three rucksacks we divide our list into sublists of 3.
     * For each submap we again use Sets to reduce finding duplicate chars in the strings to O(1).
     * Finally, we encode each badge we found per sublist to our a1Z52 cypher.
     *
     * @return the sum of all a1Z52 encoded badges.
     */
    public int solvePartTwo() {
        var inputList = puzzleInputParser.getInputAsStringList();

        AtomicInteger rucksackIndex = new AtomicInteger(0);
        var result = inputList.stream()
                .collect(Collectors.groupingBy(item -> rucksackIndex.getAndIncrement() /3))
                .values().stream()
                .mapToInt(rucksackGroup -> {
                    var rucksackOne = rucksackGroup.get(0);
                    var rucksackTwoChars = rucksackGroup.get(1).chars().mapToObj(c -> (char)c).collect(Collectors.toSet());
                    var rucksackThreeChars = rucksackGroup.get(2).chars().mapToObj(c -> (char)c).collect(Collectors.toSet());

                    return rucksackOne.chars().mapToObj(c -> (char)c)
                            .filter(c -> rucksackTwoChars.contains(c) && rucksackThreeChars.contains(c))
                            .mapToInt(this::asciiToa1Z52)
                            .findAny().orElseThrow();
                }).sum();

        System.out.printf("The sum of priorities for all badges is %d%n", result);
        return result;
    }

    public int getDayNumber() {
        return 3;
    }

    /**
     * Encode chars to a a1z26 A27Z52 cypher, as these are the priorities specified in the puzzle.
     * We just substract a magic number from the characters ascii code because that's easier than making to maps for our cypher.
     *
     * @param letter ascii character to encode.
     * @return Encoded input value (integer between 1 and 52)
     */
    private int asciiToa1Z52(char letter) {
        int asciiCode = letter;

        //Uppercase to 27 - 52
        if(asciiCode >= 65 && asciiCode <= 90)
            return asciiCode - 38;
        //Lowercased to 1 - 26
        else if(asciiCode >= 97 && asciiCode <= 122)
            return asciiCode - 96;
        else
            throw new IllegalArgumentException("Invalid ascii character was passed. Bye.");
    }
}

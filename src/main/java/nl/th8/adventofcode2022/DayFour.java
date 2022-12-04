package nl.th8.adventofcode2022;

import nl.th8.adventofcode2022.utils.PuzzleInputParser;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class DayFour implements Day {

    private final PuzzleInputParser puzzleInputParser;

    //For actual use
    public DayFour() {
        puzzleInputParser = new PuzzleInputParser(Path.of("src", "main", "resources", "input", "4.txt"));
    }


    /**
     * To find assignments completely containing the other elf' assignment, we simply check the lower and upperbound against that of the other elf.
     * We need to do this for both elves, and delegate finding our answer to the countMatches[...]Predicates function.
     *
     * @return The amount of pairs conforming to assignment one.
     */
    public int solvePartOne() {
        var inputList = puzzleInputParser.getInputAsStringList();

        Predicate<String[]> elfOneFullyContainsElfTwo = elfSplit -> Integer.parseInt(elfSplit[2]) >= Integer.parseInt(elfSplit[0]) && Integer.parseInt(elfSplit[3]) <= Integer.parseInt(elfSplit[1]);
        Predicate<String[]> elfTwoFullyContainsElfOne = elfSplit -> Integer.parseInt(elfSplit[0]) >= Integer.parseInt(elfSplit[2]) && Integer.parseInt(elfSplit[1]) <= Integer.parseInt(elfSplit[3]);
        var result = countMatchesInStringArrayBasedOnPredicates(inputList, elfTwoFullyContainsElfOne, elfOneFullyContainsElfTwo);

        System.out.printf("The ammount of assignment pairs where one fully contains the other is: %d%n", result);
        return result;
    }

    /**
     * Much like part one, except we need to check both the lower and upper bound of each assignment
     * to see if it falls inside the other elf' assignment range.
     *
     * @return The amount of pairs conforming to assignment two.
     */
    public int solvePartTwo() {
        var inputList = puzzleInputParser.getInputAsStringList();

        Predicate<String[]> elfOneContainsAnyLowerBoundElfTwo = elfSplit -> Integer.parseInt(elfSplit[0]) >= Integer.parseInt(elfSplit[2]) && Integer.parseInt(elfSplit[0]) <= Integer.parseInt(elfSplit[3]);
        Predicate<String[]> elfOneContainsAnyUpperBoundElfTwo = elfSplit -> Integer.parseInt(elfSplit[1]) >= Integer.parseInt(elfSplit[2]) && Integer.parseInt(elfSplit[1]) <= Integer.parseInt(elfSplit[3]);
        Predicate<String[]> elfTwoContainsAnyLowerBoundElfOne = elfSplit -> Integer.parseInt(elfSplit[2]) >= Integer.parseInt(elfSplit[0]) && Integer.parseInt(elfSplit[2]) <= Integer.parseInt(elfSplit[1]);
        Predicate<String[]> elfTwoContainsAnyUpperBoundElfOne = elfSplit -> Integer.parseInt(elfSplit[3]) >= Integer.parseInt(elfSplit[0]) && Integer.parseInt(elfSplit[3]) <= Integer.parseInt(elfSplit[1]);

        var result = countMatchesInStringArrayBasedOnPredicates(inputList, elfOneContainsAnyLowerBoundElfTwo, elfOneContainsAnyUpperBoundElfTwo, elfTwoContainsAnyLowerBoundElfOne, elfTwoContainsAnyUpperBoundElfOne);

        System.out.printf("The ammount of assignment pairs where one contains any part the other is: %d%n", result);
        return result;
    }


    /**
     * To find our overlapping assignments we map our elfPairings to an array of 4 Strings containing both elves start and end zones.
     * We filter this based on a set of predicates depending on the part of the assignment and count the filtered results.
     *
     * @param inputList List of elfPairings from the input list.
     * @param predicates List of String[] predicates to filter the assignments on.
     * @return The amount of pairings conforming to the predicates.
     */
    @SafeVarargs
    private int countMatchesInStringArrayBasedOnPredicates(List<String> inputList, Predicate<String[]>... predicates) {
        return (int) inputList.stream()
                .map(elfPair -> elfPair.split("[,-]"))
                .filter(Arrays.stream(predicates).reduce(Predicate::or).orElse(predicate -> true))
                .count();
    }

    public int getDayNumber() {
        return 4;
    }
}

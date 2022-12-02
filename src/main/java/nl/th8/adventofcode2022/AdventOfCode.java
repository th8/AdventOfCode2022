package nl.th8.adventofcode2022;

import org.springframework.util.StopWatch;

import java.util.List;

public class AdventOfCode {

    private static final List<Day> days = List.of(new DayOne(), new DayTwo());

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch("Advent of Code 2022");

        for(Day day : days) {
            System.out.printf("----- Day %d -----%n", day.getDayNumber());

            stopWatch.start(String.format("Day %d.1", day.getDayNumber()));
            day.solvePartOne();
            stopWatch.stop();

            stopWatch.start(String.format("Day %d.2", day.getDayNumber()));
            day.solvePartTwo();
            stopWatch.stop();

            System.out.println("");
        }

        /* Motivation for me to optimise runtime */
        System.out.println(stopWatch.prettyPrint());
    }
}

package nl.th8.adventofcode2022;

import org.springframework.util.StopWatch;

public class AdventOfCode {


    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();

        /* Day one */
        stopWatch.start("Day 1.1");
        DayOne dayOne = new DayOne();
        dayOne.solvePartOne();
        stopWatch.stop();

        stopWatch.start("Day 1.2");
        dayOne.solvePart2();
        stopWatch.stop();

        /* Day two */
        DayTwo dayTwo = new DayTwo();
        stopWatch.start("Day 2.1");
        dayTwo.solvePart1();
        stopWatch.stop();
        stopWatch.start("Day 2.2");
        dayTwo.solvePart2();
        stopWatch.stop();




        /* Motivation for me to optimise runtime */
        System.out.println(stopWatch.prettyPrint());
    }
}

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



        /* Motivation for me to optimise runtime */
        System.out.println(stopWatch.prettyPrint());
    }
}

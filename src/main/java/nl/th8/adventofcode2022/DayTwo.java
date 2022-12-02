package nl.th8.adventofcode2022;

import nl.th8.adventofcode2022.utils.PuzzleInputParser;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;

public class DayTwo {

    private final PuzzleInputParser puzzleInputParser;

    //For actual use
    public DayTwo() {
        puzzleInputParser = new PuzzleInputParser(Path.of("src", "main", "resources", "input", "2.txt"));
    }

    //For Unittesting
    public DayTwo(PuzzleInputParser puzzleInputParser) {
        this.puzzleInputParser = puzzleInputParser;
    }

    public int solvePart1() {
        var inputList = puzzleInputParser.getInputAsStringList();

        int result = inputList.stream().mapToInt(line -> {
            var split = line.split("\\s+");

            var opponent = Throw.determineThrowFromLetter(split[0]);
            var you = Throw.determineThrowFromLetter(split[1]);

            return Throw.determineScore(you, opponent);
        }).sum();

        System.out.printf("Using the strategy guide, you score would be %d%n", result);
        return result;
    }

    public int solvePart2() {
        var inputList = puzzleInputParser.getInputAsStringList();

        int result = inputList.stream().mapToInt(line -> {
            var split = line.split("\\s+");

            var opponent = Throw.determineThrowFromLetter(split[0]);
            var you = Throw.determinePlayFromLetter(split[1], opponent);

            return Throw.determineScore(you, opponent);
        }).sum();

        System.out.printf("Using the strategy guide correctly, you score would be %d%n", result);
        return result;
    }


    private enum Throw {
        ROCK(1, 3, 2),
        PAPER(2, 1, 3),
        SCISSOR(3, 2, 1);

        private static final int DRAW_SCORE = 3;
        private static final int WIN_SCORE = 6;

        Throw(int score, int winsAgainst, int losesAgainst) {
            this.score = score;
            this.winsAgainst = winsAgainst;
            this.losesAgainst = losesAgainst;
        }

        private final int score;

        private final int winsAgainst;

        private final int losesAgainst;

        public int getScore() {
            return score;
        }

        public int getWinsAgainst() {
            return winsAgainst;
        }

        public int getLosesAgainst() {
            return losesAgainst;
        }

        public static int determineScore(Throw you, Throw opponent) {
            if(you == opponent) {
                return DRAW_SCORE + you.getScore();
            } else if (you.winsAgainst == opponent.getScore()) {
                return WIN_SCORE + you.getScore();
            } else {
                return you.getScore();
            }
        }

        public static Throw determinePlayFromLetter(String letter, Throw opponent) {
            switch (letter) {
                case "X":
                    return getThrowFromScore(opponent.winsAgainst);
                case "Y":
                    return opponent;
                case "Z":
                    return getThrowFromScore(opponent.losesAgainst);
                default:
                    throw new IllegalArgumentException("Invalid RockPaperScissor value. Bye.");
            }
        }

        public static Throw determineThrowFromLetter(String letter) {
            if(Objects.equals(letter, "A") || Objects.equals(letter, "X"))
                return ROCK;
            else if(letter.equals("B")|| letter.equals("Y"))
                return PAPER;
            else if(letter.equals("C") || letter.equals("Z"))
                return SCISSOR;
            else
                throw new IllegalArgumentException("Invalid RockPaperScissor value. Bye.");
        }

        public static Throw getThrowFromScore(int score) {
            if(score == 1) {
                return ROCK;
            } else if (score == 2) {
                return PAPER;
            } else if (score == 3) {
                return SCISSOR;
            } else
                throw new IllegalArgumentException("Invalid RockPaperScissor value. Bye.");
        }
    }

}

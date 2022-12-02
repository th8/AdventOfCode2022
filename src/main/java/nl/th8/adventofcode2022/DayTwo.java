package nl.th8.adventofcode2022;

import nl.th8.adventofcode2022.utils.PuzzleInputParser;

import java.nio.file.Path;
import java.util.Objects;

public class DayTwo implements Day {
    public static final String INVALID_ROCK_PAPER_SCISSOR_VALUE_MSG = "Invalid RockPaperScissor value. Bye.";
    private final PuzzleInputParser puzzleInputParser;

    //For actual use
    public DayTwo() {
        puzzleInputParser = new PuzzleInputParser(Path.of("src", "main", "resources", "input", "2.txt"));
    }

    //For Unittesting
    public DayTwo(PuzzleInputParser puzzleInputParser) {
        this.puzzleInputParser = puzzleInputParser;
    }

    /**
     * Parse the strategy guide to determine each players' throws, and sum scored based on those throws.
     *
     * @return total score after playing by the strategy guide of part one.
     */
    public int solvePartOne() {
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

    /**
     * Parse the strategy guide to determine the opponents throw, and what your goal and deduced throw is for each round.
     * Then sum the scores based on that information.
     *
     * @return total score after playing by the strategy guide of part two.
     */
    public int solvePartTwo() {
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

    public int getDayNumber() {
        return 2;
    }


    /**
     * Represents one of three 'throws' one can choose in a game of Rock Paper Scissors.
     */
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


        /**
         * Determine your score based on the throws made by you and your opponent.
         *
         * @param you your throw.
         * @param opponent opponents throw.
         * @return score for this specific game of Rock Paper Scissors.
         */
        public static int determineScore(Throw you, Throw opponent) {
            if(you == opponent) {
                return DRAW_SCORE + you.getScore();
            } else if (you.winsAgainst == opponent.getScore()) {
                return WIN_SCORE + you.getScore();
            } else {
                return you.getScore();
            }
        }

        /**
         * Determine what throw to play based on the passed letter.
         *
         * @param letter Letter from the strategy guide. X representing a losing throw, Y a draw and Z a winning throw.
         * @param opponent The throw played by your opponent.
         * @return The throw you should play according to the strategy guide.
         */
        public static Throw determinePlayFromLetter(String letter, Throw opponent) {
            return switch (letter) {
                case "X" -> getThrowFromScore(opponent.winsAgainst);
                case "Y" -> opponent;
                case "Z" -> getThrowFromScore(opponent.losesAgainst);
                default -> throw new IllegalArgumentException(INVALID_ROCK_PAPER_SCISSOR_VALUE_MSG);
            };
        }

        /**
         * Determine which throw was indicated in the strategy guide.
         *
         * @param letter Letter from the strategy guide. A and X representing Rock, B and Y representing Paper, C and Z representing Scissors.
         * @return The throw based on the passed letter.
         */
        public static Throw determineThrowFromLetter(String letter) {
            if(Objects.equals(letter, "A") || Objects.equals(letter, "X"))
                return ROCK;
            else if(letter.equals("B")|| letter.equals("Y"))
                return PAPER;
            else if(letter.equals("C") || letter.equals("Z"))
                return SCISSOR;
            else
                throw new IllegalArgumentException(INVALID_ROCK_PAPER_SCISSOR_VALUE_MSG);
        }

        /**
         * Reverse engineer which throw to play based on the opponents throws wins/loses against score.
         *
         * @param score equals to the score of the throw to return.
         * @return throw associated with the passed score.
         */
        public static Throw getThrowFromScore(int score) {
            if(score == 1) {
                return ROCK;
            } else if (score == 2) {
                return PAPER;
            } else if (score == 3) {
                return SCISSOR;
            } else
                throw new IllegalArgumentException(INVALID_ROCK_PAPER_SCISSOR_VALUE_MSG);
        }
    }

}

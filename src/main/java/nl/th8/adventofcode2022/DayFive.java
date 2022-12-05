package nl.th8.adventofcode2022;

import nl.th8.adventofcode2022.utils.PuzzleInputParser;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DayFive implements Day {

    private final PuzzleInputParser puzzleInputParser;

    //For actual use
    public DayFive() {
        puzzleInputParser = new PuzzleInputParser(Path.of("src", "main", "resources", "input", "5.txt"));
    }


    /**
     * After transforming our input to a Map of ArrayDeque we can query this map to pop containers off one stack onto the other.
     * This is achieved by breaking down the move orders into the three relevant integers,
     * and leveraging Java's ArrayDeque, which is both a stack and queue.
     *
     * @return Nothing of worth as this is a String based puzzle
     */
    public int solvePartOne() {
        var inputList = puzzleInputParser.getInputAsStringList();
        Map<Integer, ArrayDeque<String>> containerStacks = transformInputToContainerStackMap(inputList);

        //Execute all move orders
        for(String moveOrder : inputList) {
            //Skip all the non-move order lines
            if(!moveOrder.contains("move"))
                continue;

            //We just need the three numbers from our move order.
            var orderSplit = Arrays.stream(moveOrder.split("\\s+"))
                    .filter(s -> s.matches("[0-9]+"))
                    .map(Integer::parseInt)
                    .toList();

            //Now we need to move [0] containers from mapIndex [1] to [2]
            for(int i = 0; i < orderSplit.get(0); i++) {
                var container = containerStacks.get(orderSplit.get(1)-1).pop();
                containerStacks.get(orderSplit.get(2)-1).addFirst(container);
            }
        }

        StringBuilder resultString = new StringBuilder();
        containerStacks.values().forEach(stack -> resultString.append(stack.getFirst()));

        System.out.printf("The top container of all stacks spell out: %s%n", resultString);

        return 0;
    }

    /**
     * Similar to PartOne, except we introduce an intermediary ArrayDeque to keep track of the Stack our crane can hold.
     *
     * @return nothing of worth as this is a String based puzzle.
     */
    public int solvePartTwo() {
        var inputList = puzzleInputParser.getInputAsStringList();
        Map<Integer, ArrayDeque<String>> containerStacks = transformInputToContainerStackMap(inputList);

        //Execute all move orders
        for(String moveOrder : inputList) {
            //Skip all the non-move order lines
            if(!moveOrder.contains("move"))
                continue;

            //We just need the three numbers from our move order.
            var orderSplit = Arrays.stream(moveOrder.split("\\s+"))
                    .filter(s -> s.matches("[0-9]+"))
                    .map(Integer::parseInt)
                    .toList();

            //Now we need to move [0] containers from mapIndex [1] to [2],
            //Keeping in mind that [0] is moved as one stack.
            ArrayDeque<String> craneStack = new ArrayDeque<>();
            for(int i = 0; i < orderSplit.get(0); i++) {
                //We build a new stack for the containers moved by our crane.
                craneStack.addLast(containerStacks.get(orderSplit.get(1)-1).pop());
            }
            for(int i = 0; i < orderSplit.get(0); i++) {
                //And we add the crane stack in reverse order to our new location on [2]
                containerStacks.get(orderSplit.get(2)-1).addFirst(Objects.requireNonNull(craneStack.pollLast()));
            }
        }

        StringBuilder resultString = new StringBuilder();
        containerStacks.values().forEach(stack -> resultString.append(stack.getFirst()));

        System.out.printf("The top container of all stacks spell out: %s%n", resultString);

        return 0;
    }


    /**
     * Takes the first rows of input and transforms it to a Map of ArrayDeque containing our containers.
     * Java's ArrayDeque can serve both as a Stack as well as a Queue, making it ideal for
     * building our stacks from top to bottom, and moving containers from top to top.
     *
     * @param inputList parsed input for the puzzle
     * @return a Map<Integer, ArrayDeque> containing the containerColumn as index, and an ArrayDeque with our containers.
     */
    private Map<Integer, ArrayDeque<String>> transformInputToContainerStackMap(List<String> inputList) {
        Map<Integer, ArrayDeque<String>> containerStacks = new HashMap<>();

        for(String containerRow : inputList) {
            //We only want the rows with the container diagram for now.
            if(!containerRow.contains("["))
                break;

            AtomicInteger containerRowIndex = new AtomicInteger(0);
            Arrays.stream(containerRow.split(""))
                    //Because stacks are not equally high and therefore we cannot strip whitespace, we divide our row into columns of 4 characters (e.g. '[X] ')
                    .collect(Collectors.groupingBy(s -> containerRowIndex.getAndIncrement() / 4))
                    .entrySet().stream()
                    //Filter out any entries not containing a container
                    .filter(containerEntry -> containerEntry.getValue().get(1).matches("[A-Z]"))
                    //Add them to our ArrayDequeMap
                    .forEach(containerEntry -> {
                        if(containerStacks.containsKey(containerEntry.getKey()))
                            containerStacks.get(containerEntry.getKey()).addLast(containerEntry.getValue().get(1));
                        else
                            containerStacks.put(containerEntry.getKey(), new ArrayDeque<>(Collections.singleton(containerEntry.getValue().get(1))));
                    });
        }
        return containerStacks;
    }



    public int getDayNumber() {
        return 5;
    }
}

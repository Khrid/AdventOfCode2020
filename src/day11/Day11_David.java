package day11;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11_David {
    static final char FLOOR = '.';
    static final char EMPTY = 'L';
    static final char OCCUPIED = '#';
    static final int[][] DIRECTIONS = new int[][]{{-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}};
    static char[][] seatsLayout;

    public static void main(String[] args) throws IOException {

        List<String> tmp = Files.readAllLines(new File("src/day11/input.txt").toPath(), Charset.defaultCharset());

        setLayout(tmp);
        while (seatPeople(seatsLayout)) {
        }
        System.out.println("Part 1 - There are " + countOccupiedSeats(seatsLayout) + " occupied seats after all rounds.");

        setLayout(tmp);
        while (seatPeopleP2(seatsLayout)) {
        }
        System.out.println("Part 2 - There are " + countOccupiedSeats(seatsLayout) + " occupied seats after all rounds.");
    }

    private static void setLayout(List<String> tmp) {
        seatsLayout = new char[tmp.size()][tmp.get(0).length()];
        for (int i = 0; i < tmp.size(); i++) {
            for (int j = 0; j < tmp.get(i).length(); j++) {
                seatsLayout[i][j] = tmp.get(i).charAt(j);
            }
        }
    }

    private static boolean seatPeopleP2(char[][] layout) {
        boolean change = false;
        char[][] newLayout = new char[layout.length][layout[0].length];
        int length = layout[0].length;
        for (int i = 0; i < layout.length; i++) {
            String newLine = "";
            for (int j = 0; j < length; j++) {
                char currentSeat = layout[i][j];
                String current = String.valueOf(currentSeat);
                if (currentSeat != FLOOR) {
                    if (currentSeat == EMPTY) {
                        boolean free = true;
                        // rule 1
                        // If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
                        if (0 == countVisibleOccupiedSeats(i, j, layout)) {
                            current = String.valueOf(OCCUPIED);
                            change = true;
                        }
                    }
                    if (currentSeat == OCCUPIED) {
                        // rule 2
                        // If a seat is occupied (#) and four or more seats adjacent to it are also occupied,
                        // the seat becomes empty.
                        if (5 <= countVisibleOccupiedSeats(i, j, layout)) {
                            current = String.valueOf(EMPTY);
                            change = true;
                        }

                    }
                }
                newLine += current;
            }
            newLayout[i] = newLine.toCharArray();
        }
        //seatsLayout = new ArrayList<>(newLayout);
        seatsLayout = Arrays.copyOf(newLayout, newLayout.length);
        return change;
    }

    private static int countOccupiedSeats(char[][] layout) {
        int count = 0;
        for (char[] chars : layout) {
            for (char aChar : chars) {
                if (aChar == OCCUPIED) count++;
            }
        }
        return count;
    }

    private static boolean seatPeople(char[][] layout) {
        boolean change = false;
        char[][] newLayout = new char[layout.length][layout[0].length];
        int length = layout[0].length;
        for (int i = 0; i < layout.length; i++) {
            String newLine = "";
            for (int j = 0; j < length; j++) {
                char currentSeat = layout[i][j];
                String current = String.valueOf(currentSeat);
                if (currentSeat != FLOOR) {
                    if (currentSeat == EMPTY) {
                        boolean free = true;
                        // rule 1
                        // People don't just care about adjacent seats - they care about the first seat
                        // they can see in each of those eight directions!
                        // Now, instead of considering just the eight immediately adjacent seats,
                        // consider the first seat in each of those eight directions.
                        if (0 == countAdjacentOccupiedSeats(i, j, layout)) {
                            current = String.valueOf(OCCUPIED);
                            change = true;
                        }
                    }
                    if (currentSeat == OCCUPIED) {
                        // rule 2
                        // it now takes five or more visible occupied seats for an occupied seat to become empty
                        if (4 <= countAdjacentOccupiedSeats(i, j, layout)) {
                            current = String.valueOf(EMPTY);
                            change = true;
                        }

                    }
                }
                newLine += current;
            }
            newLayout[i] = newLine.toCharArray();
        }
        seatsLayout = Arrays.copyOf(newLayout, newLayout.length);
        return change;
    }

    static int countAdjacentOccupiedSeats(int x, int y, char[][] layout) {
        int count = 0;

        for (int[] direction : DIRECTIONS) {
            if ((0 <= x + direction[0] && x + direction[0] < layout[0].length)
                    && (0 <= y + direction[1] && y + direction[1] < layout[0].length))
                if (layout[x + direction[0]][y + direction[1]] == OCCUPIED) count++;
        }

        return count;
    }

    static int countVisibleOccupiedSeats(int x, int y, char[][] layout) {
        int count = 0;
        for (int[] direction : DIRECTIONS) {
            int depth = 1;
            boolean seeSomething = false;
            while (!seeSomething) {
                if ((0 <= x + direction[0] * depth && x + direction[0] * depth < layout[0].length)
                        && (0 <= y + direction[1] * depth && y + direction[1] * depth < layout[0].length)) {
                    if (layout[x + direction[0] * depth][y + direction[1] * depth] != FLOOR) {
                        if (layout[x + direction[0] * depth][y + direction[1] * depth] == OCCUPIED) count++;
                        seeSomething = true;
                    }
                } else {
                    seeSomething = true;
                }
                depth++;
            }
        }
        return count;
    }
}

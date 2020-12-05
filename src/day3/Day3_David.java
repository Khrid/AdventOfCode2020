package day3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3_David {
    public static void main(String[] args) {
        // Reading the input file
        ArrayList<String> input = new ArrayList<>();
        Scanner reader = new Scanner(Day3_David.class.getResourceAsStream("input.txt"));
        while (reader.hasNextLine()) {
            input.add(reader.nextLine());
        }

        // Part 1
        int x = 0;
        int y = 0;
        int down = 1;
        int right = 3;
        int bottom;
        int trees = 0;

        System.out.println(input);
        bottom = input.size();

        while (y + down < bottom) {
            y += down;
            x += right;
            if (input.get(y).charAt((x % input.get(y).length())) == '#') trees++;
        }
        System.out.printf("Part 1 - At the end of the map, %s trees encountered%n", trees);

        // Part 2
        int[] rights = new int[]{1, 3, 5, 7, 1};
        int[] downs = new int[]{1, 1, 1, 1, 2};

        BigInteger p2sol = BigInteger.ONE;
        for (int i = 0; i < rights.length; i++) {
            trees = 0;
            x = 0;
            y = 0;
            while (y + downs[i] < bottom) {
                y += downs[i];
                x += rights[i];
                if (input.get(y).charAt((x % input.get(y).length())) == '#') trees++;
            }
            System.out.printf("Part 2 - At the end of the map, slope #%s, %s trees encountered%n", (i+1), trees);
            p2sol = p2sol.multiply(BigInteger.valueOf(trees));
        }
        System.out.println("Part 2 - solution : " + p2sol);
    }
}

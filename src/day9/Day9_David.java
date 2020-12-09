package day9;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class Day9_David {
    private static final int max = 25;
    public static void main(String[] args) throws IOException {
        List<String> list = Files.readAllLines(new File("src/day9/input.txt").toPath(), Charset.defaultCharset());
        long invalid = 0;

        for (int i = max; i < list.size(); i++) {
            if(equalsPreviousNumbers(i, list) != -1) {
                invalid = Long.parseLong(list.get(i));
            }
        }

        System.out.println("Part 1 - First invalid number is " + invalid);

        // Part 2
        for (int i = 0; i < list.size(); i++) {
            long min, max;
            long currentSum = max = min =  Long.parseLong(list.get(i));
            int j = i+1;
            while (currentSum <= invalid) {
                long listAtJ = Long.parseLong(list.get(j));
                currentSum += listAtJ;
                if(min > listAtJ) min = listAtJ;
                if(max < listAtJ) max = listAtJ;
                if(currentSum == invalid) {
                    System.out.print("Part 2 - min is " +min+ ", max is "+max+".");
                    System.out.print(" Their sum is " +(min+max));
                    break;
                }
                j++;
            }

        }
    }

    static long equalsPreviousNumbers(int index, List<String> list) {
        long current = Long.parseLong(list.get(index));

        for (int i = index-1; i >= index-max; i--) {
            for (int j = index-1; j >= index-max ; j--) {
                if(i != j) {
                    if(current == Long.parseLong(list.get(i))+ Long.parseLong(list.get(j))) return -1;
                }
            }
        }

        return current;
    }
}

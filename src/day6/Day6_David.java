package day6;

import java.util.ArrayList;
import java.util.Scanner;

public class Day6_David {
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> input = new ArrayList<>();
        Scanner reader = new Scanner(Day6_David.class.getResourceAsStream("input.txt"));
        ArrayList<String> tmp = new ArrayList<>();
        while (reader.hasNextLine()) {
            String l = reader.nextLine();
            if (l.equals("")) {
                input.add(tmp);
                tmp = new ArrayList<>();
            } else {
                tmp.add(l.replace("\n", ""));
            }
        }
        input.add(tmp);

        int sum1 = 0;
        int sum2 = 0;
        for (ArrayList<String> group :
                input) {
            sum1 += count(group, true, false);
            sum2 += count(group, false, true);
        }
        System.out.println("Part 1 - Total sum is " + sum1);
        System.out.println("Part 2 - Total sum is " + sum2);
    }

    public static int count(ArrayList<String> group, boolean p1, boolean p2) {
        int total = 0;
        int[] alphabet = new int[26];
        for (int i = 0; i < group.size(); i++) {
            for (int j = 0; j < group.get(i).length(); j++) {
                if ((alphabet[group.get(i).charAt(j) - 97] == 0 && p1) || p2) {
                    alphabet[group.get(i).charAt(j) - 97]++;
                }
            }
        }

        for (int idx :
                alphabet) {
            if ((idx == group.size() && p2)) {
                total++;
            } else if (p1) {
                total += idx;
            }
        }

        return total;
    }
}

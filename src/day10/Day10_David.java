package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10_David {

    static final int CHARGING_OUTLET = 0;
    static final int BUILTIN_JOLTAGE_ADAPTER = 3;

    static List<Integer> solution;
    static List<List<Integer>> arrangements = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> adapters = new ArrayList<>();

        Scanner scanner = new Scanner(new File("src/day10/input.txt"));

        while (scanner.hasNextInt()) {
            adapters.add(scanner.nextInt());
        }

        adapters.add(CHARGING_OUTLET);
        adapters.add(Collections.max(adapters) + BUILTIN_JOLTAGE_ADAPTER);
        Collections.sort(adapters);

        // Part 1 - 1 jolt * 3 jolt differences
        int[] deltas = new int[4]; // 0 / 1 / 2 / 3
        for (int i = adapters.size() - 1; i > 0; i--) {
            deltas[adapters.get(i) - adapters.get(i - 1)]++;
        }

        System.out.println(adapters);
        System.out.printf("Part 1 - There is %s 1-jolt differences, and %s 3-jolt differences. Answer is %s", deltas[1], deltas[3], deltas[1] * deltas[3]);
        System.out.println();

        // Part 2 - find number of adapter arrangements possibilities
        // backtracking bitches
        List<List<Integer>> split = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < adapters.size(); i++) {
            tmp.add(adapters.get(i));
            if (i == adapters.size() - 1) {
                split.get(split.size() - 1).add(adapters.get(i));
            } else {
                if (adapters.get(i + 1) - adapters.get(i) >= 3) {
                    split.add(tmp);
                    tmp = new ArrayList<>();
                }
            }
        }

        long total = 1;
        for (List<Integer> s :
                split) {
            total *= findArrangements(s);
        }
        System.out.printf("Part 2 - There is %s ways to arrange the adapters", total);
    }

    private static int findArrangements(List<Integer> adapters) {
        solution = new ArrayList<>(Arrays.asList(Collections.min(adapters)));
        int arrangementsSizeBefore = arrangements.size();
        find(adapters, Collections.max(adapters));
        return arrangements.size() - arrangementsSizeBefore;
    }

    static void find(List<Integer> source, int max) {
        if (solution.size() > 0) {
            if (solution.get(solution.size() - 1) == max) {
                arrangements.add(new ArrayList<>(solution));
                return;
            }
        }

        for (int j = 1; j <= 3; j++) {
            if (source.contains(solution.get(solution.size() - 1) + j)) {
                int tmpPos = source.indexOf(solution.get(solution.size() - 1) + j);
                int tmpVal = source.get(tmpPos);
                source.set(tmpPos, 0);
                solution.add(tmpVal);
                find(source, max);
                source.set(tmpPos, tmpVal);
                solution.remove(solution.size() - 1);
            }
        }
    }
}

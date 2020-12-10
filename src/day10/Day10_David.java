package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.*;

public class Day10_David {

    static final int CHARGING_OUTLET = 0;
    static final int BUILTIN_JOLTAGE_ADAPTER = 3;

    static List<Integer> currentArrangement = new ArrayList<>();
    static List<Integer> solution = new ArrayList<>(Arrays.asList(0));
    static List<List<Integer>> arrangements = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> adapters = new ArrayList<>();

        //Scanner scanner = new Scanner(new File("src/day10/input_example.txt"));
        Scanner scanner = new Scanner(new File("src/day10/input.txt"));

        while(scanner.hasNextInt()) {
            adapters.add(scanner.nextInt());
        }

        adapters.add(CHARGING_OUTLET);
        adapters.add(Collections.max(adapters)+BUILTIN_JOLTAGE_ADAPTER);
        Collections.sort(adapters);

        // Part 1 - 1 jolt * 3 jolt differences
        int[] deltas = new int[4]; // 0 / 1 / 2 / 3
        for (int i = adapters.size()-1; i > 0; i--) {
            deltas[adapters.get(i)-adapters.get(i-1)]++;
        }

        System.out.println(adapters);
        System.out.printf("Part 1 - There is %s 1-jolt differences, and %s 3-jolt differences. Answer is %s", deltas[1], deltas[3], deltas[1] * deltas[3]);
        System.out.println();

        // Part 2 - find number of adapter arrangements possibilities
        // backtracking bitches

        findArrangements(adapters);
        System.out.printf("Part 2 - There is %s ways to arrange the adapters", arrangements.size());
    }

    private static void findArrangements(List<Integer> adapters) {
        currentArrangement = adapters;
        find(currentArrangement, Collections.max(currentArrangement));
    }

    static void find(List<Integer> source, int max) {
        if(solution.size() > 0) {
            if (solution.get(solution.size()-1) == max) {
                arrangements.add(new ArrayList<>(solution));
                System.out.println(solution);
                //solution = new ArrayList<>(Arrays.asList(0));
                return;
            }
        }

        //for (int i = pos; i < source.size(); i++) {
            for (int j = 1; j <= 3; j++) {
                //if(i < solution.size()) {
                    if (source.contains(solution.get(solution.size()-1) + j)) {
                        int tmpPos = source.indexOf(solution.get(solution.size()-1) + j);
                        int tmpVal = source.get(tmpPos);
                        source.set(tmpPos, 0);
                        solution.add(tmpVal);
                        find(source, max);
                        source.set(tmpPos, tmpVal);
                        solution.remove(solution.size() - 1);
                        //tmpPos = tmpPos - 1;
                    }
                //}
            }
        //}


    }
}

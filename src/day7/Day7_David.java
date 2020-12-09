package day7;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class Day7_David {

    private static Map<String, String> bagMap;

    public static void main(String[] args) throws IOException {
        bagMap = Files.lines(Paths.get("src/day7/input.txt"))
                .collect(toMap(Day7_David::parentBag, Function.identity()));

        System.out.println(containsShinyGold());
        System.out.println(countChildBags("shiny gold"));
    }

    private static String parentBag(String s) {
        Matcher matcher = Pattern.compile("^(\\w+\\s\\w+)").matcher(s);
        matcher.find();
        return matcher.group(0);
    }

    private static long containsShinyGold() {
        return bagMap.keySet()
                .stream()
                .filter(Day7_David::containsShinyGold)
                .count();
    }

    private static boolean containsShinyGold(String s) {
        String content = bagMap.get(s);
        Matcher matcher = Pattern.compile("(\\d)\\s(\\w+\\s\\w+)").matcher(content);
        List<String> childBags = new ArrayList<>();
        while(matcher.find()) {
            if (matcher.group(2).equals("shiny gold")) {
                return true;
            }
            childBags.add(matcher.group(2));
        }
        return childBags.stream().anyMatch(Day7_David::containsShinyGold);
    }

    private static int countChildBags(String startBag) {
        String content = bagMap.get(startBag);
        Matcher matcher = Pattern.compile("(\\d)\\s(\\w+\\s\\w+)").matcher(content);
        int count = 0;
        while (matcher.find()) {
            int amount =  Integer.parseInt(matcher.group(1));
            String bagName = matcher.group(2);
            count += amount + (amount * countChildBags(bagName));
        }
        return count;
    }
}

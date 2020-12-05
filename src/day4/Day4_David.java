package day4;

import java.util.ArrayList;
import java.util.Scanner;

public class Day4_David {
    public static void main(String[] args) {
        /**
         * byr (Birth Year)
         * iyr (Issue Year)
         * eyr (Expiration Year)
         * hgt (Height)
         * hcl (Hair Color)
         * ecl (Eye Color)
         * pid (Passport ID)
         * cid (Country ID)
         */

        // Reading the input file
        ArrayList<String> input = new ArrayList<>();
        Scanner reader = new Scanner(Day4_David.class.getResourceAsStream("input.txt"));
        String line = "";
        while (reader.hasNextLine()) {
            String nextLine = reader.nextLine();
            if (nextLine.equals("")) {
                input.add(line.trim());
                line = "";
            }
            line += nextLine + " ";
        }
        input.add(line.trim());
        System.out.println(input);

        int validPassportsP1 = 0;
        int validPassportsP2 = 0;
        for (String entry :
                input) {
            if (isValidP1(entry)) {
                validPassportsP1++;
                if (isValidP2(entry)) validPassportsP2++;
            }
        }
        System.out.println("P1 - " + validPassportsP1);
        System.out.println("P2 - " + validPassportsP2);
    }

    public static boolean isValidP1(String entry) {
        if (entry.contains("byr")
                && entry.contains("iyr")
                && entry.contains("eyr")
                && entry.contains("hgt")
                && entry.contains("hcl")
                && entry.contains("ecl")
                && entry.contains("pid")
        ) return true;

        return false;
    }

    public static boolean isValidP2(String entry) {
        String[] explode = entry.split(" ");
        int ok = 0;
        for (String pair :
                explode) {
            String key = pair.split(":")[0];
            String value = pair.split(":")[1];
            if (key.contains("byr")) {
                // byr (Birth Year) - four digits; at least 1920 and at most 2002.
                if (1920 <= Integer.parseInt(value) && Integer.parseInt(value) <= 2002) ok++;
            } else if (key.contains("iyr")) {
                // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
                if (2010 <= Integer.parseInt(value) && Integer.parseInt(value) <= 2020) ok++;
            } else if (key.contains("eyr")) {
                // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
                if (2020 <= Integer.parseInt(value) && Integer.parseInt(value) <= 2030) ok++;
            } else if (key.contains("hgt")) {
                // hgt (Height) - a number followed by either cm or in:
                if (value.matches("[0-9]*(in|cm)")) {
                    // If cm, the number must be at least 150 and at most 193.
                    if (value.contains("cm")) {
                        value = value.replace("cm", "");
                        if (150 <= Integer.parseInt(value) && Integer.parseInt(value) <= 193) ok++;
                        // If in, the number must be at least 59 and at most 76.
                    } else {
                        value = value.replace("in", "");
                        if (59 <= Integer.parseInt(value) && Integer.parseInt(value) <= 76) ok++;
                    }
                }
            } else if (key.contains("hcl")) {
                // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
                if (value.matches("#([0-9a-f]){6}")) ok++;
            } else if (key.contains("ecl")) {
                // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
                if (value.matches("amb|blu|brn|gry|grn|hzl|oth")) ok++;
            } else if (key.contains("pid")) {
                // pid (Passport ID) - a nine-digit number, including leading zeroes.
                if (value.matches("[0-9]{9}")) ok++;
            }
        }
        if (ok == 7) return true;

        return false;
    }
}

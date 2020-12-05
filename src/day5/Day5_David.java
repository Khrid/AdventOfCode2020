package day5;

import java.util.*;

public class Day5_David {
    public static void main(String[] args) {

        // Reading the input file
        ArrayList<String> input = new ArrayList<>();
        Scanner reader = new Scanner(Day5_David.class.getResourceAsStream("input.txt"));
        while (reader.hasNextLine()) {
            input.add(reader.nextLine());
        }
        Set<Integer> seatIds = new TreeSet<>();
        int maxSeatId = 0;
        for (String entry :
                input) {
            int row = find("row", entry.substring(0, 7), 0, 127);
            int column = find("column", entry.substring(7), 0, 7);
            int id = row * 8 + column;
            if(id > maxSeatId) maxSeatId = id;
            seatIds.add(id); // for p2
        }


        System.out.println("Part 1 - Max seat id is " + maxSeatId);

        // ---- p2
        Iterator<Integer> i = seatIds.iterator();

        while(i.hasNext()) {
            int currentSeat = i.next();
            if(seatIds.contains(currentSeat+2) && !seatIds.contains(currentSeat+1)){
                System.out.println("Part 2 - My seat is " + (currentSeat+1));
                return;
            }
        }
    }

    public static int find(String target, String partition, int min, int max) {
        if(partition.length() == 0) {
            if(target.equals("row")) return min;
            return max;
        }

        String subpart = partition.substring(1);
        int middle = (max-min)/2;
        if(target.equals("row")) {
            if (partition.charAt(0) == 'F')
                return find(target, subpart, min, min + middle);
            else
                return find(target, subpart, max - middle, max);
        } else {
            if (partition.charAt(0) == 'L')
                return find(target, subpart, min, min + middle);
            else
                return find(target, subpart, max - middle, max);
        }
    }
}

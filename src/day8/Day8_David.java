package day8;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day8_David {
    public static void main(String[] args) throws IOException {
        List<String> list = Files.readAllLines(new File("src/day8/input.txt").toPath(), Charset.defaultCharset());

        int acc = processCode(true, list);
        System.out.println("Part 1 - acc value is "+ acc);

        testSolution(0, list);
    }

    public static void testSolution(int line, List<String> code) {
        List<String> code_ = new ArrayList<>(code);

        for (int i = line; i < code_.size(); i++) {
            if(code_.get(i).contains("jmp") || code_.get(i).contains("nop")) {
                if (code_.get(i).contains("jmp")) {
                    code_.set(i, code_.get(i).replace("jmp", "nop"));
                } else if (code.get(i).contains("nop")) {
                    code_.set(i, code_.get(i).replace("nop", "jmp"));
                }
                int res = processCode(false, code_);
                if (res == -1)
                    testSolution(i + 1, code);
                else
                    System.out.println("Part 2 - acc value is " + res);
                    break;
            }
        }
    }

    public static int processCode(boolean p1, List<String> code) {
        List<Integer> processed = new ArrayList<>();
        int acc = 0;
        for (int i = 0; i < code.size(); i++) {
            if(processed.contains(i)) {
                return (p1 ? acc : -1);
            }
            String instr = code.get(i);
            processed.add(i);
            int modifier = Integer.parseInt(instr.split(" ")[1]);

            switch (instr.split(" ")[0]) {
                case "acc":
                    acc += modifier;
                    break;
                case "jmp":
                    i += modifier-1;
                    break;
            }
        }
        return acc;
    }
}

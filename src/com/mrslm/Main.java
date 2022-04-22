package com.mrslm;

import com.mrslm.services.FileService;
import com.mrslm.services.ProcessorService;

import java.io.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Main {
    private static final LinkedList<String> inputs = new LinkedList<>(Arrays.asList(
            "src/com/test/test_input.txt",
            "src/com/test/test1_input.txt",
            "src/com/test/test2_input.txt"));
    private static final String outputs = inputs.get(0).substring(0, inputs.get(0).lastIndexOf("/") + 1) + "result/";
    private static int counter = 0;

    public static void main(String[] args) {
        try {
            if (args.length != 0) {
                inputs.clear();
                for (String arg : args) {
                    inputs.add(arg.trim().replace(",", "").replace("\"", ""));
                }
            } else {
                System.out.println("Введите адрес одного или нескольких файлов, которые следует обработать. Для выхода введите \\q");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String userInput = reader.readLine();
                if (userInput.equals("\\q")) System.exit(0);
                inputs.clear();
                inputs.addAll(Arrays.asList(userInput.trim().replace(",", "").split(" ")));
            }
            {
                    for (String inPath : inputs) {
                        String outPath = outputs + "output" + (counter > 0 ? (("_" + counter)) : "") + ".txt";
                        try {
                            String content = FileService.read(inPath);
                            Map<String, String> combined = ProcessorService.combine(ProcessorService.parse(content));
                            FileService.write(combined, outPath);
                            counter += 1;
                            String fileName = inPath.substring(inPath.lastIndexOf("/")+1);
                            System.out.println(fileName + " успешно обработан. Результат: " + outPath);
                            //test(inPath, outPath);
                        }catch (RuntimeException e){
                            System.out.println(e.getMessage());
                        }
                    }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            main(args);
        }finally {
            args = new String[0];
            main(args);
        }
    }

    private static void test(String inPath, String comparedPath) {
        try {
            String referenceFile = FileService.read(inPath.replace("input", "ref_output"));
            String comparedFile = FileService.read(comparedPath);
            if (!referenceFile.equals(comparedFile)) {
                System.err.println("Внимание: результат не соответствует образцу");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Не удалось протестировать: не найден файл с образцом результата");
        }

    }
}
package com.mrslm;

import com.mrslm.services.FileService;
import com.mrslm.services.ProcessorService;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    private static final LinkedList<String> inputs = new LinkedList<>();
    private static final LinkedList<String> outputs = new LinkedList<>();
    private static int counter = 0;

    public static void main(String[] args) {
        try {
            if (args.length != 0) {
                initIO(Arrays.asList(args));
            } else {
                System.out.println("Введите адрес одного или нескольких файлов, которые следует обработать. Для выхода введите \\q");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String userInput = reader.readLine();
                if (userInput.equals("\\q")) System.exit(0);
                initIO(Arrays.asList(userInput.trim().split(" ")));
            }
            for (int i = 0; i < inputs.size(); i++) {
                String outPath = outputs.get(i) + "output" + (counter > 0 ? (("_" + counter)) : "") + ".txt";
                try {
                    String content = FileService.read(inputs.get(i));
                    Map<String, String> combined = ProcessorService.combine(ProcessorService.parse(content));
                    FileService.write(combined, outPath);
                    counter += 1;
                    String fileName = inputs.get(i).substring(inputs.lastIndexOf("/") + 1);
                    System.out.println(fileName + " успешно обработан. Результат: " + outPath);
                    //test(inPath, outPath);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            main(args);
        } finally {
            args = new String[0];
            main(args);
        }
    }

    private static void initIO(List<String> inputPaths) {
        inputs.clear();
        outputs.clear();
        for (String path : inputPaths) {
            path.trim().replace(",", "").replace("\"", "");
        }
        inputs.addAll(inputPaths);
        for (String input : inputs) {
            String output = input.substring(0, input.lastIndexOf("/") + 1) + "result/";
            outputs.add(output);
            File dir = new File(output);
            if (!dir.exists()) {
                dir.mkdir();
            }
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
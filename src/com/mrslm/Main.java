package com.mrslm;

import com.mrslm.services.FileService;
import com.mrslm.services.ProcessorService;

import java.util.Map;

public class Main {
    private static String[] inputs = new String[]{"src/com/test/test1_input.txt", "src/com/test/test2_input.txt", "src/com/test/test3_input.txt"};
    private static String outputs = inputs[0].substring(0, inputs[0].lastIndexOf("/")+1)+ "result/";
    private static int counter = 0;

    public static void main(String[] args) {
        for (String inPath : inputs) {
            counter +=1 ;
            String outPath = outputs +"test" +counter+"_output.txt";
            String content = FileService.read(inPath);
            Map<String, String> combined = ProcessorService.combine(ProcessorService.parse(content));
            FileService.write(combined, outPath);
            test(inPath, outPath);
        }
    }
    private static void test(String inPath, String comparedPath){
        String referenceFile = FileService.read(inPath.replace("input", "ref_output"));
        String comparedFile = FileService.read(comparedPath);
        System.out.println("Test for file "
                + comparedPath.substring(comparedPath.lastIndexOf("/")+1)
                + " "
                + referenceFile.equals(comparedFile));
    }
}

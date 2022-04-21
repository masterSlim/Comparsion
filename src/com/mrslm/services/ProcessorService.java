package com.mrslm.services;

import java.util.*;

public class ProcessorService {

    public static Map<Integer, ArrayList<String>> parse(String content) {
        int counter = 0;
        Map<Integer, ArrayList<String>> result = new HashMap<>();
        ArrayList<String> words;
        String[] lines = content.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].matches("\\d+")) {
                int lastLine = i+Integer.parseInt(lines[i]);
                words = new ArrayList<>();
                for (int a = i+1; a <= lastLine; a++) {
                    words.add(lines[a]);
                    i++;
                }
                result.put(counter++, words);
            }
        }
        return result;
    }

    public static Map<String, String> combine(Map<Integer, ArrayList<String>> parsed) {
        Map<String, String> combination = new LinkedHashMap<>();
        if((parsed.get(0).size() == 1) && (parsed.get(1).size() == 1)){
            combination.put(parsed.get(0).get(0), parsed.get(1).get(0));
            return combination;
        }
        for (String key : parsed.get(0)) {
            for (String value : parsed.get(1)) {
                if (same(key, value)) {
                    combination.put(key, value);
                    break;
                } else {
                    combination.put(key, null);
                }
            }
        }
        return combination;
    }

    private static boolean same(String reference, String compared) {
        ArrayList<String> refWords = new ArrayList<>(Arrays.asList(reference.split(" ")));
        for (String refWord : refWords) {
            if (compared.toLowerCase().contains(refWord.toLowerCase())){
                return true;
            }else if ((overlap(refWord.toLowerCase(), compared.toLowerCase())) > 0.8){
                return true;
            }
        }
        return false;
    }

    private static double overlap(String reference, String compared) {
        int a = reference.length();
        int b = compared.length();
        int c = 0;
        Set<Integer> checked = new HashSet<>();
        for (char ch : reference.toCharArray()) {
            int index = compared.indexOf(ch);
            if ((index > -1) && (!checked.contains(index))){
                c += 1;
                checked.add(index);
            }
        }
        double tanimotoCoef = c / (a + b - c);
        return tanimotoCoef;

    }
}

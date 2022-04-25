package com.mrslm.services;

import java.util.*;

public class ProcessorService {

    public static HashMap<String, ArrayList<String>> parse(String content) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        ArrayList<String> words;
        String[] lines = content.split("\n");
        String side = "left";
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].matches("\\d+")) {
                int lastLine = i + Integer.parseInt(lines[i]);
                words = new ArrayList<>();
                for (int a = i + 1; a <= lastLine; a++) {
                    words.add(lines[a]);
                    i++;
                }
                result.put(side, words);
                side = "right";
            }
        }
        if (result.isEmpty()) throw new NullPointerException("Ошибка при чтении файла. Проверьте, выбран ли файл с неподходящим содержанием");
        return result;
    }

    public static LinkedHashMap<String, String> combine(Map<String, ArrayList<String>> parsed) {
        LinkedHashMap<String, String> combination = new LinkedHashMap<>();
        ArrayList<String> left = parsed.get("left");
        ArrayList<String> right = parsed.get("right");
        if (left.size() == 1 && right.size() == 1) {
            combination.put(left.get(0), right.get(0));
            return combination;
        }
        for (int i = 0; i < left.size(); i++) {
            String l = left.get(i);
            for (String r : right) {
                if (same(l, r)) {
                    combination.put(l, r);
                    break;
                } else {
                    combination.put(l, null);
                }
            }
            int leftover = right.size() - left.size();
            for (int v = 0; v < leftover; v++)
                combination.put(null, right.get(left.size()+v));
        }
        return combination;
    }

    private static boolean same(String reference, String compared) {
        ArrayList<String> refWords = new ArrayList<>(Arrays.asList(reference.split(" ")));
        for (String refWord : refWords) {
            if(refWord.length()<4) continue;
            if (compared.toLowerCase().contains(refWord.toLowerCase())) {
                return true;
            } else
                for(String s : compared.split(" ")){
                    if ((overlap(refWord.toLowerCase(), s.toLowerCase())) > 0.56) return true;
                }
        }
        return false;
    }

    private static double overlap(String reference, String compared) {
        double a = reference.length();
        double b = compared.length();
        double c = 0;
        Set<Integer> checked = new HashSet<>();
        for (char ch : reference.toCharArray()) {
            int index = compared.indexOf(ch);
            if ((index > -1) && (!checked.contains(index))) {
                c += 1;
                checked.add(index);
            }
        }
        double tanimotoCoef = c / (a + b - c);
        return tanimotoCoef;

    }
}

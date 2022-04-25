package com.mrslm.services;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileService {

    public static String read(String inPath) {
        String fileName = inPath.substring(inPath.lastIndexOf("/") + 1);
        StringBuilder result = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(new File(inPath).getAbsoluteFile()))) {
            String s;
            while ((s = in.readLine()) != null) {
                result.append(s).append("\n");
            }
            result.deleteCharAt(result.length() - 1); // убирает последний перенос строки
        } catch (FileNotFoundException e) {
            throw new RuntimeException(fileName + " не найден. Проверьте, правильно ли указан путь к файлу");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении файла " + fileName);
        }
        return result.toString();
    }

    public static void write(Map<String, String> content, String fileName) {
        try (PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile())) {
            String output = format(content);
            out.print(output);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать файл " + fileName);
        }
    }

    private static String format(Map<String, String> content) {
        StringBuilder result = new StringBuilder();
        content = align(content);
        for (String key : content.keySet()) {
            result.append(key).append(":");
            if (content.get(key) == null) {
                result.append("?");
            } else result.append(content.get(key));
            result.append("\n");
        }
        result.deleteCharAt(result.length() - 1); // убирает последний перенос строки
        return result.toString();
    }

    private static Map<String, String> align(Map<String, String> crooked) {
        Map<String, String> fixed = new LinkedHashMap<>();
        for (String key : crooked.keySet()) {
            if (key == null) {
                String tmp = crooked.get(null);
                fixed.put(tmp, null);
            } else {
                fixed.put(key, crooked.get(key));
            }
        }
        return fixed;
    }

}

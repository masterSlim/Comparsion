package com.mrslm.services;

import java.io.*;
import java.util.Map;

public class FileService {

    public static String read(String inPath) {
        String fileName = inPath.substring(inPath.lastIndexOf("/")+1);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(new File(inPath).getAbsoluteFile()))) {
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(fileName + " не найден. Проверьте, правильно ли указан путь к файлу");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении файла " + fileName);
        }
        return sb.toString();
    }

    public static void write(Map<String, String> content, String fileName) {
        try (PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile())) {
            out.print(format(content));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать файл " + fileName);
        }
    }

    private static String format(Map<String, String> content) {
        StringBuilder result = new StringBuilder();
        align(content);
        for (String key: content.keySet()) {
            result.append(key).append(":");
            if (content.get(key) == null) {
                result.append("?");
            } else result.append(content.get(key));
            result.append("\n");
        }
        return result.toString();
    }
    private static void align(Map<String,String> crooked){
        for (String key:crooked.keySet()) {
            String tmp;
            if(key == null){
                tmp = crooked.get(null);
                crooked.remove(key);
                crooked.put(tmp, null);
            }
        }
    }

}

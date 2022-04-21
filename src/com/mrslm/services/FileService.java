package com.mrslm.services;

import java.io.*;
import java.util.Map;

public class FileService {

    public static String read(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(new File(filePath).getAbsoluteFile()))) {
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Проверьте, правильно ли указан путь к файлу");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла. Проверьте, используете ли вы подходящий файл txt");
        }
        return sb.toString();
    }
    public static void write(Map<String, String> content, String fileName){
        try (PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile())) {
            out.print(format(content));
        } catch (IOException e){
            System.err.println("Не удалось записать файл");
        }
    }
    private static String format(Map<String,String> content){
        StringBuilder result = new StringBuilder();
        for (String key: content.keySet()) {
            result.append(key).append(":");
            if(content.get(key) == null){
                result.append("?");
            }else result.append(content.get(key));
            result.append("\n");

        }

        return result.toString();
    }
}

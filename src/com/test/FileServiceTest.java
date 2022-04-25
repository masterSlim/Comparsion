package com.test;

import com.mrslm.services.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class FileServiceTest {
    String inPath = "src/com/test/testdata/test_input.txt";
    String in = "4\n" + "гвоздь\n" + "шуруп\n" + "краска синяя\n" + "ведро для воды\n" + "3\n" + "краска\n" +
            "корыто для воды\n" + "шуруп 3х1.5";
    String outPath = "src/com/test/testdata/test_ref_output.txt";
    String out = "гвоздь:?\n" + "шуруп:шуруп 3х1.5\n" + "краска синяя:краска\n" + "ведро для воды:корыто для воды";

    String inPath1 = "src/com/test/testdata/test1_input.txt";
    String in1 = "1\n" + "Бетон с присадкой\n" + "1\n" + "Цемент";
    String outPath1 = "src/com/test/testdata/test1_ref_output.txt";
    String out1 = "Бетон с присадкой:Цемент";

    String inPath2 = "src/com/test/testdata/test2_input.txt";
    String in2 = "1\n" + "Бетон с присадкой\n" + "2\n" + "присадка бля бетона\n" + "доставка";
    String outPath2 = "src/com/test/testdata/test2_ref_output.txt";
    String out2 = "Бетон с присадкой:присадка бля бетона\n" + "доставка:?";

    String inPath3 = "src/com/test/testdata/test3_input.txt";
    String in3 = "1\n" + "Бетон с присадкой\n" + "2\n" + "присадка бля бетона\n" + "доставка";
    String outPath3 = "src/com/test/testdata/test3_ref_output.txt";
    String out3 = "1\n" + "Бетон с присадкой\n" + "2\n" + "присадка бля бетона\n" + "доставка";

    @TempDir
    java.io.File tempDir;

    @Test
    void readTestFiles() {
        List<String> refIn = Arrays.asList(inPath, inPath1, inPath2, inPath3);
        List<String> refOut = Arrays.asList(in, in1, in2, in3);
        for (int i = 0; i < 4; i++) {
            String expected = refOut.get(i);
            String actual = FileService.read(refIn.get(i));
            Assertions.assertEquals(expected, actual);
        }
    }

    @Test
    void readEmptyWrong() {
        String empty = "";
        String wrong = ")&*()";
        Exception e;
        e = Assertions.assertThrows(RuntimeException.class, () -> FileService.read(empty), "Методу read() удалось прочитать пустую ссылку");
        Assertions.assertEquals(" не найден. Проверьте, правильно ли указан путь к файлу", e.getMessage());
        e = Assertions.assertThrows(RuntimeException.class, () -> FileService.read(wrong), "Методу read() удалось прочитать неправильную ссылку");
        Assertions.assertEquals(wrong + " не найден. Проверьте, правильно ли указан путь к файлу", e.getMessage());
    }

    @Test
    void write() throws IOException {
        Map<String, String> content = new LinkedHashMap<>();
        content.put("Бетон с присадкой", "присадка бля бетона");
        content.put(null, "доставка");
        String fileName = tempDir.getAbsolutePath() + "/testWrite";
        FileService.write(content, fileName);
        String actual = new String(Files.readAllBytes(Paths.get(fileName)));
        Assertions.assertEquals(out2, actual);
    }
}
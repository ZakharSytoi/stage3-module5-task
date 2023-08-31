package com.mjc.school.repository.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    public static List<String> read(String fileName) {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null)
                data.add(line);
        } catch (IOException e) {
            throw new RuntimeException(e + "Exception while reading from " + fileName);
        }
        return data;
    }
}

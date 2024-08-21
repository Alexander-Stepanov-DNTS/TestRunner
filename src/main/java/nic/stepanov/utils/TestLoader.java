package nic.stepanov.utils;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.models.Result;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLoader {
    private final Map<String, String> testMap = new HashMap<>();

    public void loadTests() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/nic.stepanov/test_names.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    testMap.put(parts[0], parts[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTest(String testName, String className) {
        testMap.put(testName, className);
    }

    public List<String> getTestNames() {
        return new ArrayList<>(testMap.keySet());
    }

    public Result runTestByName(String testName) throws TestFailedException {
        String className = testMap.get(testName);
        if (className == null) {
            throw new TestFailedException("Тест не найден: " + testName);
        }

        try {
            Class<?> testClass = Class.forName(className);
            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            Method runMethod = testClass.getMethod("runTest");

            return (Result) runMethod.invoke(testInstance);

        } catch (Exception e) {
            throw new TestFailedException("Ошибка выполнения теста: " + e.getMessage(), e);
        }
    }
}
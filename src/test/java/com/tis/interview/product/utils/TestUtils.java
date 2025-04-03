package com.tis.interview.product.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TestUtils {
    public static  String readJsonFile(String path) throws IOException {
        Path filePath = Path.of(path);
        return Files.readString(filePath);
    }
}
package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class ReflectorTest {

    private final String sourceDir = "./classes";

    @Test
    void test() throws IOException {

    }

    private String getFileName(final String name) {
        return sourceDir + File.separator + name;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFile(final String name, @NotNull final String... lines) throws IOException {
        final File file = new File(getFileName(name));
        new File(file.getParent()).mkdirs();
        file.createNewFile();

        try (final PrintWriter writer = new PrintWriter(file)) {
            for (final String line : lines) {
                writer.append(line).append('\n');
            }
        }
    }

    private void deleteFile(@NotNull final String name) {
        final File file = new File(name);
        if (!file.isDirectory()) {
            file.delete();
        } else {
            //noinspection ConstantConditions
            for (final File inner : file.listFiles()) {
                deleteFile(inner.getAbsolutePath());
            }
            file.delete();
//            assertTrue(file.delete());
        }
    }
}
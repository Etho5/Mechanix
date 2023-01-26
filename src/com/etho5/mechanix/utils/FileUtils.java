package com.etho5.mechanix.utils;

import com.etho5.mechanix.Main;

import java.io.*;
import java.nio.channels.ClosedByInterruptException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class FileUtils {

    public static File createIfNotExists(String path) {
        final File dataFolder = Main.getInstance().getDataFolder();
        final int lastIndex = path.lastIndexOf("/");
        final File directory = new File(dataFolder, path.substring(0, Math.max(lastIndex, 0)));

        directory.mkdirs();

        final File destination = new File(dataFolder, path);

        try {
            destination.createNewFile();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }

        return destination;
    }

    public static void zip(String sourceDirectory, String to) throws IOException {
        final File parent = Main.getInstance().getDataFolder().getParentFile().getParentFile();
        final File toFile = new File(parent, to + ".zip");

        if (toFile.exists())
            return;

        final Path pathTo = Files.createFile(Paths.get(toFile.toURI()));

        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(pathTo))) {
            final Path pathFrom = Paths.get(new File(parent, sourceDirectory).toURI());

            Files.walk(pathFrom).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                final ZipEntry zipEntry = new ZipEntry(pathFrom.relativize(path).toString());

                try {
                    zs.putNextEntry(zipEntry);

                    Files.copy(path, zs);
                    zs.closeEntry();
                } catch (final IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public static void deleteRecursively(File file) {
        if (file.isDirectory())
            for (final File subfolder : file.listFiles())
                deleteRecursively(subfolder);
    }

    public static File createIfNotExists(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (final Throwable t) {
                t.printStackTrace();
            }
        }
        return file;
    }

    public static File getOrMakeFile(String path) {
        final File file = getFile(path);

        return file.exists() ? file : createIfNotExists(path);
    }

    public static File getFile(String path) {
        return new File(Main.getInstance().getDataFolder(), path);
    }

    // ----------------------------------------------------------------
    // Writing
    // -----------------------------------------------------------------

    public static void write(String to, Collection<String> lines) {
        write(getOrMakeFile(to), lines, StandardOpenOption.APPEND);
    }

    public static File extract(String path) {
        return extract(path, path);
    }

    public static File extract(String from, String to) {
        File file = new File(Main.getInstance().getDataFolder(), to);

        final List<String> lines = getInternalFileContent(from);

        if (file.exists())
            return file;

        file = createIfNotExists(to);

        try {
            final String fileName = getFileName(file);

            Files.write(file.toPath(), lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (final IOException ex) {
            ex.printStackTrace();
        }

        return file;
    }

    public static String getFileName(File file) {
        return getFileName(file.getName());
    }

    public static String getFileName(String path) {

        int pos = path.lastIndexOf("/");

        if (pos > 0)
            path = path.substring(pos + 1);
        pos = path.lastIndexOf(".");
        if (pos > 0)
            path = path.substring(0, pos);

        return path;
    }

    public static void write(File to, Collection<String> lines, StandardOpenOption... options) {
        try {
            final Path path = Paths.get(to.toURI());

            try {
                if (!to.exists())
                    createIfNotExists(to);

                Files.write(path, lines, StandardCharsets.UTF_8, options);
            } catch (final ClosedByInterruptException ex) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(to, true))) {
                    for (final String line : lines) {
                        bw.append(System.lineSeparator() + line);
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<String> getInternalFileContent(String path) {

        try (JarFile jarFile = new JarFile(Main.getInstance().getDataFolder())) {

            for (final Enumeration<JarEntry> it = jarFile.entries(); it.hasMoreElements(); ) {
                final JarEntry entry = it.nextElement();

                if (entry.toString().equals(path)) {

                    final InputStream is = jarFile.getInputStream(entry);
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    final List<String> lines = reader.lines().collect(Collectors.toList());

                    reader.close();
                    return lines;
                }
            }

        } catch (final Throwable ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void extractFolderFromJar(String folder, final String destination) {
        if (getFile(folder).exists())
            return;

        try (JarFile jarFile = new JarFile(Main.getInstance().getDataFolder())) {
            for (final Enumeration<JarEntry> it = jarFile.entries(); it.hasMoreElements(); ) {
                final JarEntry jarEntry = it.nextElement();
                final String entryName = jarEntry.getName();

                // Copy each individual file manually
                if (entryName.startsWith(folder) && !entryName.equals(folder))
                    extract(entryName);
            }

        } catch (final Throwable t) {
            t.printStackTrace();
        }
    }

    public static File[] getFiles(String directory, String extension) {

        // Remove initial dot, if any
        if (extension.startsWith("."))
            extension = extension.substring(1);

        final File dataFolder = new File(Main.getInstance().getDataFolder(), directory);

        if (!dataFolder.exists())
            dataFolder.mkdirs();

        final String finalExtension = extension;

        return dataFolder.listFiles((FileFilter) file -> !file.isDirectory() && file.getName().endsWith("." + finalExtension));
    }
}

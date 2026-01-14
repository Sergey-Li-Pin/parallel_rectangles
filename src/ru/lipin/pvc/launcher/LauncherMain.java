package ru.lipin.pvc.launcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LauncherMain {

    private static final String CHILD_MAIN_CLASS = "ru.lipin.pvc.app.WindowMain";

    public static void main(String[] args) {
        String javaBin = getJavaBin();
        String classPath = System.getProperty("java.class.path");

        startChild(javaBin, classPath,
                "--pos=top",
                "--mode=filled");

        startChild(javaBin, classPath,
                "--pos=bottom",
                "--mode=outline");
    }

    private static void startChild(String javaBin, String classPath, String... childArgs) {
        List<String> cmd = new ArrayList<>();
        cmd.add(javaBin);
        cmd.add("-cp");
        cmd.add(classPath);
        cmd.add(CHILD_MAIN_CLASS);

        for (String a : childArgs) {
            cmd.add(a);
        }

        ProcessBuilder pb = new ProcessBuilder(cmd);

        pb.inheritIO();

        try {
            pb.start();
        } catch (IOException e) {
            System.err.println("Не удалось запустить дочерний процесс: " + String.join(" ", cmd));
            e.printStackTrace();
        }
    }

    private static String getJavaBin() {
        String javaHome = System.getProperty("java.home");
        Path javaPath = Paths.get(javaHome, "bin", isWindows() ? "java.exe" : "java");
        return javaPath.toString();
    }

    private static boolean isWindows() {
        String os = System.getProperty("os.name");
        return os != null && os.toLowerCase().contains("win");
    }
}

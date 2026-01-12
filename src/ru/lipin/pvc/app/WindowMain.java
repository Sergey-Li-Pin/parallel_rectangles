package ru.lipin.pvc.app;


import ru.lipin.pvc.logic.RectGenerator;
import ru.lipin.pvc.model.RectModel;
import ru.lipin.pvc.ui.RectPanel;
import ru.lipin.pvc.util.Constants;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Dimension;

/**
 * Дочерний процесс: поднимает одно окно и рисует прямоугольник
 * в режиме filled/outline. Параметры фигуры меняются по таймеру.
 */
public class WindowMain {

    private enum Pos { TOP, BOTTOM }

    public static void main(String[] args) {
        AppArgs a = parseArgs(args);

        long pid = ProcessHandle.current().pid();
        System.out.printf("Process started: pid=%d pos=%s mode=%s%n",
                pid, a.pos, a.filled ? "filled" : "outline");

        SwingUtilities.invokeLater(() -> createAndShow(a));
    }

    private static void createAndShow(AppArgs a) {
        JFrame frame = new JFrame(titleFor(a));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RectModel model = new RectModel();
        RectGenerator generator = new RectGenerator();

        RectPanel panel = new RectPanel(model, a.filled);
        panel.setPreferredSize(new Dimension(Constants.WINDOW_W, Constants.WINDOW_H));

        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);

        // Позиционирование верх/низ
        if (a.pos == Pos.TOP) {
            frame.setLocation(Constants.X, Constants.Y_TOP);
        } else {
            frame.setLocation(Constants.X, Constants.Y_TOP + Constants.WINDOW_H + Constants.GAP);
        }

        frame.setVisible(true);

        // Первичная генерация, чтобы не было "пустого" старта
        generator.update(model, panel.getWidth(), panel.getHeight());
        panel.repaint();

        // Обновление каждые TIMER_MS
        Timer timer = new Timer(Constants.TIMER_MS, e -> {
            generator.update(model, panel.getWidth(), panel.getHeight());
            panel.repaint();
        });
        timer.start();
    }

    private static String titleFor(AppArgs a) {
        String pos = (a.pos == Pos.TOP) ? "TOP" : "BOTTOM";
        String mode = a.filled ? "FILLED" : "OUTLINE";
        return "Rect process: " + pos + " / " + mode;
    }

    private static AppArgs parseArgs(String[] args) {
        Pos pos = Pos.TOP;
        boolean filled = true;

        for (String arg : args) {
            if (arg == null) continue;
            if (arg.startsWith("--pos=")) {
                String v = arg.substring("--pos=".length()).trim().toLowerCase();
                pos = v.equals("bottom") ? Pos.BOTTOM : Pos.TOP;
            } else if (arg.startsWith("--mode=")) {
                String v = arg.substring("--mode=".length()).trim().toLowerCase();
                filled = v.equals("filled");
                if (v.equals("outline")) filled = false;
            }
        }
        return new AppArgs(pos, filled);
    }

    private static final class AppArgs {
        final Pos pos;
        final boolean filled;

        AppArgs(Pos pos, boolean filled) {
            this.pos = pos;
            this.filled = filled;
        }
    }
}


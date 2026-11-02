import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;



public class AssignmentReminderWithTimer {
    private static final Color BACKGROUND = new Color(38, 42, 58);
    private static final Color SURFACE = new Color(54, 60, 80);
    private static final Color ACCENT = new Color(137, 180, 250);
    private static final long NANOS_PER_SECOND = 1_000_000_000L;

    private final JTextField input = new JTextField();
    private final JFrame window = new JFrame("Assignment Reminder");
    private final JLabel timeDisplay = new JLabel("30:00", SwingConstants.CENTER);
    private final JLabel timerStatus = new JLabel("Ready for a 30-minute focus session.", SwingConstants.CENTER);
    private final JLabel reminderStatus = new JLabel("Alert enabled", SwingConstants.CENTER);
    private final JToggleButton reminderToggle = new JToggleButton("Alert when time is up", true);
    private final JButton startButton = new JButton("Start");
    private final JButton pauseButton = new JButton("Pause");
    private final Timer refreshTimer = new Timer(200, event -> updateTimerDisplay());

    private long remainingNanos;
    private long deadlineNanos;
    private boolean running;

    private AssignmentReminderWithTimer() {
        createWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AssignmentReminderWithTimer::new);
    }

    private void createWindow() {
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon icon = new ImageIcon("icon.png");

        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JLabel heading = new JLabel("Assignment Reminder", SwingConstants.CENTER);
        heading.setForeground(Color.WHITE);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 22f));
        content.add(heading, BorderLayout.NORTH);

        JPanel timerPanel = new JPanel(new BorderLayout(0, 12));
        timerPanel.setBackground(SURFACE);
        timerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(76, 84, 108)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        timeDisplay.setForeground(Color.WHITE);
        timeDisplay.setFont(timeDisplay.getFont().deriveFont(Font.BOLD, 56f));
        timerStatus.setForeground(Color.WHITE);
        timerStatus.setFont(timerStatus.getFont().deriveFont(14f));
        timerPanel.add(timeDisplay, BorderLayout.CENTER);
        timerPanel.add(timerStatus, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        buttons.setBackground(SURFACE);
        buttons.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        styleButton(startButton, true);
        startButton.addActionListener(event -> startTimer());
        styleButton(pauseButton, false);
        pauseButton.addActionListener(event -> pauseOrResumeTimer());
        buttons.add(startButton);
        buttons.add(pauseButton);
        timerPanel.add(buttons, BorderLayout.SOUTH);
        content.add(timerPanel, BorderLayout.CENTER);

        JPanel reminderPanel = new JPanel(new BorderLayout(0, 8));
        reminderPanel.setBackground(BACKGROUND);
        reminderPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        JLabel reminderLabel = new JLabel("What do you need to remember?", SwingConstants.LEFT);
        reminderLabel.setForeground(Color.WHITE);
        reminderLabel.setFont(reminderLabel.getFont().deriveFont(Font.BOLD, 14f));
        input.setFont(input.getFont().deriveFont(14f));
        input.setToolTipText("Enter the assignment or task for this reminder");
        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 108, 132)),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)));
        reminderToggle.setOpaque(false);
        reminderToggle.setForeground(Color.WHITE);
        reminderStatus.setForeground(ACCENT);
        reminderStatus.setHorizontalAlignment(SwingConstants.RIGHT);
        reminderToggle.addActionListener(event -> reminderStatus.setText(
                reminderToggle.isSelected() ? "Alert enabled" : "Alert disabled"));

        JPanel reminderOptions = new JPanel(new BorderLayout());
        reminderOptions.setBackground(BACKGROUND);
        reminderOptions.add(reminderToggle, BorderLayout.WEST);
        reminderOptions.add(reminderStatus, BorderLayout.EAST);
        reminderPanel.add(reminderLabel, BorderLayout.NORTH);
        reminderPanel.add(input, BorderLayout.CENTER);
        reminderPanel.add(reminderOptions, BorderLayout.SOUTH);
        content.add(reminderPanel, BorderLayout.SOUTH);

        window.setContentPane(content);
        window.setMinimumSize(new Dimension(440, 400));
        window.setSize(500, 450);
        window.setLocationRelativeTo(null);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                refreshTimer.stop();
            }
        });
        window.setVisible(true);
        window.setIconImage(icon.getImage());
    }

    private static void styleButton(JButton button, boolean primary) {
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        if (primary) {
            button.setBackground(ACCENT);
            button.setForeground(BACKGROUND);
            button.setOpaque(true);
        } else {
            button.setForeground(Color.WHITE);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(125, 135, 160)),
                    BorderFactory.createEmptyBorder(9, 21, 9, 21)));
        }
    }


    // x = miuntes, y = seconds: x * y * nanos
    private void startTimer() {
        remainingNanos = 30 * 60 * NANOS_PER_SECOND;
        deadlineNanos = System.nanoTime() + remainingNanos;
        running = true;
        timerStatus.setText("Focus time remaining");
        startButton.setText("Restart");
        pauseButton.setText("Pause");
        refreshTimer.start();
        updateTimerDisplay();
    }

    private void pauseOrResumeTimer() {
        if (running) {
            remainingNanos = Math.max(0, deadlineNanos - System.nanoTime());
            running = false;
            refreshTimer.stop();
            timerStatus.setText("Paused");
            pauseButton.setText("Resume");
            updateTimerDisplay();
        } else if (remainingNanos > 0) {
            deadlineNanos = System.nanoTime() + remainingNanos;
            running = true;
            timerStatus.setText("Focus time remaining");
            pauseButton.setText("Pause");
            refreshTimer.start();
        } else {
            timerStatus.setText("Press Start to begin a 30-minute session.");
        }
    }

    private void updateTimerDisplay() {
        if (running) {
            remainingNanos = Math.max(0, deadlineNanos - System.nanoTime());
            if (remainingNanos == 0) {
                running = false;
                refreshTimer.stop();
                timeDisplay.setText("00:00");
                timerStatus.setText("Session complete");
                pauseButton.setText("Pause");
                if (reminderToggle.isSelected()) {
                    String task = input.getText().trim();
                    String message = task.isEmpty()
                            ? "Your 30-minute focus session is complete."
                            : "Time to check: " + task;
                    JOptionPane.showMessageDialog(
                            window, message, "Assignment Reminder",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                return;
            }
        }

        long secondsLeft = (remainingNanos + NANOS_PER_SECOND - 1) / NANOS_PER_SECOND;
        timeDisplay.setText(formatTime(secondsLeft));
    }

    private static String formatTime(long totalSeconds) {
        return String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60);
    }
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

    //This is the fully original version of the application that the AI made
    //I Forgot that JFrames existed at first and asked them to use html and javascript at first.
    //But upon remembering, I asked the ai to convert what I had 


public class AssignmentReminder {
    private static final Color BACKGROUND = new Color(68, 68, 102);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AssignmentReminder::createWindow);
    }

    private static void createWindow() {
        JFrame window = new JFrame("Assignment Reminder");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JLabel heading = new JLabel("Assignment Reminder!", SwingConstants.CENTER);
        heading.setForeground(Color.WHITE);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        content.add(heading, BorderLayout.NORTH);

        JPanel controls = new JPanel(new GridLayout(2, 1, 0, 12));
        controls.setBackground(BACKGROUND);
        JToggleButton toggle = new JToggleButton("Click here to toggle reminders!", true);
        JLabel status = new JLabel("Reminders are on.", SwingConstants.CENTER);
        status.setForeground(Color.WHITE);
        toggle.addActionListener(
                event -> status.setText(toggle.isSelected() ? "Reminders are on." : "Reminders are off."));
        controls.add(toggle);
        controls.add(status);
        content.add(controls, BorderLayout.CENTER);

        

        window.setContentPane(content);
        window.setSize(480, 260);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}

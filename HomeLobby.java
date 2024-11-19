

package ludolegends;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HomeLobby extends JFrame {

    private boolean soundEffectsEnabled = true;
    private boolean backgroundMusicEnabled = true;

    // Constructor for HomeLobby frame
    public HomeLobby() {
        setTitle("Game Lobby");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color for the frame
        getContentPane().setBackground(new Color(242, 242, 242));

        // Background Panel with a solid color
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Call parent method to ensure correct painting behavior

                // Load and draw the background image
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/bgi.png"));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        backgroundPanel.setBackground(new Color(242, 242, 242)); // Same light gray background

        // Sound control panel in the upper right
        JPanel soundControlPanel = new JPanel();
        soundControlPanel.setLayout(new BoxLayout(soundControlPanel, BoxLayout.Y_AXIS));
        soundControlPanel.setBackground(new Color(242, 242, 242));
        soundControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));

        // Create circular sound control buttons
        JButton soundEffectsButton = createCircleButton("🔊", new Color(66, 169, 255), e -> {
            soundEffectsEnabled = !soundEffectsEnabled;
            ((JButton)e.getSource()).setText(soundEffectsEnabled ? "🔊" : "🔈");
        });

        JButton backgroundMusicButton = createCircleButton("♪", new Color(79, 157, 88), e -> {
            backgroundMusicEnabled = !backgroundMusicEnabled;
            ((JButton)e.getSource()).setText(backgroundMusicEnabled ? "♪" : "♫");
        });

        soundControlPanel.add(soundEffectsButton);
        soundControlPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
        soundControlPanel.add(backgroundMusicButton);

        // Title label with alternating colors
        JLabel titleLabel = new JLabel("<html><div style='text-align:center; font-family: \"PressStart2P-Regular.ttf\", monospace;'>"
            + "<span style='font-size:50px; color:#4285F4;'>Ludo Legends</span><br/>"
            + "<span style='font-size:36px; color:#66B4FF;'>T</span>"
            + "<span style='font-size:36px; color:#66BB6A;'>h</span>"
            + "<span style='font-size:36px; color:#DE0A26;'>e </span>"
            + "<span style='font-size:36px; color:#F4B400;'>B</span>"
            + "<span style='font-size:36px; color:#66B4FF;'>a</span>"
            + "<span style='font-size:36px; color:#66BB6A;'>t</span>"
            + "<span style='font-size:36px; color:#DE0A26;'>t</span>"
            + "<span style='font-size:36px; color:#F4B400;'>l</span>"
            + "<span style='font-size:36px; color:#66B4FF;'>e </span>"
            + "<span style='font-size:36px; color:#66BB6A;'>o</span>"
            + "<span style='font-size:36px; color:#DE0A26;'>f </span>"
            + "<span style='font-size:36px; color:#F4B400;'>C</span>"
            + "<span style='font-size:36px; color:#66B4FF;'>o</span>"
            + "<span style='font-size:36px; color:#DE0A26;'>l</span>"
            + "<span style='font-size:36px; color:#F4B400;'>o</span>"
            + "<span style='font-size:36px; color:#66B4FF;'>r</span>"
            + "<span style='font-size:36px; color:#66BB6A;'>s</span>"
            + "</div></html>", JLabel.CENTER);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0)); // Add space below title
        backgroundPanel.add(titleLabel);

        // Add buttons
        backgroundPanel.add(createStyledButton("Start / Play", new Color(66, 169, 255), e -> {
            JOptionPane.showMessageDialog(null, "Game Starting!");
        }));

        backgroundPanel.add(createStyledButton("Game Instructions", new Color(79, 157, 88), e -> {
            String instructions = "1. Roll the dice to move your token.\n"
                                + "2. Reach the target and be the first to win!\n"
                                + "3. Click on your token to move.";
            JOptionPane.showMessageDialog(null, instructions, "Instructions", JOptionPane.INFORMATION_MESSAGE);
        }));

        backgroundPanel.add(createStyledButton("Exit", new Color(194, 53, 29), e -> {
            System.exit(0);
        }));

        // Add the background panel to the frame
        add(backgroundPanel, BorderLayout.CENTER);
        add(soundControlPanel, BorderLayout.EAST);
    }

    // Method to create a circular button for sound controls
    private JButton createCircleButton(String text, Color color, ActionListener action) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color.brighter());
                } else {
                    g2d.setColor(color);
                }
                g2d.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(40, 40));
        button.setMaximumSize(new Dimension(40, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(action);

        return button;
    }

    // Method to create a styled button with rounded corners and dynamic color changes
    private JButton createStyledButton(String text, Color color, ActionListener action) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                if (getModel().isPressed()) {
                    g2d.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color.brighter());
                } else {
                    g2d.setColor(color);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 50));
        button.setMaximumSize(new Dimension(250, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(true);
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       
        button.addActionListener(action);

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeLobby().setVisible(true));
    }
}



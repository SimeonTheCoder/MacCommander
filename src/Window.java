import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Window extends JPanel {
    JFrame frame = new JFrame();
    JFileChooser selectDir;
    JFileChooser selectDir2;

    BorderLayout layout = new BorderLayout();
    GridLayout layout2 = new GridLayout(12, 1);

    public Window() {

    }

    public void addDirectoryDisplay() {
        selectDir = new JFileChooser();
        selectDir.setName("Select File");

        this.add(selectDir, BorderLayout.WEST);

        selectDir2 = new JFileChooser();
        selectDir2.setName("Select Directory");
        selectDir2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        this.add(selectDir2, BorderLayout.EAST);
    }

    public void show(String title, int sizeX, int sizeY) {
        this.setLayout(layout);

        frame.setTitle(title);

        frame.setSize(sizeX, sizeY);

        this.addDirectoryDisplay();

        JButton swapButton = new JButton("<->");
        swapButton.addActionListener(new ButtonListener(Event.SWAP));

        JButton copyButton = new JButton("COPY");
        copyButton.addActionListener(new ButtonListener(Event.COPY));

        JButton deleteButton = new JButton("DEL");
        deleteButton.addActionListener(new ButtonListener(Event.DEL));

        JButton moveButton = new JButton("MOVE");
        moveButton.addActionListener(new ButtonListener(Event.MOVE));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(layout2);

        buttonPanel.add(swapButton);
        buttonPanel.add(copyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(moveButton);

        this.add(buttonPanel, BorderLayout.CENTER);

        frame.add(this);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void copy(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;

            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();

            os.close();
        }
    }

    public void swapSides() {
        remove(selectDir);
        remove(selectDir2);

        JFileChooser tmp = selectDir;

        selectDir = selectDir2;
        selectDir.setFileSelectionMode(JFileChooser.FILES_ONLY);

        selectDir2 = tmp;
        selectDir2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        add(selectDir, BorderLayout.WEST);
        add(selectDir2, BorderLayout.EAST);
    }

    public void update() throws InterruptedException {
        frame.setSize(frame.getWidth() - 1, frame.getHeight());

        TimeUnit.MILLISECONDS.sleep(10);

        frame.setSize(frame.getWidth() + 1, frame.getHeight());
    }

    static class ButtonListener implements ActionListener {
        public Event event;

        public ButtonListener(Event event) {
            this.event = event;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Main.event = event;
        }
    }
}

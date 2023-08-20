import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main extends JFrame {
    private static final String[] PHONETICS = {
            "æ", "ɑː", "ɔː", "e", "iː", "ɪ", "ə", "ʊ", "uː", "ɜː", "ʌ", "ɒ", "aɪ", "aʊ", "ɔɪ", "eɪ", "əʊ", "ɪə", "eə", "ʊə",
            "/f/", "/s/", "/ʃ/", "/θ/", "/h/", "/v/", "/z/", "/ʒ/", "/ð/",
  // 辅英
            "p", "b", "tː", "d", "k", "g", "m", "n", "ŋ", "f", "v", "θ", "ð", "s", "z", "ʃ", "ʒ", "h", "tʃ", "dʒ", "w"
            , "r", "j", "l"
    };

    private JLabel phoneticLabel;
    private JButton nextButton;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private Timer timer;
    private int score;
    private int currentIndex;
    private int timeRemaining;
    private List<String> phoneticsList;

    public Main() {
        setTitle("Phonetics Exercise Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        phoneticLabel = new JLabel();
        phoneticLabel.setFont(new Font("Arial", Font.BOLD, 36));
        phoneticLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(phoneticLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateScore(1);
                showNextPhonetic();
            }
        });
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1, 2));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        infoPanel.add(scoreLabel);

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        infoPanel.add(timeLabel);

        add(infoPanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);

        score = 0;
        currentIndex = 0;
        timeRemaining = 10;
        phoneticsList = Arrays.asList(PHONETICS);
        Collections.shuffle(phoneticsList);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                if (timeRemaining <= 0) {
                    showNextPhonetic();
                } else {
                    timeLabel.setText("Time Remaining: " + timeRemaining + " seconds");
                }
            }
        });
    }

    public void start() {
        showNextPhonetic();
        timer.start();
        setVisible(true);
    }

    private void showNextPhonetic() {
        if (currentIndex < phoneticsList.size()) {
            String phonetic = phoneticsList.get(currentIndex);
            phoneticLabel.setText(phonetic);
            currentIndex++;
            timeRemaining = 10;
            timeLabel.setText("Time Remaining: " + timeRemaining + " seconds");
        } else {
            Collections.shuffle(phoneticsList);
            currentIndex = 0;
            score = 0;
            scoreLabel.setText("Score: 0");
            showNextPhonetic();
        }
    }

    private void updateScore(int delta) {
        score += delta;
        scoreLabel.setText("Score: " + score);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main tool = new Main();
                tool.start();
            }
        });
    }
}
//https://github.com/ChrisAye
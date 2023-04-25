package interfata;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class CalcView extends JFrame {
    private final JTextField m_userInputTf = new JTextField(20);
    private final JTextField m_userInputTf1 = new JTextField(20);
    private final JTextField m_userInputTf2 = new JTextField(20);
    private final JTextField m_userInputTf3 = new JTextField(20);
    private final JTextField m_userInputTf4 = new JTextField(20);
    private final JTextField m_userInputTf5 = new JTextField(20);
    private final JTextField m_userInputTf6 = new JTextField(20);
    private final JButton m_multiplyBtn = new JButton(" START");
    private final JTextArea m_outputArea = new JTextArea(20, 80);
    private final JScrollPane m_outputScrollPane = new JScrollPane(m_outputArea);
    private final CalcModel m_model;
    CalcView(CalcModel model) {
        m_model = model;
        m_model.setValue(CalcModel.INITIAL_VALUE);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("Număr clienți"));
        inputPanel.add(m_userInputTf);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(new JLabel("Număr cozi"));
        inputPanel.add(m_userInputTf1);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(new JLabel("Timpul maxim de simualre"));
        inputPanel.add(m_userInputTf2);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(new JLabel("Timpul maxim de sosire"));
        inputPanel.add(m_userInputTf3);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(new JLabel("Timpul minim de sosire"));
        inputPanel.add(m_userInputTf4);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(new JLabel("Timpul maxim de servire"));
        inputPanel.add(m_userInputTf5);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(new JLabel("Timpul minim de servire"));
        inputPanel.add(m_userInputTf6);

        JPanel operatii = new JPanel();
        operatii.setLayout(new BoxLayout(operatii, BoxLayout.Y_AXIS));
        operatii.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        operatii.add(m_multiplyBtn);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.add(new JLabel("Output:"));
        outputPanel.add(m_outputScrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        content.add(inputPanel);
        content.add(Box.createRigidArea(new Dimension(20, 0)));
        content.add(operatii);
        content.add(Box.createRigidArea(new Dimension(20, 0)));
        content.add(outputPanel);

        this.setContentPane(content);
        this.pack();
        this.setTitle("Queue management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    String getUserInput() {
        return m_userInputTf.getText();
    }
    String getUserInput1() {
        return m_userInputTf1.getText();
    }
    String getUserInput2() {
        return m_userInputTf2.getText();
    }
    String getUserInput3() {
        return m_userInputTf3.getText();
    }
    String getUserInput4() {
        return m_userInputTf4.getText();
    }
    String getUserInput5() {
        return m_userInputTf5.getText();
    }
    String getUserInput6() {
        return m_userInputTf6.getText();
    }
    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
    void addMultiplyListener(ActionListener mal) {
        m_multiplyBtn.addActionListener(mal);
    }
    void appendOutput(String output) {
        String[] outputArray = output.split("(?=Time:)");
        int delay = 2000;
        final int[] a = {0};
             Timer timer = new Timer(delay, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (a[0] < outputArray.length) {
                     m_outputArea.append(outputArray[a[0]] + "\n");
                      a[0]++;
                      } else {
                     ((Timer) evt.getSource()).stop();
                 }
             }
         });
        timer.start();
    }
}

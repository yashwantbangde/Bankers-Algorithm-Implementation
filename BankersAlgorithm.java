import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankersAlgorithm extends JFrame implements ActionListener {

    private JLabel[] allocationLabel, maxLabel, needLabel, availableLabel, processLabel;
    private JTextField[][] allocationTF, maxTF, needTF;
    private JTextField[] availableTF;
    private JButton calculateButton;
    private int n, m;
    private int[][] allocation, max, need, available;

    public BankersAlgorithm() {
        //super(BankersAlgorithm);

        // Initialize the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Get the number of processes and resources from the user
        n = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number of processes:"));
        m = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number of resources:"));

        // Create the labels for the input fields
        allocationLabel = new JLabel[n];
        maxLabel = new JLabel[n];
        needLabel = new JLabel[n];
        availableLabel = new JLabel[m];
        processLabel = new JLabel[n];
        for (int i = 0; i < n; i++) {
            allocationLabel[i] = new JLabel("Allocation for P" + i + ": ");
            maxLabel[i] = new JLabel("Max for P" + i + ": ");
            needLabel[i] = new JLabel("Need for P" + i + ": ");
            processLabel[i] = new JLabel("P" + i);
        }
        for (int i = 0; i < m; i++) {
            availableLabel[i] = new JLabel("Available R" + i + ": ");
        }

        // Create the input fields
        allocationTF = new JTextField[n][m];
        maxTF = new JTextField[n][m];
        needTF = new JTextField[n][m];
        availableTF = new JTextField[m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                allocationTF[i][j] = new JTextField(5);
                maxTF[i][j] = new JTextField(5);
                needTF[i][j] = new JTextField(5);
            }
        }
        for (int i = 0; i < m; i++) {
            availableTF[i] = new JTextField(5);
        }

        // Create the calculate button
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        // Create the layout for the JFrame
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel centerPanel = new JPanel(new GridLayout(n + 1, m + 2));
        JPanel bottomPanel = new JPanel(new FlowLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add the labels and input fields to the layout
        topPanel.add(new JLabel("Banker's Algorithm"));
        bottomPanel.add(calculateButton);
        for (int i = 0; i < m; i++) {
            centerPanel.add(availableLabel[i]);
            centerPanel.add(availableTF[i]);
        }
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        for (int i = 0; i < n; i++) {
            centerPanel.add(processLabel[i]);
        }
        for (int i = 0; i < n; i++) {
        centerPanel.add(allocationLabel[i]);
        for (int j = 0; j < m; j++) {
            centerPanel.add(allocationTF[i][j]);
        }
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
    }
    for (int i = 0; i < n; i++) {
        centerPanel.add(maxLabel[i]);
        for (int j = 0; j < m; j++) {
            centerPanel.add(maxTF[i][j]);
        }
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
    }
    for (int i = 0; i < n; i++) {
        centerPanel.add(needLabel[i]);
        for (int j = 0; j < m; j++) {
            centerPanel.add(needTF[i][j]);
        }
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
    }

    setVisible(true);
}

public void actionPerformed(ActionEvent e) {
    if (e.getSource() == calculateButton) {
        // Get the input values
        allocation = new int[n][m];
        max = new int[n][m];
        need = new int[n][m];
        available = new int[1][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                allocation[i][j] = Integer.parseInt(allocationTF[i][j].getText());
                max[i][j] = Integer.parseInt(maxTF[i][j].getText());
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
        for (int i = 0; i < m; i++) {
            available[0][i] = Integer.parseInt(availableTF[i].getText());
        }

        // Run the Banker's Algorithm
        boolean[] finish = new boolean[n];
        int[] safeSequence = new int[n];
        int count = 0;
        while (count < n) {
            boolean found = false;
            for (int i = 0; i < n; i++) {
                if (!finish[i]) {
                    int j;
                    for (j = 0; j < m; j++) {
                        if (need[i][j] > available[0][j]) {
                            break;
                        }
                    }
                    if (j == m) {
                        for (int k = 0; k < m; k++) {
                            available[0][k] += allocation[i][k];
                        }
                        safeSequence[count++] = i;
                        finish[i] = true;
                        found = true;
                    }
                }
            }
            if (!found) {
                break;
            }
        }

        // Display the results
        if (count == n) {
            JOptionPane.showMessageDialog(null, "Safe Sequence: " + getSafeSequence(safeSequence));
        } else {
            JOptionPane.showMessageDialog(null, "System is in an unsafe state.");
        }
    }
}

private String getSafeSequence(int[] safeSequence) {
    String sequence = "";
    for (int i = 0; i < safeSequence.length; i++) {
        sequence += "P" + safeSequence[i] + " ";
    }
    return sequence;
}

public static void main(String[] args) {
    new BankersAlgorithm();
}

}
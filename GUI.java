import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    Border borderline;
    JFrame gui;
    JLabel instructionsLabel;
    JLabel scoreLabel;
    JPanel aiPanel;
    ImageIcon img;
    JLabel imgLabel;
    JTextArea aiLabel;
    JPanel userPanel;
    JTextField userInput;
    JButton submitButton;

    public GUI(String model){ 
        ChatGPT ai = new ChatGPT(model); 
        
        borderline = BorderFactory.createLineBorder(Color.GRAY);
        gui = new JFrame("Therapy AI");
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setLayout(new BorderLayout());
        gui.setBackground(Color.WHITE);
        gui.getContentPane().setBackground(Color.WHITE);

        // Instructions Label with HTML for line breaks
        instructionsLabel = new JLabel();
        instructionsLabel.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 16));
        instructionsLabel.setHorizontalAlignment(JLabel.LEFT); 
        instructionsLabel.setText("<html>Welcome to TherapyAI, your personalized therapy chatbot.<br><br>" +
                "To get started, please type a recent experience that you feel was emotionally charged.<br>" +
                "Before clicking submit, please also include how that experience made you feel on an emotional level.<br><br>" +
                "Repeat this process three times, and the AI will assign you a mental score (0-100) and offer feedback based on your reflections.<br><br>" +
                "Take your time and reflect carefully. We're here to help you!</html>");

        // Score Label
        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("DejaVu Math TeX Gyre", Font.ITALIC, 12));
        scoreLabel.setAlignmentX(JLabel.CENTER);
        scoreLabel.setText("Mental Score: 0");
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Added padding around the score label

        aiPanel = new JPanel();
        aiPanel.setLayout(new BorderLayout());
        aiPanel.setBackground(Color.WHITE);

        try {
            img = new ImageIcon(getClass().getResource("therapist.png"));
        } catch(Exception e) {
            System.out.println("Image could not be found!");
        }
        imgLabel = new JLabel(img);

        aiLabel = new JTextArea("Here I will give you feedback after you share your experience in the box below and hit submit.");
        aiLabel.setFont(new Font("DejaVu Math TeX Gyre", Font.PLAIN, 16));
        aiLabel.setLineWrap(true);
        aiLabel.setWrapStyleWord(true); 
        aiLabel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        aiLabel.setBorder(borderline);
        aiLabel.setBackground(Color.WHITE);
        aiLabel.setEditable(false); 

        userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        userPanel.setBackground(Color.WHITE);

        // Increase the size of the text box
        userInput = new JTextField();
        userInput.setFont(new Font("DejaVu Math TeX Gyre", Font.PLAIN, 16));  // Increased font size
        userInput.setPreferredSize(new Dimension(400, 60));  // Set preferred size for the text field
        userInput.setHorizontalAlignment(JTextField.CENTER);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("DejaVu Math TeX Gyre", Font.PLAIN, 14));
        submitButton.setPreferredSize(new Dimension(120, 50));

        aiPanel.add(instructionsLabel, BorderLayout.NORTH);
        aiPanel.add(scoreLabel, BorderLayout.EAST);  // Score label in the east side
        aiPanel.add(aiLabel, BorderLayout.CENTER);
        aiPanel.add(imgLabel, BorderLayout.WEST);

        userPanel.add(userInput, BorderLayout.CENTER);
        userPanel.add(submitButton, BorderLayout.EAST);

        gui.add(aiPanel, BorderLayout.NORTH);
        gui.add(userPanel, BorderLayout.SOUTH);
        gui.setVisible(true);

        
        

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userMessage = userInput.getText();
                userInput.setText("");
                //System.out.println("User: " + userMessage);

                String response = ai.getGPT("You are an ai therapist, I am about to tell you a recent experience and how it made me feel; then I want you to give me some feedback as a therapist in about 300 to 320 words, no wiggle room (Please respond in normal formatting with no bulletpoints, no line breaks using backslash n backslash, just text); At the end of it all I want you to asign me a mental score of 0-100 (0 depressed, 100 super happy; try to be very precise, for example me saying im depressed should be like 10-30 while saying sad should be around 40 and saying happy should be 80-100), please put the score at the complete end of the the text with no punctiation after and dont indicate this is my mental score it should just be like text-period-space-number; " + userMessage);
                aiLabel.setText(response.substring(response.indexOf(":")+3, response.length()-4));
            
                //System.out.println(response.substring(response.length()-3, response.length()));
                int currentMentalScore = Integer.parseInt(response.substring(response.length()-3, response.length()-1));

                
                scoreLabel.setText("Mental Score: " + currentMentalScore);
                gui.repaint();


                
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

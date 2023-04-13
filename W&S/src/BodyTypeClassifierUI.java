import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BodyTypeClassifierUI extends JFrame implements ActionListener {

    private JPanel panel;
    private JLabel nameLabel, weightLabel, heightLabel, resultLabel, shoeSizeLabel;
    private JTextField nameText, weightText, feetText, inchesText;
    private JButton classifyButton;
    private JComboBox<String> genderBox;

    public BodyTypeClassifierUI() {
        // Set up the window
        setTitle("Body Type Classifier");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the input fields and labels
        nameLabel = new JLabel("Name:");
        nameText = new JTextField(20);
        weightLabel = new JLabel("Weight (in lbs):");
        weightText = new JTextField(20);
        heightLabel = new JLabel("Height (in feet and inches):");
        feetText = new JTextField(2);
        inchesText = new JTextField(2);
        classifyButton = new JButton("Classify Body Type");
        
        // Add an action listener to the classify button
        classifyButton.addActionListener(this);
        
        // Create the result label (but leave the text blank for now)
        resultLabel = new JLabel("Body Type Classification:");
        
        // Create the shoe size label
        shoeSizeLabel = new JLabel("Estimated Shoe Size:");
        
        // Create the gender combo box
        String[] genderOptions = {"Male", "Female"};
        genderBox = new JComboBox<>(genderOptions);
        genderBox.setSelectedIndex(0);
        
        // Set up the panel with the components
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(weightLabel);
        panel.add(weightText);
        panel.add(heightLabel);
        panel.add(feetText);
        panel.add(inchesText);
        panel.add(genderBox);
        panel.add(classifyButton);
        panel.add(resultLabel);
        panel.add(shoeSizeLabel);
        
        // Set the background color of the panel to light blue
        panel.setBackground(new Color(173, 216, 230));
        
        // Add the panel to the window
        add(panel);
        
        // Show the window
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        // Get the input values from the text fields and combo box
        String name = nameText.getText();
        int weight = Integer.parseInt(weightText.getText());
        int feet = Integer.parseInt(feetText.getText());
        int inches = Integer.parseInt(inchesText.getText());
        String gender = (String)genderBox.getSelectedItem();
        
        // Determine the body type classification based on the given weight/height ratio
        String result = classifyBodyType(weight, feet, inches);
        
        // Determine the shoe size based on foot length
        double footLength = calculateFootLength(feet, inches);
        int shoeSize = estimateShoeSize(footLength, gender);
        
        // Set the value of the result and shoe size labels
        resultLabel.setText("Body Type Classification: " + result);
        shoeSizeLabel.setText("Estimated Shoe Size: " + shoeSize);
    }
    
    private static String classifyBodyType(int weight, int feet, int inches) {
        // Convert the height to inches
        int heightInches = feet * 12 + inches;
        
        // Calculate the weight/height ratio (in kg/m^2) using the given formula
        double ratio = weight / ((heightInches / 39.37) * (heightInches / 39.37));
        
        // Determine the body type classification based on the calculated ratio
        if (ratio < 50) {
            return "Low";
        } else if (ratio < 80) {
            return "Normal";
        } else {
            return "High";
        }
    }
    
    private static double calculateFootLength(int feet, int inches) {
        // Convert the foot length to centimeters
        double length = (feet * 1.5 + inches) * 1.5;
        
        // Adjust the length for gender differences
        if (feet >= 6) { // Assume male if shoe size is at least 6
            length += 1.8;
        } else { // Assume female if shoe size is less than 6
            length += 1.3;
        }
        
        return length;
    }
    
    public static int estimateShoeSize(double footLength, String gender) {
        double size;
        
        // Determine size based on gender
        if (gender.equals("Male")) { // Male
            size = footLength / 2.54 / 0.26;
        } else { // Female
            size = footLength / 2.54 / 0.24;
        }
        
        return (int)(size + .5); // Round to nearest half size
    }
    
    public static void main(String[] args) {
        BodyTypeClassifierUI gui = new BodyTypeClassifierUI();
    }
}
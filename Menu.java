import javax.swing.*;
import java.awt.event.*;

public class Menu implements ActionListener {
    JButton button;
    public void Setup() {
        JFrame frame = new JFrame();
        button = new JButton("up");
        button.addActionListener((ActionListener) this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(button);
        frame.setSize(200,200);
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent event) {
        button.setText("I was Pressed!");
    }
}
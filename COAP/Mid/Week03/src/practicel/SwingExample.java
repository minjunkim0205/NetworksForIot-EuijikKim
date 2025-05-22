package practicel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingExample extends JFrame{
    /*Field */ 
    JLabel label;
    JTextField text_field;
    JComboBox combo_box;
    JRadioButton radio_button_man, radio_button_wonam;
    JTextArea text_area;
    JButton button;
    /*Construct */
    SwingExample() {
        this.setTitle("Window Title");
        //Label
        label = new JLabel("Name: ");
        label.setBounds(10, 10, 100, 20);
        add(label);
        //TextField
        text_field = new JTextField(20);
        text_field.setBounds(50, 10, 200, 20);
        add(text_field);
        //ComboBox
        String[] job_list = {"None", "Student", "Curr", "Dev", "see"};
        combo_box = new JComboBox(job_list);
        JPanel panel_job = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_job.setBounds(5, 40, 250, 40);
        panel_job.add(combo_box);
        add(panel_job);
        //RadioButton
        JPanel panel_gender = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radio_button_man = new JRadioButton("Man", true);
        radio_button_wonam = new JRadioButton("Wonam", true);
        ButtonGroup group_gender = new ButtonGroup();
        group_gender.add(radio_button_man);
        group_gender.add(radio_button_wonam);
        panel_gender.add(radio_button_man);
        panel_gender.add(radio_button_wonam);
        panel_gender.setBounds(5, 80, 100, 30);
        add(panel_gender);
        //TextArea
        text_area = new JTextArea();
        JScrollPane scroll_pane = new JScrollPane(text_area);
        scroll_pane.setBounds(10, 110, 250, 100);
        add(scroll_pane);
        //Button
        button = new JButton("PRINT");
        button.setBounds(10, 220, 120, 20);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == button){
                    text_area.setText("");
                    text_area.append("Name : " + text_area.getText() + "\n");
                    text_area.append("Job : " + combo_box.getSelectedItem().toString() + "\n");
                    if(radio_button_man.isSelected()){
                        text_area.append("Gender : " + radio_button_man.getText() + "\n");
                    }
                    else{
                        text_area.append("Gender : " + radio_button_wonam.getText() + "\n");
                    }
                }
            }
        });
        add(button);
        //Frame Settings
        setSize(300, 300);
        setLayout(null);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new SwingExample();
    }
}
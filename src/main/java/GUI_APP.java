import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI_APP extends JFrame{

    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton STARTButton;
    private JButton SHORTEST_QUEUEButton;
    private JButton RESETButton;
    private JButton SHORTEST_TIMEButton;
    private JScrollPane scrollPaneClients;
    private JScrollPane scrollPaneQueues;
    private JCheckBox checkBoxQueue;
    private JCheckBox checkBoxTime;
    private JTextArea textAreaClients;
    private JTextArea textAreaQueues;

    public GUI_APP(){
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textAreaClients = new JTextArea();
        textAreaQueues = new JTextArea();
        this.scrollPaneClients.setViewportView(textAreaClients);
        this.scrollPaneQueues.setViewportView(textAreaQueues);
        this.pack();
        this.setVisible(true);
    }

    public JTextArea getTextAreaClients() {
        return textAreaClients;
    }

    public JTextArea getTextAreaQueues() {
        return textAreaQueues;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    public JTextField getTextField6() {
        return textField6;
    }

    public JTextField getTextField7() {
        return textField7;
    }

    public JButton getSTARTButton() {
        return STARTButton;
    }

    public JButton getSHORTEST_QUEUEButton() {
        return SHORTEST_QUEUEButton;
    }

    public JButton getRESETButton() {
        return RESETButton;
    }

    public JButton getSHORTEST_TIMEButton() {
        return SHORTEST_TIMEButton;
    }

    public JCheckBox getCheckBoxQueue() {
        return checkBoxQueue;
    }

    public JCheckBox getCheckBoxTime() {
        return checkBoxTime;
    }

    public void addSTARTListener(ActionListener listenForSTARTButton) {
        STARTButton.addActionListener(listenForSTARTButton);
    }

    public void addRESETListener(ActionListener listenForRESETButton) {
        RESETButton.addActionListener(listenForRESETButton);
    }

    public void addSHORTEST_QUEUEListener(ActionListener listenForSHORTEST_QUEUEButton) {
        SHORTEST_QUEUEButton.addActionListener(listenForSHORTEST_QUEUEButton);
    }

    public void addSHORTEST_TIMEListener(ActionListener listenForSHORTEST_TIMEButton) {
        SHORTEST_TIMEButton.addActionListener(listenForSHORTEST_TIMEButton);
    }

    public void displayErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(this, errorMessage);
    }

}

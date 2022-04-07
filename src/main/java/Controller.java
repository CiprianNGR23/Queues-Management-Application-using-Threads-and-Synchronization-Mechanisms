import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Controller implements ActionListener{

    private GUI_APP view;
    private SimulationManager simManager;
    private SelectionPolicy policy;
    private PrintWriter obj;

    public Controller(GUI_APP view) {
        this.view = view;

        try {
            this.obj = new PrintWriter("printLogsTest.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.view.addSTARTListener(this);
        this.view.addRESETListener(this);
        this.view.addSHORTEST_QUEUEListener(this);
        this.view.addSHORTEST_TIMEListener(this);

    }

    public void updateView(ArrayBlockingQueue<Task> generatedTasks, List<Server> servers, int timeSim, int maxTime) {
        String textClients = view.getTextAreaClients().getText() + "\n" + generatedTasks.toString();
        String textQueues = view.getTextAreaQueues().getText() + "\n" + "timeSim: " + timeSim + "\n" +servers.toString();

        view.getPeekTime().setText(maxTime + "s");

        if(!generatedTasks.isEmpty())
            view.getTextAreaClients().setText(textClients);
        view.getTextAreaQueues().setText(textQueues);

        if(!generatedTasks.isEmpty())
            obj.printf(generatedTasks.toString());
        obj.println();
        obj.printf("timeSim: " + timeSim + "\n" +servers.toString());
    }

    public void updateAvgTime(float avgTime) {
        view.getAvgTime().setText(avgTime + "s");
    }

    public void closeFileLog() {
        obj.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == view.getSTARTButton()){
            simManager = new SimulationManager(Integer.parseInt(view.getTextField7().getText()),
                    Integer.parseInt(view.getTextField6().getText()), Integer.parseInt(view.getTextField5().getText()),
                    Integer.parseInt(view.getTextField4().getText()), Integer.parseInt(view.getTextField3().getText()),
                    Integer.parseInt(view.getTextField2().getText()), Integer.parseInt(view.getTextField1().getText()),
                    this, policy);

            simManager.setSelectionPolicy(policy);

            Thread t = new Thread(simManager);
            t.start();
        }
        else if(e.getSource() == view.getSHORTEST_QUEUEButton()) {
            try {
                policy = SelectionPolicy.SHORTEST_QUEUE;
                view.getCheckBoxQueue().setSelected(true);
                view.getCheckBoxTime().setSelected(false);
            } catch (Exception ex) {
                view.displayErrorMessage(ex.getMessage());
            }
        }
        else if(e.getSource() == view.getSHORTEST_TIMEButton()) {
            try {
                policy = SelectionPolicy.SHORTEST_TIME;
                view.getCheckBoxTime().setSelected(true);
                view.getCheckBoxQueue().setSelected(false);
            } catch (Exception ex) {
                view.displayErrorMessage(ex.getMessage());
            }
        }
        else if(e.getSource() == view.getRESETButton()) {
            view.getTextAreaClients().setText("");
            view.getTextAreaQueues().setText("");
            view.getAvgTime().setText("");
            view.getPeekTime().setText("");
            simManager.setRunning(false);
        }
    }

}

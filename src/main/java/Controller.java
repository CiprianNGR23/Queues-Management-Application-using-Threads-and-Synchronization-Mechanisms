import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Controller implements ActionListener{

    private GUI_APP view;
    private SimulationManager simManager;
    private SelectionPolicy policy;

    public Controller(GUI_APP view) {
        this.view = view;

        this.view.addSTARTListener(this);
        this.view.addRESETListener(this);
        this.view.addSHORTEST_QUEUEListener(this);
        this.view.addSHORTEST_TIMEListener(this);

    }

    public void updateView(ArrayBlockingQueue<Task> generatedTasks, List<Server> servers, int timeSim) {
        if(!generatedTasks.isEmpty())
            view.getTextAreaClients().setText(view.getTextAreaClients().getText() + "\n" + generatedTasks.toString());
        view.getTextAreaQueues().setText(view.getTextAreaQueues().getText() + "\n" + "timeSim: " + timeSim + "\n" +servers.toString());
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
            System.out.println("DAD");
        }
    }

}

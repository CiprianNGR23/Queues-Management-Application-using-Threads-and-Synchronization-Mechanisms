import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class SimulationManager implements Runnable{

    private Controller controller;
    private int currentTime = 0;

    //data read from UI
    public int timeLimit; // = 100;
    public int maxProcessingTime; // = 10;
    public int minProcessingTime; // = 2;
    public int numberOfServers; // = 3;
    public int numberOfClients; // = 100;
    public int maxArrivalTime;
    public int minArrivalTime;
    private boolean running;
    public SelectionPolicy selectionPolicy; //= SelectionPolicy.SHORTEST_TIME;

    //entity responsible wit queue management and client distribution
    private Scheduler scheduler;

    //frame for displaying simulation
    //private SimulationFrame frame;

    //pool of tasks (client shopping in the store)
    private ArrayBlockingQueue<Task> generatedTasks;

    public SimulationManager(int numberOfClients, int numberOfServers, int timeLimit, int minArrivalTime, int maxArrivalTime,
                             int minProcessingTime, int maxProcessingTime, Controller controller, SelectionPolicy policy) {
        //initialize the scheduler
        // => create and start numberOfServers threads
        // => initialize selection strategy => createStrategy
        //initialize frame to display simulation
        //generate numberOfClients clients using generateNRandomTasks() and store them to generatedTasks

        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.controller = controller;
        this.running = true;

       /* System.out.println("nbC: " + numberOfClients + "\nnbS: " + numberOfServers +
                "\ntimeL: " + timeLimit + "\nminA: " + minArrivalTime + "\nmaxA: " + maxArrivalTime +
                "\nminP: " + minProcessingTime + "\nmaxP: " + maxProcessingTime);*/

        scheduler = new Scheduler(numberOfServers, numberOfClients);
        scheduler.changeStrategy(policy);
        generatedTasks = generateNRandomTasks(numberOfClients, minArrivalTime, minProcessingTime, maxArrivalTime, maxProcessingTime);
    }

    private ArrayBlockingQueue<Task> generateNRandomTasks(int numberOfClients, int minArrivalTime, int minProcessingTime,
                                      int maxArrivalTime, int maxProcessingTime) {
        //generate N random tasks
        // - random processing time
        // minProcessingTime < processingTime < maxProcessingTime
        // - random arrivalTime
        //sort list with respect of arrivalTime

        ArrayBlockingQueue<Task> queueTask = new ArrayBlockingQueue<Task>(numberOfClients);
        ArrayList<Task> arrayTask = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<numberOfClients; i++) {
            Task client = new Task(i, random.nextInt(minArrivalTime, maxArrivalTime),
                    random.nextInt(minProcessingTime, maxProcessingTime));
            arrayTask.add(client);
        }
        arrayTask.sort(Comparator.comparing(Task::getArrivalTime));
        queueTask.addAll(arrayTask);

        return queueTask;
    }

    public void checkForRun() {
        if(generatedTasks.isEmpty()) {
            running = false;
            for (Server serv : scheduler.getServers()) {
                if (serv.isRunning()) {
                    running = true;
                    break;
                }
            }
        }
    }

    public int peekMaxClientsTime(int maxTimeSize) {
        int sum = 0;
        for(Server serv: scheduler.getServers()) {
            sum += serv.getTasks().size();
        }
        if(maxTimeSize < sum)
            maxTimeSize = sum;
        return maxTimeSize;
    }

    @Override
    public void run() {
        int maxTimeSize = 0;
        while(currentTime < timeLimit && running == true) {
            for (Task client : generatedTasks) {
                if(client.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(client);
                    generatedTasks.remove(client);
                }
            }
            maxTimeSize = peekMaxClientsTime(maxTimeSize);
            controller.updateView(generatedTasks, scheduler.getServers(), currentTime, maxTimeSize);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentTime++;
            checkForRun();
        }
        controller.closeFileLog();
        controller.updateAvgTime(scheduler.getWaitingTime() / numberOfClients);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public int getMinProcessingTime() {
        return minProcessingTime;
    }

    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
        this.selectionPolicy = selectionPolicy;
    }

    public int getMaxArrivalTime() {
        return maxArrivalTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public int getMinArrivalTime() {
        return minArrivalTime;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }
}

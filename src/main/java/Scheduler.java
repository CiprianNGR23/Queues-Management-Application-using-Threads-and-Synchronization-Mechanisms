import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;
    private int waitingTime;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new ArrayList<Server>();

        for(int i=0; i<maxNoServers; i++) {
            Server serv = new Server(maxTasksPerServer);
            this.servers.add(serv);
            Thread t = new Thread(serv);
            t.start();
        }

    }

    public void changeStrategy(SelectionPolicy policy) {
        //apply strategy patter to instantiate the strategy with the concrete
        //strategy corresponding to policy
        if(policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t) {
        //call the strategy addTask method
        waitingTime += strategy.addTask(servers, t);
    }

    public List<Server> getServers() {
        return servers;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}

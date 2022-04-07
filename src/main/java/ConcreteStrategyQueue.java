import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConcreteStrategyQueue implements Strategy{

    @Override
    public int addTask(List<Server> servers, Task t) {

        List<Server> copyServer = servers;
        Collections.sort(servers, new Comparator<Server>() {
            @Override
            public int compare(Server o1, Server o2) {
                return o1.compareToSize(o2);
            }
        });

        if(servers.get(0).isRunning() == false)
            new Thread(servers.get(0)).start();
        return servers.get(0).addTask(t);
    }
}

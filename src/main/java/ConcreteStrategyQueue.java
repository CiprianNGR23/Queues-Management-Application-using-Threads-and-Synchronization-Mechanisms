import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConcreteStrategyQueue implements Strategy{

    @Override
    public void addTask(List<Server> servers, Task t) {

        List<Server> copyServer = servers;
        Collections.sort(servers, new Comparator<Server>() {
            @Override
            public int compare(Server o1, Server o2) {
                return o1.compareToSize(o2);
            }
        });

        servers.get(0).addTask(t);

    }
}

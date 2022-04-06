import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConcreteStrategyTime implements Strategy{

    @Override
    public void addTask(List<Server> servers, Task t) {

        Collections.sort(servers, new Comparator<Server>() {
            @Override
            public int compare(Server o1, Server o2) {
                return o1.compareTo(o2);
            }
        });

        servers.get(0).addTask(t);

    }
}

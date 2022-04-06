import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server(int maxTasksPerServer) {
        this.tasks = new ArrayBlockingQueue<Task>(maxTasksPerServer);
        this.waitingPeriod = new AtomicInteger();
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        this.waitingPeriod.getAndAdd(newTask.getServiceTime());
    }

    @Override
    public void run() {
        while(true) {
            //take next task from queue
            Task val = tasks.peek();
            if(val != null) {
                //stop the thread for a time equal with the task's processing time
                if(val.getServiceTime() == 1)
                    tasks.remove(val);
                else {
                    val.setServiceTime(val.getServiceTime()-1);
                    this.waitingPeriod.getAndDecrement();
                }
                //decrement the waitingPeriod

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Task[] getTasks() {
        return new Task[0];
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public int compareTo(Server o2) {
        if(this.getWaitingPeriod().get() > o2.getWaitingPeriod().get())
            return 1;
        return -1;
    }

    public int compareToSize(Server o2) {
        if(this.tasks.size() > o2.tasks.size())
            return 1;
        return -1;
    }

    @Override
    public String toString() {
        return "Server[ " +
                "tasks=" + tasks +
                " ]\n";
    }
}

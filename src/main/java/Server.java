import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private boolean running;

    public Server(int maxTasksPerServer) {
        this.tasks = new ArrayBlockingQueue<Task>(maxTasksPerServer);
        this.waitingPeriod = new AtomicInteger();
        this.running = true;
    }

    public int addTask(Task newTask) {
        tasks.add(newTask);
        this.running = true;
        this.waitingPeriod.getAndAdd(newTask.getServiceTime());
        return this.waitingPeriod.get();
    }

    @Override
    public void run() {
        while(running) {
            //take next task from queue
            Task val = tasks.peek();
            if(val != null) {
                //stop the thread for a time equal with the task's processing time

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(val.getServiceTime() == 1)
                    tasks.remove(val);
                else {
                    val.setServiceTime(val.getServiceTime()-1);
                    this.waitingPeriod.getAndDecrement();
                }
                //decrement the waitingPeriod
            }
            if(tasks.isEmpty())
                running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
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

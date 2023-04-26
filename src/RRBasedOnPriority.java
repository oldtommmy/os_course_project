import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *  基于优先级的时间片轮转算法
 */
public class RRBasedOnPriority {

    private final Scanner scanner = new Scanner(System.in);
    private LinkedList<PCB> waitingQueue = new LinkedList<>();
    private LinkedList<PCB> finishedQueue = new LinkedList<>();
    int timeSlice;
    public TimeGUI gui;

    /**
     * 初始化
     */
    public void init(int number,int timeSlice,LinkedList<PCB> inputQueue) {
        int num = number;
        this.timeSlice = timeSlice;
        waitingQueue = inputQueue;
        waitingQueue.sort(Comparator.comparingDouble(PCB::getArriveTime));
    }

    public void RR() {

        double currentTime = 0;
        int index = 0;
        boolean isReady = false;


        while (!waitingQueue.isEmpty()) {
            String temp = "";
            temp = connectString(temp,"================================Ready Queue======================================");
            temp = connectString(temp,"Process ID\tPriority\tArrival Time\tService Time\tRun Time\tStatus");
            int maxPriority = Integer.MAX_VALUE;
            PCB target = null;
            isReady = false;

            //遍历队列
            //输出当前已到达的进程 并找到最高优先级的进程
            for (int i = 0; i < waitingQueue.size(); i++) {
                PCB PCB = waitingQueue.get(i);
                if (currentTime >= PCB.getArriveTime()) {
                    isReady = true;
                    temp = connectString(temp, PCB.getName()+"\t"+ PCB.getPriority()+"\t"+
                            PCB.getArriveTime()+"\t"+ PCB.getServiceTime()+"\t"+ PCB.getRunningTime()+"\t"+
                            "Ready");
                    if (PCB.getPriority() < maxPriority) {
                        maxPriority = PCB.getPriority();
                        target = PCB;
                        index = i;
                    }
                }
            }

            if (isReady) {
                temp = connectString(temp,"Current time:"+currentTime+", Selected process:"+target.getName());
                target.setRunningTime(target.getRunningTime()+timeSlice);
                if (target.getRunningTime() > target.getServiceTime()) {
                    target.setStatus('F');
                    currentTime += target.getServiceTime() - (target.getRunningTime() - timeSlice);
                    target.setRunningTime(target.getServiceTime());
                    finishedQueue.add(target);
                    waitingQueue.remove(index);
                }else {
                    currentTime += timeSlice;
                    target.setPriority(target.getPriority() + 2); //每执行完一个时间片优先数加 2
                }
            }

            temp = connectString(temp,"===============================Finished Queue=================================");
            temp = connectString(temp,"Process ID\tPriority\tArrival Time\tService Time\tRun Time\tStatus");
            for (int i = 0; i < finishedQueue.size(); i++) {
                PCB PCB = finishedQueue.get(i);
                temp = connectString(temp, PCB.getName()+"\t"+ PCB.getPriority()+"\t"+
                        PCB.getArriveTime()+"\t"+ PCB.getServiceTime()+"\t"+ PCB.getRunningTime()+"\t"+
                        "finished");
            }

            gui.submitResult(temp);
        }
    }

    private  String connectString(String f,String s){
        return f+s+"\n";
    }

    public static void doRR() {
        RRBasedOnPriority rrBasedOnPriority = new RRBasedOnPriority();
        //rrBasedOnPriority.init();
        //rrBasedOnPriority.RR();
    }

}

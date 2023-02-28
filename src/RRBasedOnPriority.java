import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *  基于优先级的时间片轮转算法
 */
public class RRBasedOnPriority {

    private final Scanner scanner = new Scanner(System.in);
    private LinkedList<PBT> waitingQueue = new LinkedList<>();
    private LinkedList<PBT> finishedQueue = new LinkedList<>();

    /**
     * 初始化
     */
    public void init() {
        System.out.println("输入进程数目:");
        int num = scanner.nextInt();
        String name;
        double arrive;
        double service;
        int priority;
        char status = 'W';
        System.out.println("请创建进程对象 输入进程名称  到达时间  服务时间 优先数");
        System.out.println("请输入进程的信息: ");
        for (int i = 0; i < num; i++) {
            name = scanner.next();
            arrive = scanner.nextDouble();
            service = scanner.nextDouble();
            priority = scanner.nextInt();
            if (arrive == 0) {
                status = 'R';
            }
            waitingQueue.add(new PBT(name, arrive, service, priority, status));
        }
        waitingQueue.sort(Comparator.comparingDouble(PBT::getArriveTime));
    }

    public void RR() {

        System.out.println("请输入时间片大小:");
        int timeSlice = scanner.nextInt();
        double currentTime = 0;
        int index = 0;
        boolean exist = false;


        while (!waitingQueue.isEmpty()) {
            System.out.println("================================Ready Queue======================================");
            System.out.println("Process ID\t\tPriority\tArrival Time\tService Time\tRun Time\tStatus");
            int maxPriority = Integer.MAX_VALUE;
            PBT target = null;
            boolean isReady = false;

            //遍历队列
            //输出当前已到达的进程 并找到最高优先级的进程
            for (int i = 0; i < waitingQueue.size(); i++) {
                PBT pbt = waitingQueue.get(i);
                if (currentTime >= pbt.getArriveTime()) {
                    isReady = true;
                    System.out.println("\t\t"+pbt.getName()+"\t\t"+pbt.getPriority()+"\t\t\t"+
                            pbt.getArriveTime()+"\t\t\t\t"+pbt.getServiceTime()+"\t\t\t\t"+pbt.getRunningTime()+"\t\t\t"+
                            "Ready");
                    if (pbt.getPriority() < maxPriority) {
                        maxPriority = pbt.getPriority();
                        target = pbt;
                        index = i;
                    }
                }
            }

            if (isReady) {
                System.out.println("Current time:"+currentTime+", Selected process:"+target.getName());
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

            System.out.println("===============================Finished Queue=================================");
            System.out.println("Process ID\t\tPriority\tArrival Time\tService Time\tRun Time\tStatus");
            for (int i = 0; i < finishedQueue.size(); i++) {
                PBT pbt = finishedQueue.get(i);
                System.out.println("\t\t"+pbt.getName()+"\t\t"+pbt.getPriority()+"\t\t\t"+
                        pbt.getArriveTime()+"\t\t\t\t"+pbt.getServiceTime()+"\t\t\t\t"+pbt.getRunningTime()+"\t\t\t"+
                        "finished");
            }

        }
    }

    public static void doRR() {
        RRBasedOnPriority rrBasedOnPriority = new RRBasedOnPriority();
        rrBasedOnPriority.init();
        rrBasedOnPriority.RR();
    }

}

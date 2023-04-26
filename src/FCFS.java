import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 先来先服务算法
 */
@SuppressWarnings({"all"})
public class FCFS {

    // 平均周转时间
    public static double avgTotalTurnTime;

    private static int processNum;

    private static LinkedList<PCB> PCBS = new LinkedList<>();

    private static Scanner sc = new Scanner(System.in);

    public FCFSGUI gui;

    public void init(int number,LinkedList<PCB> inputQueue) {
//        System.out.println("=================FCFS 先来先服务模拟===============");
//        System.out.println("请输入作业数:");
//        processNum = sc.nextInt();
//
//        System.out.println("请依次输入作业标识符,作业到达时间,作业运行时间:" );
//        for (int i = 0; i < processNum; i++) {
//            PCBS.add(new PCB(sc.next(), sc.nextDouble(), sc.nextDouble(), 0, 0));
//        }
        processNum = number;
        PCBS = inputQueue;
        PCBS.sort(Comparator.comparingDouble(PCB::getArriveTime));
        avgTotalTurnTime = 0;
    }

    public void doFCFS() {

        //init();
        double preFinishedTime = 0;

        if (PCBS.get(0).getArriveTime() != preFinishedTime) {
            preFinishedTime = PCBS.get(0).getArriveTime();
        }

        for (PCB PCB : PCBS) {
            PCB.setFinishedTime(preFinishedTime + PCB.getServiceTime());
            preFinishedTime = PCB.getFinishedTime();
            PCB.setTurnTime(PCB.getFinishedTime() - PCB.getArriveTime());
        }   
        show();
    }

    public void show() {
        gui.showOnUI("================================FCFS======================================");
        gui.showOnUI("Work ID\t\tArrival Time\t\tFinished Time\t\tTurn Time");
        for (PCB PCB : PCBS) {
            avgTotalTurnTime += PCB.getTurnTime();
            gui.showOnUI("\t"+ PCB.getName()+"\t"+ PCB.getArriveTime()+"\t"+
                    PCB.getFinishedTime()+"\t"+ PCB.getTurnTime());
        }
        avgTotalTurnTime /= processNum;
        gui.showOnUI("=============================Avg TurnTime:" + avgTotalTurnTime + "===========================");
    }
}

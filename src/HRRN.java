/**
 * 高响应比优先
 * 响应比 = 作业周转时间/作业处理时间
 *       =（作业处理时间+作业等待时间）/作业处理时间
 *       = 1 +（作业等待时间/作业处理时间）
 */
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class HRRN {

    private static LinkedList<PCB> PCBS = new LinkedList<>();

    private static double avgTotalTurnTime;

    private static int processNum;

    private static Scanner sc = new Scanner(System.in);

    public HRRNGUI gui;

    public static void init(int number,LinkedList<PCB> inputQueue) {
//        System.out.println("=================HRRN 高响应比优先算法===============");
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

    public void show() {
        gui.showOnUI("================================HRRN======================================");
        gui.showOnUI("Work ID\t\t\tArrival Time\t\tFinished Time\t\tTurn Time");
        for (PCB PCB : PCBS) {
            avgTotalTurnTime += PCB.getTurnTime();
            gui.showOnUI("\t"+ PCB.getName()+"\t\t\t"+ PCB.getArriveTime()+"\t\t\t\t\t"+
                    PCB.getFinishedTime()+"\t\t\t\t"+ PCB.getTurnTime());
        }
        avgTotalTurnTime /= processNum;
        gui.showOnUI("=============================Avg TurnTime:" + avgTotalTurnTime + "===========================");
    }

    public void doHRN() {
        //init();
        // 根据响应比排序
        PCBS.sort((p1, p2) -> {
            double r1 = (p1.getWaitingTime() + p1.getServiceTime()) / p1.getServiceTime();
            double r2 = (p2.getWaitingTime() + p2.getServiceTime()) / p2.getServiceTime();
            return Double.compare(r2, r1);
        });

        // 执行进程
        double current_time = 0;
        for (PCB PCB : PCBS) {
            PCB.setFinishedTime(current_time + PCB.getServiceTime());
            PCB.setWaitingTime(current_time - PCB.getArriveTime());
            PCB.setTurnTime(PCB.getFinishedTime() - PCB.getArriveTime());
            current_time += PCB.getServiceTime();
        }
        show();
    }
}



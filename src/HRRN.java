/**
 * 高响应比优先
 * 响应比 = 作业周转时间/作业处理时间
 *       =（作业处理时间+作业等待时间）/作业处理时间
 *       = 1 +（作业等待时间/作业处理时间）
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class HRRN {

    private static LinkedList<PBT> pbts = new LinkedList<>();

    private static double avgTotalTurnTime;

    private static int processNum;

    private static Scanner sc = new Scanner(System.in);

    public HRRNGUI gui;

    public static void init(int number,LinkedList<PBT> inputQueue) {
//        System.out.println("=================HRRN 高响应比优先算法===============");
//        System.out.println("请输入作业数:");
//        processNum = sc.nextInt();
//
//        System.out.println("请依次输入作业标识符,作业到达时间,作业运行时间:" );
//        for (int i = 0; i < processNum; i++) {
//            pbts.add(new PBT(sc.next(), sc.nextDouble(), sc.nextDouble(), 0, 0));
//        }
        processNum = number;
        pbts = inputQueue;
        pbts.sort(Comparator.comparingDouble(PBT::getArriveTime));
        avgTotalTurnTime = 0;
    }

    public void show() {
        gui.showOnUI("================================HRRN======================================");
        gui.showOnUI("Work ID\t\t\tArrival Time\t\tFinished Time\t\tTurn Time");
        for (PBT pbt : pbts) {
            avgTotalTurnTime += pbt.getTurnTime();
            gui.showOnUI("\t"+pbt.getName()+"\t\t\t"+pbt.getArriveTime()+"\t\t\t\t\t"+
                    pbt.getFinishedTime()+"\t\t\t\t"+pbt.getTurnTime());
        }
        avgTotalTurnTime /= processNum;
        gui.showOnUI("=============================Avg TurnTime:" + avgTotalTurnTime + "===========================");
    }

    public void doHRN() {
        //init();
        // 根据响应比排序
        pbts.sort((p1, p2) -> {
            double r1 = (p1.getWaitingTime() + p1.getServiceTime()) / p1.getServiceTime();
            double r2 = (p2.getWaitingTime() + p2.getServiceTime()) / p2.getServiceTime();
            return Double.compare(r2, r1);
        });

        // 执行进程
        int current_time = 0;
        for (PBT pbt : pbts) {
            pbt.setFinishedTime(current_time + pbt.getServiceTime());
            pbt.setWaitingTime(current_time - pbt.getArriveTime());
            pbt.setTurnTime(pbt.getFinishedTime() - pbt.getArriveTime());
            current_time += pbt.getServiceTime();
        }
        show();
    }
}



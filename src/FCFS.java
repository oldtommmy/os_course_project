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

    private static LinkedList<PBT> pbts = new LinkedList<>();

    private static Scanner sc = new Scanner(System.in);

    public FCFSGUI gui;

    public void init(int number,LinkedList<PBT> inputQueue) {
//        System.out.println("=================FCFS 先来先服务模拟===============");
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

    public void doFCFS() {

        //init();
        double preFinishedTime = 0;

        if (pbts.get(0).getArriveTime() != preFinishedTime) {
            preFinishedTime = pbts.get(0).getArriveTime();
        }

        for (PBT pbt : pbts) {
            pbt.setFinishedTime(preFinishedTime + pbt.getServiceTime());
            preFinishedTime = pbt.getFinishedTime();
            pbt.setTurnTime(pbt.getFinishedTime() - pbt.getArriveTime());
        }   
        show();
    }

    public void show() {
        gui.showOnUI("================================FCFS======================================");
        gui.showOnUI("Work ID\t\tArrival Time\t\tFinished Time\t\tTurn Time");
        for (PBT pbt : pbts) {
            avgTotalTurnTime += pbt.getTurnTime();
            gui.showOnUI("\t"+pbt.getName()+"\t"+pbt.getArriveTime()+"\t"+
                    pbt.getFinishedTime()+"\t"+pbt.getTurnTime());
        }
        avgTotalTurnTime /= processNum;
        gui.showOnUI("=============================Avg TurnTime:" + avgTotalTurnTime + "===========================");
    }
}

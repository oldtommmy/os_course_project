import java.util.*;

/**
 * 短作业优先算法（已适配）
 */
@SuppressWarnings({"all"})
public class SJF {

    // 平均周转时间
    public static double avgTotalTurnTime;

    private static int processNum;

    private static double preFinised;

    private static LinkedList<PBT> pbts = new LinkedList<>();

    private static Scanner sc = new Scanner(System.in);

    public SJFGUI gui;

    public static void init(int number,LinkedList<PBT> inputQueue) {
//        System.out.println("=================SJF 短作业优先算法===============");
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
        preFinised = 0;
    }

    public void show() {
        gui.showOnUI("================================SJF======================================");
        gui.showOnUI("Work ID\tArrival Time\tFinished Time\tTurn Time");
        for (PBT pbt : pbts) {
            avgTotalTurnTime += pbt.getTurnTime();
            gui.showOnUI(pbt.getName()+"\t"+pbt.getArriveTime()+"\t"+
                    pbt.getFinishedTime()+"\t"+pbt.getTurnTime());
        }
        avgTotalTurnTime /= processNum;
        gui.showOnUI("=============================Avg TurnTime:" + avgTotalTurnTime + "===========================");
    }

    public void doSJF() {

        //init();
        double min;
        int target = 0;
        for (int i = 0; i < pbts.size(); i++) {
            min = Double.MAX_VALUE;
            for (int j = 0; j < pbts.size(); j++) {
                PBT pbt = pbts.get(j);
                /**
                 *  1. 如果服务时间小于最小时间
                 *  2. 到达时间小于等于前一个作业的完成时间
                 *  3. 完成时间为0代表着还没有进行服务
                 *  最核心的地方就在于有完成时间限制着作业不会继续重复进入循环
                 */
                if (pbt.getServiceTime() < min && pbt.getArriveTime() <= preFinised && pbt.getFinishedTime() == 0){
                    min = pbt.getServiceTime();
                    target = j;
                }
            }
            PBT pbt = pbts.get(target);
            pbt.setFinishedTime(preFinised + pbt.getServiceTime());
            preFinised = pbt.getFinishedTime();
            pbt.setTurnTime(pbt.getFinishedTime() - pbt.getArriveTime());
        }
        show();
    }
}

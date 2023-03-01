import java.util.*;

/**
 * 短作业优先算法
 */
@SuppressWarnings({"all"})
public class SJF {

    // 平均周转时间
    public static double avgTotalTurnTime;

    private static int processNum;

    private static double preFinised;

    private static List<PBT> pbts = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);

    public static void init() {
        System.out.println("=================SJF 短作业优先算法===============");
        System.out.println("请输入作业数:");
        processNum = sc.nextInt();

        System.out.println("请依次输入作业标识符,作业到达时间,作业运行时间:" );
        for (int i = 0; i < processNum; i++) {
            pbts.add(new PBT(sc.next(), sc.nextDouble(), sc.nextDouble(), 0, 0));
        }
        pbts.sort(Comparator.comparingDouble(PBT::getArriveTime));
        avgTotalTurnTime = 0;
        preFinised = 0;
    }

    public static void show() {
        System.out.println("================================SJF======================================");
        System.out.println("Work ID\t\t\tArrival Time\t\tFinished Time\t\tTurn Time");
        for (PBT pbt : pbts) {
            avgTotalTurnTime += pbt.getTurnTime();
            System.out.println("\t"+pbt.getName()+"\t\t\t"+pbt.getArriveTime()+"\t\t\t\t\t"+
                    pbt.getFinishedTime()+"\t\t\t\t"+pbt.getTurnTime());
        }
        avgTotalTurnTime /= processNum;
        System.out.println("=============================Avg TurnTime:" + avgTotalTurnTime + "===========================");
    }

    public static void doSJF() {

        init();
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

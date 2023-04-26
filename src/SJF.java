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

    private static LinkedList<PCB> PCBS = new LinkedList<>();

    private static Scanner sc = new Scanner(System.in);

    public SJFGUI gui;

    public static void init(int number, LinkedList<PCB> inputQueue) {
//        System.out.println("=================SJF 短作业优先算法===============");
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
        preFinised = 0;
    }

    public void show() {
        gui.showOnUI("================================SJF======================================");
        gui.showOnUI("Work ID\tArrival Time\tFinished Time\tTurn Time");
        for (PCB PCB : PCBS) {
            avgTotalTurnTime += PCB.getTurnTime();
            gui.showOnUI(PCB.getName()+"\t"+ PCB.getArriveTime()+"\t"+
                    PCB.getFinishedTime()+"\t"+ PCB.getTurnTime());
        }
        avgTotalTurnTime /= processNum;
        gui.showOnUI("=============================Avg TurnTime:" + avgTotalTurnTime + "===========================");
    }

    public void doSJF() {

        //init();
        double min;
        int target = 0;
        for (int i = 0; i < PCBS.size(); i++) {
            min = Double.MAX_VALUE;
            for (int j = 0; j < PCBS.size(); j++) {
                PCB PCB = PCBS.get(j);
                /**
                 *  1. 如果服务时间小于最小时间
                 *  2. 到达时间小于等于前一个作业的完成时间
                 *  3. 完成时间为0代表着还没有进行服务
                 *  最核心的地方就在于有完成时间限制着作业不会继续重复进入循环
                 */
                if (PCB.getServiceTime() < min && PCB.getArriveTime() <= preFinised && PCB.getFinishedTime() == 0){
                    min = PCB.getServiceTime();
                    target = j;
                }
            }
            PCB PCB = PCBS.get(target);
            PCB.setFinishedTime(preFinised + PCB.getServiceTime());
            preFinised = PCB.getFinishedTime();
            PCB.setTurnTime(PCB.getFinishedTime() - PCB.getArriveTime());
        }
        show();
    }
}

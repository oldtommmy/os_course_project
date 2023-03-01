/**
 * 运行入口
 */
public class OS_Project {
    public static void main(String[] args) {

        //RRBasedOnPriority.doRR(); 时间片轮转
        //MSFQSBasedOnTimeSlice.doMSFQ(); //多级优先队列
        FCFS.doFCFS();
    }
}

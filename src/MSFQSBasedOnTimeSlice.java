import java.util.*;

//@SuppressWarnings({"all"})
public class MSFQSBasedOnTimeSlice {

    private static Queue<PBT> firstQueue = new LinkedList<>();
    private static Queue<PBT> secondQueue = new LinkedList<>();
    private static Queue<PBT> thirdQueue = new LinkedList<>();
    private static LinkedList<PBT> pbts = new LinkedList<>();
    private static int currentTime = 0;
    private static int firstTime;  //第一队列cpu时间片
    private static int secondTime; //第二队列cpu时间片
    private static int thirdTime;  //第三队列cpu时间片
    private static int processNum;     //进程数量
    private static Scanner sc = new Scanner(System.in);

    public static void init() {
        System.out.println("=================MSFQ 多级队列反馈调度模拟===============");
        System.out.println("队列数为3，按队列优先级从高到低的顺序输入各个队列的时间片长度：");
        firstTime = sc.nextInt();
        secondTime = sc.nextInt();
        thirdTime = sc.nextInt();
        System.out.println( "请输入进程数:" );
        processNum = sc.nextInt();

        System.out.println("请依次输入进程标识符,进程到达时间,进程运行时间:" );
        for (int i = 0; i < processNum; i++) {
            pbts.add(new PBT(sc.next(), sc.nextDouble(), sc.nextDouble()));
        }
        pbts.sort(Comparator.comparingDouble(PBT::getArriveTime));
    }

    public static void doMSFQ() {

        init();

        int firstCpu = firstTime;
        int secondCpu = secondTime;
        int thirdCpu = thirdTime;
        currentTime = 0;
        int num = 0; //计数器

        while (num < processNum || !firstQueue.isEmpty() || !secondQueue.isEmpty() || !thirdQueue.isEmpty()) {

            //添加到达的进程至第一队列
            while(num < processNum && pbts.get(num).getArriveTime() == currentTime) {
                firstQueue.offer(pbts.get(num++));
            }


            showQueue();

            if(!firstQueue.isEmpty()){
                if (secondQueue.peek() != null) {
                    secondQueue.peek().setStatus('R');
                }
                if (thirdQueue.peek() != null) {
                    thirdQueue.peek().setStatus('R');
                }
                //运行时间+1
                PBT peek = firstQueue.peek();
                peek.setRunningTime(peek.getRunningTime() + 1);
                //CPU剩余时间片-1
                firstTime -= 1;
                //更新当前时间：+1
                currentTime++;
                //进程正在运行，状态：E
                if(peek.getRunningTime() < peek.getServiceTime()){
                    peek.setStatus('E');
                    //当前队列CPU时间片用完而进程仍未运行完时，进程出队，入次优先级队尾
                    if(firstTime == 0) {
                        peek.setStatus('R');
                        secondQueue.offer(firstQueue.poll());
                        firstTime = firstCpu;
                    }

                }

                //进程运行完毕，状态：F，记录完成时刻并出队
                if(peek.getRunningTime() == peek.getServiceTime()){
                    peek.setStatus('F');
                    System.out.println("current time:" + currentTime + "\t" +peek.getName() +" finished");
                    Objects.requireNonNull(firstQueue.poll());
                    firstTime = firstCpu;
                }
            } else if(!secondQueue.isEmpty()){

                if (thirdQueue.peek() != null) {
                    thirdQueue.peek().setStatus('R');
                }
                //运行时间+1
                PBT peek = secondQueue.peek();
                peek.setRunningTime(peek.getRunningTime() + 1);
                //CPU剩余时间片-1
                secondTime -= 1;
                //更新当前时间：+1
                currentTime++;
                //进程正在运行，状态：E
                if(peek.getRunningTime() < peek.getServiceTime()){
                    peek.setStatus('E');
                    //当前队列CPU时间片用完而进程仍未运行完时，进程出队，入次优先级队尾
                    if(secondTime == 0) {
                        peek.setStatus('R');
                        thirdQueue.offer(secondQueue.poll());
                        secondTime = secondCpu;
                    }
                }


                //进程运行完毕，状态：F，记录完成时刻并出队
                if(peek.getRunningTime() == peek.getServiceTime()){
                    peek.setStatus('F');
                    System.out.println("current time:" + currentTime + "\t" +peek.getName() +" finished");
                    Objects.requireNonNull(secondQueue.poll());
                    secondTime = secondCpu;
                }
            } else if(!thirdQueue.isEmpty()){
                //运行时间+1
                PBT peek = thirdQueue.peek();
                peek.setRunningTime(peek.getRunningTime() + 1);
                //CPU剩余时间片-1
                thirdTime -= 1;
                //更新当前时间：+1
                currentTime++;
                //进程正在运行，状态：E
                if(peek.getRunningTime() < peek.getServiceTime()){
                    peek.setStatus('E');
                    //当前队列CPU时间片用完而进程仍未运行完时，进程出队，入次优先级队尾
                    if(secondTime == 0) {
                        peek.setStatus('R');
                        thirdQueue.offer(thirdQueue.poll());
                        thirdTime = thirdCpu;
                    }

                } else {
                    firstTime = firstCpu;
                    peek.setStatus('F');
                    System.out.println("current time:" + currentTime + "\t" +peek.getName() +" finished");
                    Objects.requireNonNull(thirdQueue.poll());
                }
            }
        }
    }

    public static void showQueue() {
        System.out.println("================================Current time: "+currentTime+ "================================");
        System.out.println("================================First Queue======================================");
        System.out.println("Process ID\t\tPriority\tArrival Time\tService Time\tRun Time\tStatus");
        for (PBT pbt : firstQueue) {
            System.out.println("\t\t"+pbt.getName()+"\t\t"+pbt.getPriority()+"\t\t\t"+
                    pbt.getArriveTime()+"\t\t\t\t"+pbt.getServiceTime()+"\t\t\t\t"+pbt.getRunningTime()+"\t\t\t"+
                    (pbt.getStatus() == 'R' ? "Ready" : "Running"));
        }

        System.out.println("================================Second Queue======================================");
        System.out.println("Process ID\t\tPriority\tArrival Time\tService Time\tRun Time\tStatus");
        for (PBT pbt : secondQueue) {
            System.out.println("\t\t"+pbt.getName()+"\t\t"+pbt.getPriority()+"\t\t\t"+
                    pbt.getArriveTime()+"\t\t\t\t"+pbt.getServiceTime()+"\t\t\t\t"+pbt.getRunningTime()+"\t\t\t"+
                    (pbt.getStatus() == 'R' ? "Ready" : "Running"));
        }

        System.out.println("================================Third Queue======================================");
        System.out.println("Process ID\t\tPriority\tArrival Time\tService Time\tRun Time\tStatus");
        for (PBT pbt : thirdQueue) {
            System.out.println("\t\t"+pbt.getName()+"\t\t"+pbt.getPriority()+"\t\t\t"+
                    pbt.getArriveTime()+"\t\t\t\t"+pbt.getServiceTime()+"\t\t\t\t"+pbt.getRunningTime()+"\t\t\t"+
                    (pbt.getStatus() == 'R' ? "Ready" : "Running"));
        }
        System.out.println("\n");

    }



}

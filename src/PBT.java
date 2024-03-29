/**
 *  进程块的定义
 */
public class PBT {

    private String name;

    private double arriveTime;

    private double runningTime;

    private double serviceTime;

    private double turnTime;

    private double finishedTime;

    private double waitingTime;

    private int priority;

    private char status;

    public PBT(String name, double arriveTime, double serviceTime, int priority, char status) {
        this.name = name;
        this.arriveTime = arriveTime;
        this.serviceTime = serviceTime;
        this.priority = priority;
        this.status = status;
    }

    public PBT(String name, double arriveTime, double serviceTime) {
        this.name = name;
        this.arriveTime = arriveTime;
        this.serviceTime = serviceTime;
    }

    public PBT(String name, double arriveTime, double serviceTime, double turnTime, double finishedTime) {
        this.name = name;
        this.arriveTime = arriveTime;
        this.serviceTime = serviceTime;
        this.turnTime = turnTime;
        this.finishedTime = finishedTime;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public double getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(double turnTime) {
        this.turnTime = turnTime;
    }

    public double getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(double finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(double arriveTime) {
        this.arriveTime = arriveTime;
    }

    public double getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(double runningTime) {
        this.runningTime = runningTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
}

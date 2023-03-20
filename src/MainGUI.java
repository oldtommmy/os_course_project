import java.awt.*;
import java.awt.event.*;

//-Dfile.encoding=GB18030
public class MainGUI extends Frame {
    public MainGUI() {
        setSize(1600,900);
        setTitle("选择菜单");
        setLayout(new GridLayout(2, 2, 100, 100)); // 两行两列，行间距10像素，列间距10像素

        // 进程调度
        Button button1 = new Button("进程调度：时间片轮转");
        button1.setFont(new Font("苹方", Font.BOLD,20));
        Button button2 = new Button("进程调度：多级优先级");
        button2.setFont(new Font("苹方", Font.BOLD,20));

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RRBasedOnPriority control = new RRBasedOnPriority();
                TimeGUI gui = new TimeGUI(control);
                control.gui = gui;
                gui.setVisible(true);
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MSFQSBasedOnTimeSlice control = new MSFQSBasedOnTimeSlice();
                MSFQGUI gui = new MSFQGUI(control);
                control.gui = gui;
                gui.setVisible(true);
            }
        });

        add(button1);
        add(button2);

        // 作业调度
        Button button3 = new Button("作业调度：先来先服务");
        button3.setFont(new Font("苹方", Font.BOLD,20));
        Button button4 = new Button("作业调度：短作业优先");
        button4.setFont(new Font("苹方", Font.BOLD,20));
        Button button5 = new Button("作业调度：最高响应比");
        button5.setFont(new Font("苹方", Font.BOLD,20));

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FCFS control = new FCFS();
                FCFSGUI gui = new FCFSGUI(control);
                control.gui = gui;
                gui.setVisible(true);
            }
        });

        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SJF control = new SJF();
                SJFGUI gui = new SJFGUI(control);
                control.gui = gui;
                gui.setVisible(true);
            }
        });

        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HRRN control = new HRRN();
                HRRNGUI gui = new HRRNGUI(control);
                control.gui = gui;
                gui.setVisible(true);
            }
        });

        add(button3);
        add(button4);
        add(button5);

        //pack(); // 根据控件大小自适应窗口大小

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}

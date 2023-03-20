import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class TimeGUI extends Frame implements ActionListener {
    private TextField jobCountField, idField, arrivalTimeField, executionTimeField,levelField,timeField;
    private Button confirmButton, submitButton,lastButton,nextButton;
    private TextArea resultArea;
    private int jobCount,jobCount1,time;
    private String finalInput = "";

    private ArrayList<String> results = new ArrayList<String>();

    private LinkedList<PBT> inputs = new LinkedList<PBT>();


    private int page = 0;

    public RRBasedOnPriority control;

    public TimeGUI(RRBasedOnPriority control) {
        this.control = control;
        setTitle("基于优先级的时间片轮转算法");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        Font font = new Font("Arial", Font.PLAIN, 25);

        // 第一行：作业数输入
        Panel firstRow = new Panel();
        firstRow.setLayout(new FlowLayout());
        Label timeLabel = new Label("时间片大小");
        timeField = new TextField(10);
        Label jobCountLabel = new Label("作业数");
        jobCountField = new TextField(10);
        confirmButton = new Button("确认");
        confirmButton.addActionListener(this);
        jobCountLabel.setPreferredSize(new Dimension(200, 60));
        confirmButton.setPreferredSize(new Dimension(200, 60));
        jobCountField.setPreferredSize(new Dimension(200, 60));
        timeLabel.setPreferredSize(new Dimension(200, 60));
        timeField.setPreferredSize(new Dimension(200, 60));
        jobCountLabel.setFont(font);;
        confirmButton.setFont(font);;
        jobCountField.setFont(font);;
        timeLabel.setFont(font);;
        timeField.setFont(font);;
        firstRow.add(timeLabel);
        firstRow.add(timeField);
        firstRow.add(jobCountLabel);
        firstRow.add(jobCountField);
        firstRow.add(confirmButton);
        add(firstRow, BorderLayout.NORTH);

        // 第二行：标识符、到达时间、执行时间输入
        Panel secondRow = new Panel();
        secondRow.setLayout(new FlowLayout());
        Label idLabel = new Label("标识符");
        idField = new TextField(10);
        Label arrivalTimeLabel = new Label("到达时间");
        arrivalTimeField = new TextField(10);
        Label executionTimeLabel = new Label("执行时间");
        executionTimeField = new TextField(10);
        Label levelLabel = new Label("优先级");
        levelField = new TextField(10);
        submitButton = new Button("提交");
        submitButton.setEnabled(false);
        submitButton.addActionListener(this);
        idLabel.setPreferredSize(new Dimension(150, 60));
        idField.setPreferredSize(new Dimension(150, 60));
        arrivalTimeLabel.setPreferredSize(new Dimension(150, 60));
        arrivalTimeField.setPreferredSize(new Dimension(150, 60));
        executionTimeLabel.setPreferredSize(new Dimension(150, 60));
        executionTimeField.setPreferredSize(new Dimension(150, 60));
        levelLabel.setPreferredSize(new Dimension(150, 60));
        levelField.setPreferredSize(new Dimension(150, 60));
        submitButton.setPreferredSize(new Dimension(150, 60));
        idLabel.setFont(font);
        idField.setFont(font);
        arrivalTimeLabel.setFont(font);
        arrivalTimeField.setFont(font);
        executionTimeLabel.setFont(font);
        executionTimeField.setFont(font);
        levelLabel.setFont(font);
        levelField.setFont(font);
        submitButton.setFont(font);
        secondRow.add(idLabel);
        secondRow.add(idField);
        secondRow.add(arrivalTimeLabel);
        secondRow.add(arrivalTimeField);
        secondRow.add(executionTimeLabel);
        secondRow.add(executionTimeField);
        secondRow.add(levelLabel);
        secondRow.add(levelField);
        secondRow.add(submitButton);
        lastButton = new Button("上一时刻");
        nextButton = new Button("下一时刻");
        lastButton.setPreferredSize(new Dimension(250, 60));
        nextButton.setPreferredSize(new Dimension(250, 60));
        lastButton.setFont(font);
        nextButton.setFont(font);
        lastButton.setEnabled(false);
        nextButton.setEnabled(false);
        lastButton.addActionListener(this);
        nextButton.addActionListener(this);
        secondRow.add(lastButton);
        secondRow.add(nextButton);
        add(secondRow, BorderLayout.CENTER);

        // 第四行：结果显示
        resultArea = new TextArea();
        resultArea.setFont(new Font("苹方", Font.BOLD,20));
        resultArea.setEditable(false);
        resultArea.setPreferredSize(new Dimension(1600, 600));
        add(resultArea, BorderLayout.SOUTH);
        setSize(1600,900);
        // 设置关闭窗口事件
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            jobCount = Integer.parseInt(jobCountField.getText());
            time = Integer.parseInt(timeField.getText());
            jobCountField.setEditable(false);
            confirmButton.setEnabled(false);
            submitButton.setEnabled(true);
            jobCount1 = jobCount;
            //todo 这里提供int值jobCount,为记录数。
            showOnUI("作业数:"+jobCount);  //测试用语句
        } else if (e.getSource() == submitButton) {
            String id = idField.getText();
            String arrivalTime = arrivalTimeField.getText();
            String executionTime = executionTimeField.getText();
            String level = levelField.getText();
            String result = id + " " + arrivalTime + " " + executionTime + " "+level+" ";
            if( jobCount > 0 ){
                showOnUI("接受到指定作业：作业标识符："+idField.getText()+" ，作业到达时间："+arrivalTimeField.getText()+" ，作业持续时间："+executionTimeField.getText()+" ，作业优先级："+levelField.getText());
                finalInput = finalInput + result;
                if(Double.parseDouble(arrivalTime) == 0){
                    inputs.add(new PBT(id,Double.parseDouble(arrivalTime),Double.parseDouble(executionTime),Integer.parseInt(level),'R'));
                }else{
                    inputs.add(new PBT(id,Double.parseDouble(arrivalTime),Double.parseDouble(executionTime),Integer.parseInt(level),'W'));
                }
                jobCount--;
            }
            if (jobCount == 0) {
                //todo 每一次按下提交，会把数据转为字符串拼接起来，直到作业数减为0，即达到预定作业条数，最后的结果为finalInput
                showOnUI("最终字符串:"+finalInput);  //测试用
                submitButton.setEnabled(false);
                lastButton.setEnabled(true);
                nextButton.setEnabled(true);
                control.init(jobCount1,time,inputs);
                control.RR();
                showOnUI(results.get(page));
            }
            idField.setText("");
            arrivalTimeField.setText("");
            executionTimeField.setText("");
            levelField.setText("");
        }else if(e.getSource() == lastButton){
            //todo 上一页
            if(page >0){
                page--;
                cleanResult();
                showOnUI(results.get(page));
            }
        }else if(e.getSource() == nextButton){
            //todo 下一页
            if(page < results.size()-1){
                page++;
                cleanResult();
                showOnUI(results.get(page));
            }
        }
    }

    public void showOnUI(String string){
        resultArea.append("  "+string+"\n");
    }

    public void cleanResult(){
        resultArea.setText("");
    }

    public void submitResult(String string){
        System.out.println(string);
        results.add(string);
    }

    private void testResults(){
        results.add("第一页");
        results.add("第二页");
        results.add("第三页");
    }

    public static void main(String[] args) {
        //TimeGUI gui = new TimeGUI();
        //gui.testResults();  //翻页功能测试
        //gui.setVisible(true);
    }
}

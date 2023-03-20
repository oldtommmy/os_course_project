import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class MSFQGUI extends Frame implements ActionListener {
    private TextField jobCountField, idField, arrivalTimeField, executionTimeField,FField,SField,TField;
    private Button confirmButton, submitButton,lastButton,nextButton;
    private TextArea resultArea;
    private int jobCount,jobCount1,firstTime,secondTime,thirdTime;
    private String finalInput = "";

    private ArrayList<String> results = new ArrayList<String>();

    private LinkedList<PBT> inputs = new LinkedList<PBT>();


    private int page = 0;

    public MSFQSBasedOnTimeSlice control;

    public MSFQGUI(MSFQSBasedOnTimeSlice control) {
        this.control = control;
        setTitle("多级队列反馈调度模拟");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        Font font = new Font("Arial", Font.PLAIN, 25);

        // 第一行：作业数输入
        Panel firstRow = new Panel();
        firstRow.setLayout(new FlowLayout());
        Label FLabel = new Label("第一队列");
        FField = new TextField(10);
        Label SLabel = new Label("第二队列");
        SField = new TextField(10);
        Label TLabel = new Label("第三队列");
        TField = new TextField(10);
        Label jobCountLabel = new Label("作业数");
        jobCountField = new TextField(10);
        confirmButton = new Button("确认");
        confirmButton.addActionListener(this);
        jobCountLabel.setPreferredSize(new Dimension(150, 60));
        confirmButton.setPreferredSize(new Dimension(150, 60));
        jobCountField.setPreferredSize(new Dimension(150, 60));
        FLabel.setPreferredSize(new Dimension(150, 60));
        FField.setPreferredSize(new Dimension(150, 60));
        SLabel.setPreferredSize(new Dimension(150, 60));
        SField.setPreferredSize(new Dimension(150, 60));
        TLabel.setPreferredSize(new Dimension(150, 60));
        TField.setPreferredSize(new Dimension(150, 60));
        jobCountLabel.setFont(font);;
        confirmButton.setFont(font);;
        jobCountField.setFont(font);;
        FLabel.setFont(font);;
        FField.setFont(font);;
        SLabel.setFont(font);;
        SField.setFont(font);;
        TLabel.setFont(font);;
        TField.setFont(font);;
        firstRow.add(FLabel);
        firstRow.add(FField);
        firstRow.add(SLabel);
        firstRow.add(SField);
        firstRow.add(TLabel);
        firstRow.add(TField);
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
        submitButton = new Button("提交");
        submitButton.setEnabled(false);
        submitButton.addActionListener(this);
        idLabel.setPreferredSize(new Dimension(200, 60));
        idField.setPreferredSize(new Dimension(200, 60));
        arrivalTimeLabel.setPreferredSize(new Dimension(200, 60));
        arrivalTimeField.setPreferredSize(new Dimension(200, 60));
        executionTimeLabel.setPreferredSize(new Dimension(200, 60));
        executionTimeField.setPreferredSize(new Dimension(200, 60));
        submitButton.setPreferredSize(new Dimension(200, 60));
        idLabel.setFont(font);
        idField.setFont(font);
        arrivalTimeLabel.setFont(font);
        arrivalTimeField.setFont(font);
        executionTimeLabel.setFont(font);
        executionTimeField.setFont(font);
        submitButton.setFont(font);
        secondRow.add(idLabel);
        secondRow.add(idField);
        secondRow.add(arrivalTimeLabel);
        secondRow.add(arrivalTimeField);
        secondRow.add(executionTimeLabel);
        secondRow.add(executionTimeField);
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
        resultArea.setFont(new Font("苹方", Font.BOLD, 20));
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
            firstTime = Integer.parseInt(FField.getText());
            secondTime = Integer.parseInt(SField.getText());
            thirdTime = Integer.parseInt(TField.getText());
            FField.setEditable(false);
            SField.setEditable(false);
            TField.setEditable(false);
            jobCountField.setEditable(false);
            confirmButton.setEnabled(false);
            submitButton.setEnabled(true);
            jobCount1 = jobCount;
            //todo 这里提供int值jobCount,为记录数,firstTime等三个值为三个队列的时间片。
            showOnUI("作业数:"+jobCount);  //测试用语句
        } else if (e.getSource() == submitButton) {
            String id = idField.getText();
            String arrivalTime = arrivalTimeField.getText();
            String executionTime = executionTimeField.getText();
            String result = id + " " + arrivalTime + " " + executionTime + " ";
            if( jobCount > 0 ){
                showOnUI("接受到指定作业：作业标识符："+idField.getText()+" ，作业到达时间："+arrivalTimeField.getText()+" ，作业持续时间："+executionTimeField.getText());
                finalInput = finalInput + result;
                inputs.add(new PBT(id,Double.parseDouble(arrivalTime),Double.parseDouble(executionTime)));
                jobCount--;
            }
            if (jobCount == 0) {
                //todo 每一次按下提交，会把数据转为字符串拼接起来，直到作业数减为0，即达到预定作业条数，最后的结果为finalInput
                showOnUI("最终字符串:"+finalInput);  //测试用
                submitButton.setEnabled(false);
                lastButton.setEnabled(true);
                nextButton.setEnabled(true);
                //todo 这里传值，执行
                control.init(firstTime,secondTime,thirdTime,jobCount1,inputs);
                control.doMSFQ();
                showOnUI(results.get(page));
            }
            idField.setText("");
            arrivalTimeField.setText("");
            executionTimeField.setText("");
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
        results.add(string);
    }

    private void testResults(){
        results.add("第一页");
        results.add("第二页");
        results.add("第三页");
    }

    public static void main(String[] args) {
        MSFQGUI gui = new MSFQGUI(new MSFQSBasedOnTimeSlice());
        //gui.testResults();  //翻页功能测试
        gui.setVisible(true);
    }
}

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class HRRNGUI extends Frame implements ActionListener {
    private TextField jobCountField, idField, arrivalTimeField, executionTimeField;
    private Button confirmButton, submitButton;
    private TextArea resultArea;
    private int jobCount,jobcount1;
    private LinkedList<PCB> inputs = new LinkedList<PCB>();

    private HRRN control;
    private String finalInput = "";

    public HRRNGUI(HRRN control) {
        this.control = control;
        setTitle("高响应比优先模拟");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        Font font = new Font("Arial", Font.PLAIN, 25);

        // 第一行：作业数输入
        Panel firstRow = new Panel();
        firstRow.setLayout(new FlowLayout());
        Label jobCountLabel = new Label("作业数");
        jobCountField = new TextField(10);
        confirmButton = new Button("确认");
        confirmButton.addActionListener(this);
        jobCountLabel.setPreferredSize(new Dimension(200, 60));
        confirmButton.setPreferredSize(new Dimension(200, 60));
        jobCountField.setPreferredSize(new Dimension(200, 60));
        jobCountLabel.setFont(font);;
        confirmButton.setFont(font);;
        jobCountField.setFont(font);;
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
        add(secondRow, BorderLayout.CENTER);

        // 第三行：结果显示
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
            jobcount1 = jobCount;
            jobCountField.setEditable(false);
            confirmButton.setEnabled(false);
            submitButton.setEnabled(true);
            //todo 这里提供int值jobCount,为记录数。
            showOnUI("作业数:"+jobCount);  //测试用语句
        } else if (e.getSource() == submitButton) {
            String id = idField.getText();
            String arrivalTime = arrivalTimeField.getText();
            String executionTime = executionTimeField.getText();
            String result = id + " " + arrivalTime + " " + executionTime + " ";
            if( jobCount > 0 ){
                showOnUI("接受到指定作业：作业标识符："+idField.getText()+" ，作业到达时间："+arrivalTimeField.getText()+" ，作业持续时间："+executionTimeField.getText());
                inputs.add(new PCB(id,Double.parseDouble(arrivalTime),Double.parseDouble(executionTime),0,0));
                finalInput = finalInput + result;
                jobCount--;
            }
            if (jobCount == 0) {
                //todo 每一次按下提交，会把数据转为字符串拼接起来，直到作业数减为0，即达到预定作业条数，最后的结果为finalInput
                showOnUI("最终字符串:"+finalInput);
                //todo 这里传值，执行
                control.init(jobcount1,inputs);
                control.doHRN();
            }
            idField.setText("");
            arrivalTimeField.setText("");
            executionTimeField.setText("");
        }
    }

    public void showOnUI(String string){
        resultArea.append("  "+string+"\n");
    }

    public void cleanResult(){
        resultArea.setText("");
    }

    public static void main(String[] args) {
        //FCFSGUI gui = new FCFSGUI(new FCFS());
        //gui.setVisible(true);
    }
}

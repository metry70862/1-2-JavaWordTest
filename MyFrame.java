package week15.practice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Comparator;
import java.util.List;

public class MyFrame extends JFrame {
    Container frame = getContentPane();
    JPanel northPanel;
    String filename;
    JTextField tf = new JTextField(10);
    JTextArea ta = new JTextArea(10,10);
    VocManager voc;
    String[] stringArray = {"전체검색", "부분검색"};
    JComboBox<String> jComboBox;
    boolean flag = true; //전체검색 : true, 부분검색 : false
    String[] header = {"영어 단어", "한글 뜻"};
    JTable table;
    DefaultTableModel model;
    JRadioButton ascending, descending;
    boolean sortingFlag = true;// true 오름차순, false: 내림차순

    public MyFrame(String filename){
       this.setTitle("202311302 변수혁");
       this.filename = filename;
       this.setSize(500, 500);
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);
       this.setLocationRelativeTo(null);
       init();
       this.setVisible(true);
       voc = new VocManager("변수혁");
       boolean result = voc.makeVoc(filename);
       if(result){
           JOptionPane.showMessageDialog(this,"단어장 생성에 성공했습니다.");
           initTableData();
       }else {
           JOptionPane.showMessageDialog(this, "단어장 생성에 실패했습니다.\n 파일 경로를 확인하세요");
       }

    }
    void removeTableData(){
        for (int i = this.model.getRowCount()-1; i >= 0; i--) {
            this.model.removeRow(i);
        }
    }
    public void initTableData(){
        List<Word> list;
        if(sortingFlag){
            list = voc.voc.stream().sorted((o1, o2) -> o1.eng.compareTo(o2.eng)).toList();
        }else {
            list = voc.voc.stream().sorted((o1, o2) -> o1.eng.compareTo(o2.eng)*-1).toList();
        }
        removeTableData();


        for(Word word: list){
            model.addRow(new String[] {word.eng, word.kor});
        }

    }
    public void init(){
        initNorthPanel();
        initTextArea();
        initCombo();
    }

    private void initCombo() {
        this.jComboBox = new JComboBox<>(stringArray);
        this.northPanel.add(jComboBox);
        this.jComboBox.addActionListener(e -> {
            int index = jComboBox.getSelectedIndex();
            flag = index == 0;
        });
    }

    private void initTextArea() {
        this.model = new DefaultTableModel(header, 0);
        this.table = new JTable(model);
        this.table.getTableHeader().setFont(new Font("고딕체", 10, 15));
        this.frame.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void initNorthPanel(){
        this.northPanel = new JPanel();
        this.ascending = new JRadioButton("오름차순",true);
        this.ascending.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    sortingFlag = true;
                    initTableData();
                }
            }
        });
        this.descending = new JRadioButton("내림차순");
        this.descending.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                sortingFlag = false;
                initTableData();
            }
        });
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(ascending);
        buttonGroup.add(descending);
        this.northPanel.add(ascending);
        this.northPanel.add(descending);
        JLabel label = new JLabel("검색할 단어");
        this.ta.setFont(new Font("고딕체",10, 15));
        this.northPanel.add(label);
        this.northPanel.add(tf);
        this.tf.addActionListener(e -> {
            String eng = tf.getText();
            //System.out.println(eng);
            if(flag){
                //String result = voc.searchVoc(eng);
                Word word = voc.searchVoc(eng);
                if(word != null){
                    removeTableData();
                    model.addRow(new String[] {word.eng, word.kor});
                }else {
                    JOptionPane.showMessageDialog(MyFrame.this,"찾는 단어가 없습니다.");
                }
                tf.setText("");
            } else if (!flag) {
                var list = voc.searchVoc2(eng);
                removeTableData();
                if(!list.isEmpty()){
                    removeTableData();
                    for(Word word: list){
                        model.addRow(new String[] {word.eng, word.kor});
                    }
                }
                tf.setText("");
            }

        });

        this.frame.add(northPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args){
        MyFrame frame1 = new MyFrame("words.txt");
    }
}

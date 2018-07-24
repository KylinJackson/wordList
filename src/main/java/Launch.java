import com.itextpdf.text.DocListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class Launch extends JFrame {


    private JFrame mainForm = new JFrame("赵乙麒的单词本");
    private JLabel label1 = new JLabel("选择XML文件：");
    private JLabel label2 = new JLabel("选择PDF文件存放位置：");
    static JTextField sourceFile = new JTextField();
    static JTextField targetFile = new JTextField();
    static JButton buttonBrowseSource = new JButton("浏览");
    static JButton buttonBrowseTarget = new JButton("浏览");

    static JRadioButton radioTrue = new JRadioButton("打印单词", true);
    static JRadioButton radioFalse = new JRadioButton("不打印单词");

    static JButton buttonConvert = new JButton("生成PDF");

    private Launch() {
        Container container = mainForm.getContentPane();

        mainForm.setSize(400, 300);
        mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainForm.setLocationRelativeTo(null);
        mainForm.setResizable(false);
        mainForm.setLayout(null);
        mainForm.setVisible(true);

        buttonConvert.setEnabled(false);

        label1.setBounds(30, 10, 300, 30);
        sourceFile.setBounds(50, 50, 200, 30);
        buttonBrowseSource.setBounds(270, 50, 60, 30);
        label2.setBounds(30, 90, 300, 30);
        targetFile.setBounds(50, 130, 200, 30);
        buttonBrowseTarget.setBounds(270, 130, 60, 30);
        buttonConvert.setBounds(115, 220, 160, 30);
        radioTrue.setBounds(50, 170, 130, 30);
        radioFalse.setBounds(180, 170, 300, 30);
        /* 为各元素绑定事件监听器 */
        buttonBrowseSource.addActionListener(new BrowseAction()); // 为源文件浏览按钮绑定监听器，点击该按钮调用文件选择窗口
        buttonBrowseTarget.addActionListener(new BrowseAction()); // 为目标位置浏览按钮绑定监听器，点击该按钮调用文件选择窗口
        buttonConvert.addActionListener(new Convert());
        sourceFile.getDocument().addDocumentListener(new TextFieldAction());// 为源文件文本域绑定事件，如果文件是.txt类型，则禁用解密按钮；如果是.kcd文件，则禁用加密按钮。
        sourceFile.setEditable(false);// 设置源文件文本域不可手动修改
        targetFile.setEditable(false);// 设置目标位置文本域不可手动修改

        container.add(label1);
        container.add(label2);
        container.add(sourceFile);
        container.add(targetFile);
        container.add(buttonBrowseSource);
        container.add(buttonBrowseTarget);
        container.add(buttonConvert);

        container.add(radioTrue);
        container.add(radioFalse);

        ButtonGroup groupPDF = new ButtonGroup();
        groupPDF.add(radioTrue);
        groupPDF.add(radioFalse);

    }

    public static void main(String[] args) {
        new Launch();
    }
}

class BrowseAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(Launch.buttonBrowseSource)) {
            JFileChooser fcDlg = new JFileChooser();
            fcDlg.setDialogTitle("请选择XML文件");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "XML文件(*.xml)", "xml");
            fcDlg.setFileFilter(filter);
            int returnVal = fcDlg.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filePath = fcDlg.getSelectedFile().getPath();
                Launch.sourceFile.setText(filePath);
            }
        } else if (e.getSource().equals(Launch.buttonBrowseTarget)) {
            JFileChooser fcDlg = new JFileChooser();
            fcDlg.setDialogTitle("请选择PDF文件保存路径");
            fcDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fcDlg.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filePath = fcDlg.getSelectedFile().getPath();
                Launch.targetFile.setText(filePath);
            }
        }
    }
}

class Convert implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Launch.sourceFile.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请选择XML文件！");
        } else if (Launch.targetFile.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请选择PDF文件路径！");
        } else {
            String sourcePath = Launch.sourceFile.getText();
            String targetPath = Launch.targetFile.getText();
            File file = new File(sourcePath);
            String fileName = file.getName().toLowerCase().replaceAll(".xml", "");
            File result = new File(targetPath + "\\" + fileName + ".pdf");
            try {
                List<Word> wordList = XML.readXML(file);
                PDF pdf = new PDF();
                pdf.createPDF(result);
                if (Launch.radioTrue.isSelected()) pdf.printList(wordList, true);
                if (Launch.radioFalse.isSelected()) pdf.printList(wordList, false);
                pdf.close();
                //DB db = new DB();
                //db.createTable(fileName);
                //db.addWordList(fileName, wordList);
                //db.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

class TextFieldAction implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        ButtonAjust();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        ButtonAjust();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        ButtonAjust();
    }

    private void ButtonAjust() {
        String file = Launch.sourceFile.getText();
        if (file.endsWith("xml")) {
            Launch.buttonConvert.setEnabled(true);
        }
    }
}

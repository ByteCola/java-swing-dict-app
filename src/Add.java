
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Add extends JFrame implements ActionListener {
    JTextField 添加汉语解释_文本条;
    JTextField 添加英语单词_文本条;
    JTextField 添加英语单词_例句;
    JButton addbtn;
    JButton cancelbtn;
    Connection Con = null;
    Statement Stmt = null;

    public Add() {
        super("添加单词");
        this.setBounds(250, 250, 250, 250);
        this.setVisible(true);
        JPanel p1 = new JPanel();

        p1.add(new JLabel("输入要添加的单词:"));
        this.添加英语单词_文本条 = new JTextField(20);
        p1.add(this.添加英语单词_文本条);
        p1.add(new JLabel("输入添加的单词的解释:"));
        this.添加汉语解释_文本条 = new JTextField(20);
        p1.add(this.添加汉语解释_文本条);
        p1.add(new JLabel("输入添加的单词的例句:"));

        this.添加英语单词_例句 = new JTextField(20);
        p1.add(this.添加英语单词_例句);
        this.addbtn = new JButton("提交");
        this.cancelbtn = new JButton("取消");
        p1.add(this.addbtn);
        p1.add(this.cancelbtn);
        this.add(p1);
        this.addbtn.addActionListener(this);
        this.cancelbtn.addActionListener(this);
        this.validate();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addbtn) {
            if (!this.添加英语单词_文本条.getText().equals("") && !this.添加汉语解释_文本条.getText().equals("")) {
                try {
                    this.add();
                } catch (SQLException var3) {
                }
            } else {
                JOptionPane.showMessageDialog(this, "添加的单词或解释不能为空！", "警告", 2);
            }
        } else if (e.getSource() == this.cancelbtn) {
            this.dispose();
        }

    }

    public void add() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException var10) {
        }

        this.Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zidian?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone = GMT", "root", "12345678");
        this.Stmt = this.Con.createStatement();
        ResultSet rs = this.Stmt.executeQuery("SELECT * FROM data");
        boolean boo = false;

        while(boo = rs.next()) {
            String ename = rs.getString(1);
            String cname = rs.getString(2);
            String liju = rs.getString(3);
            if (ename.equals(this.添加英语单词_文本条.getText())) {
                JOptionPane.showMessageDialog(this, "此词汇已存在！", "警告", 2);
                break;
            }
        }

        if (!boo) {
            String s1 = "'" + this.添加英语单词_文本条.getText().trim() + "'";
            String s2 = "'" + this.添加汉语解释_文本条.getText().trim() + "'";
            String s3 = "'" + this.添加英语单词_例句.getText().trim() + "'";
            String temp = "INSERT INTO data VALUES (" + s1 + "," + s2 + "," + s3 + ")";
            this.Stmt.executeUpdate(temp);
            JOptionPane.showMessageDialog(this, "添加成功！", "恭喜", 2);
            this.dispose();
        }

        this.Con.close();
    }
}


import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Modify extends JFrame implements ActionListener {
    JTextField 修改英语单词_文本条;
    JTextField 修改汉语解释_文本条;
    JTextField 修改英语单词_例句;
    JButton modifybtn;
    JButton cancelbtn;
    Connection Con = null;
    Statement Stmt = null;

    Modify() {
        super("修改");
        this.setBounds(250, 250, 250, 250);
        this.setVisible(true);
        JPanel p = new JPanel();
        p.add(new JLabel("输入英语单词:"));
        this.修改英语单词_文本条 = new JTextField(20);
        p.add(this.修改英语单词_文本条);
        p.add(new JLabel("输入该单词修改的汉语解释:"));
        this.修改汉语解释_文本条 = new JTextField(20);
        p.add(this.修改汉语解释_文本条);
        p.add(new JLabel("输入英语例句:"));
        this.修改英语单词_例句 = new JTextField(20);
        p.add(this.修改英语单词_例句);
        this.modifybtn = new JButton("提交");
        this.cancelbtn = new JButton("取消");
        p.add(this.modifybtn);
        p.add(this.cancelbtn);
        this.modifybtn.addActionListener(this);
        this.cancelbtn.addActionListener(this);
        this.add(p);
        this.validate();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.modifybtn) {
            if (!this.修改英语单词_文本条.getText().equals("") && !this.修改汉语解释_文本条.getText().equals("")) {
                try {
                    this.mod();
                } catch (SQLException var3) {
                }
            } else {
                JOptionPane.showMessageDialog(this, "修改的单词或解释不能为空！", "警告", 2);
            }
        } else if (e.getSource() == this.cancelbtn) {
            this.dispose();
        }

    }

    public void mod() throws SQLException {
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
            if (ename.equals(this.修改英语单词_文本条.getText())) {
                String s1 = "'" + this.修改英语单词_文本条.getText().trim() + "'";
                String s2 = "'" + this.修改汉语解释_文本条.getText().trim() + "'";
                String s3 = "'" + this.修改英语单词_例句.getText().trim() + "'";
                String temp = "UPDATE data SET chinese=" + s2 + " ,liju=" + s3 + " WHERE english= " + s1;
                this.Stmt.executeUpdate(temp);
                JOptionPane.showMessageDialog(this, "记录修改成功！", "恭喜", 2);
                this.dispose();
                break;
            }
        }

        this.Con.close();
        if (!boo) {
            JOptionPane.showMessageDialog(this, "不存在此单词！", "警告", 2);
        }

    }
}

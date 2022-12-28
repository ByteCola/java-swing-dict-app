

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Delect extends JFrame implements ActionListener {
    JTextField 删除单词_文本条;
    JButton delbtn;
    JButton cancelbtn;
    Connection Con = null;
    Statement Stmt = null;

    Delect() {
        super("删除单词");
        this.setBounds(250, 250, 250, 200);
        this.setVisible(true);
        JPanel p1 = new JPanel();
        p1.add(new JLabel("输入要删除的单词:"));
        this.删除单词_文本条 = new JTextField(20);
        p1.add(this.删除单词_文本条);
        this.delbtn = new JButton("删除");
        this.cancelbtn = new JButton("取消");
        p1.add(this.delbtn);
        p1.add(this.cancelbtn);
        this.add(p1);
        this.delbtn.addActionListener(this);
        this.cancelbtn.addActionListener(this);
        this.validate();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.delbtn) {
            if (this.删除单词_文本条.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "删除的单词不能为空！", "警告", 2);
            } else {
                try {
                    this.del();
                } catch (SQLException var3) {
                }
            }
        } else if (e.getSource() == this.cancelbtn) {
            this.dispose();
        }

    }

    public void del() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException var7) {
        }

        this.Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zidian?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone = GMT", "root", "123456");
        this.Stmt = this.Con.createStatement();
        ResultSet rs = this.Stmt.executeQuery("SELECT * FROM data");
        boolean boo = false;

        while(boo = rs.next()) {
            String ename = rs.getString(1);
            String cname = rs.getString(2);
            if (ename.equals(this.删除单词_文本条.getText())) {
                String s1 = "'" + this.删除单词_文本条.getText().trim() + "'";
                String temp = "DELETE FROM data WHERE english=" + s1;
                this.Stmt.executeUpdate(temp);
                JOptionPane.showMessageDialog(this, "成功删除记录！", "恭喜", 2);
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

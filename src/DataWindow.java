
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class DataWindow extends JFrame implements ActionListener {
    JTextField englishtext;
    JTextArea lijutext;
    JTextField chinesetext;
    JTextField geshutext;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JLabel label;
    JLabel label2;
    JMenuBar mbar;
    JMenu mfile;
    JMenu medit;
    JMenuItem edic;
    JMenuItem cdic;
    JMenuItem quit;
    JMenuItem addedit;
    JMenuItem modedit;
    JMenuItem deledit;

    public DataWindow() {
    }

    public DataWindow(String s, int x, int y, int w, int h) {
        this.init(s);
        this.setLocation(x, y);
        this.setSize(w, h);
        this.setVisible(true);
        this.setDefaultCloseOperation(2);
    }

    void init(String s) {
        this.setTitle(s);
        this.mbar = new JMenuBar();
        this.setJMenuBar(this.mbar);
        this.mbar.setOpaque(true);
        this.mfile = new JMenu("文件");
        this.medit = new JMenu("编辑");
        this.edic = new JMenuItem("英汉词典");
        this.cdic = new JMenuItem("汉英词典");
        this.quit = new JMenuItem("  退出");
        this.addedit = new JMenuItem("添加词汇");
        this.modedit = new JMenuItem("修改词汇");
        this.deledit = new JMenuItem("删除词汇");
        this.label2 = new JLabel("查询结果");
        this.label = new JLabel("输入英语单词:");
        this.mbar.add(this.mfile);
        this.mbar.add(this.medit);
        this.mfile.add(this.edic);
        this.mfile.addSeparator();
        this.mfile.add(this.cdic);
        this.mfile.addSeparator();
        this.mfile.add(this.quit);
        this.medit.add(this.addedit);
        this.medit.addSeparator();
        this.medit.add(this.modedit);
        this.medit.addSeparator();
        this.medit.add(this.deledit);
        this.edic.addActionListener(this);
        this.cdic.addActionListener(this);
        this.quit.addActionListener(this);
        this.addedit.addActionListener(this);
        this.modedit.addActionListener(this);
        this.deledit.addActionListener(this);
        this.englishtext = new JTextField(8);
        this.lijutext = new JTextArea(8, 15);
        this.chinesetext = new JTextField(8);
        this.geshutext = new JTextField(10);
        this.b1 = new JButton("查询");
        this.b2 = new JButton("添加");
        this.b3 = new JButton("修改");
        this.b4 = new JButton("刪除");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        p1.add(this.geshutext);
        p1.add(this.label);
        p1.add(this.englishtext);
        p1.add(this.b1);
        p1.add(this.label2);
        p1.add(this.chinesetext);
        p2.add(this.b2);
        p2.add(this.b3);
        p2.add(this.b4);
        this.add(p1, "North");
        this.add(p2, "South");
        this.add(new JScrollPane(this.lijutext), "Center");
        this.b1.addActionListener(this);
        this.b2.addActionListener(this);
        this.b3.addActionListener(this);
        this.b4.addActionListener(this);
        this.englishtext.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != this.b1 && e.getSource() != this.englishtext) {
            if (e.getSource() == this.edic) {
                this.label.setText("输入英语单词:");
                this.b1.setVisible(true);
            } else if (e.getSource() == this.cdic) {
                this.label.setText("输入汉语意思:");
                this.b1.setVisible(true);
            } else if (e.getSource() != this.b2 && e.getSource() != this.addedit) {
                if (e.getSource() != this.b3 && e.getSource() != this.modedit) {
                    if (e.getSource() != this.b4 && e.getSource() != this.deledit) {
                        if (e.getSource() == this.quit) {
                            System.exit(0);
                        }
                    } else {
                        new Delect();
                    }
                } else {
                    new Modify();
                }
            } else {
                new Add();
            }
        } else {
            this.chinesetext.setText("");
            if (this.englishtext.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "查询对象不能为空！", "警告", 2);
            } else {
                try {
                    this.search();
                } catch (SQLException var3) {
                }
            }
        }

    }

    public void search() throws SQLException {
        String regex1 = "[a-zA-Z]+";
        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException var9) {
            var9.printStackTrace();
        }

        Connection Ex1Con = DriverManager.getConnection("jdbc:mysql://localhost:3309/zidian?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "123456");
        Statement Ex1Stmt = Ex1Con.createStatement();
        String cname;
        String ename;
        String liju;
        ResultSet rs2;
        if (this.label.getText().equals("输入英语单词:")) {
            rs2 = Ex1Stmt.executeQuery("SELECT * FROM data");

            while(rs2.next()) {
                ename = rs2.getString(1);
                cname = rs2.getString(2);
                liju = rs2.getString(3);
                if (ename.equals(this.englishtext.getText())) {
                    if (cname.matches(regex1)) {
                        JOptionPane.showMessageDialog(this, "该单词的中文意思出错！", "警告", 2);
                        this.lijutext.setText("");
                        break;
                    }

                    this.chinesetext.setText(cname);
                    this.lijutext.setText("该单词的例句为：" + liju);
                    this.lijutext.validate();
                }
            }

            if (this.chinesetext.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "查无此单词！", "警告", 2);
                this.lijutext.setText("");
            }
        } else if (this.label.getText().equals("输入汉语意思:")) {
            rs2 = Ex1Stmt.executeQuery("SELECT * FROM data WHERE chinese LIKE '%" + this.englishtext.getText() + "%'");

            while(rs2.next()) {
                ename = rs2.getString(1);
                cname = rs2.getString(2);
                liju = rs2.getString(3);
                this.chinesetext.setText(ename + '\n');
                this.lijutext.setText("该单词的例句为：" + liju);
            }

            if (this.chinesetext.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "查无此单词！", "警告", 2);
                this.lijutext.setText("");
            }
        }

        rs2 = Ex1Stmt.executeQuery("SELECT count(*) as result FROM data");

        while(rs2.next()) {
            count = rs2.getInt(1);
            this.geshutext.setText("字典单词个数" + count);
            Ex1Con.close();
        }

    }
}

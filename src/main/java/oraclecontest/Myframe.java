package oraclecontest;

import action.dbnetaction;
import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;
import control.Actionlisten;
import view.chuang;

import javax.swing.*;
import java.awt.*;


public class Myframe extends JFrame {
  public chuang chuang;// 弹窗
  public Actionlisten actionlisten;// 添加监听
  public JLabel lb_username;
  public JComboBox tx_username;
  public JComboBox tx_sqltype;// 插入，修改、删除、查询、分页查询可选语句生成
  public String[] item = {"BH_RK", "OUTLN"};
  public String[] sqltypeitem = {"查询", "新增", "修改", "删除", "liger表", "json传值", "传值不转码", "传值转码",
      "插入语句", "修改语句", "删除语句", "查询语句", "分页查询语句"};
  public JLabel lb_tablename;
  public JLabel lb_url;// 显示当前连接的url
  public JTextField tx_tablename;

  public JButton btn_submit;
  public JButton btn_fuzhi;
  public JButton tantest;
  public JTextArea jf;
  public JPanel panelOutput;
  public static Myframe myframe;

  public static Myframe getMyFrame() {
    if (myframe == null) {
      myframe = new Myframe();
    }
    return myframe;
  }

  private Myframe() {
    actionlisten = new Actionlisten();

    btn_submit = new JButton("生成语句");
    btn_submit.setFont(new Font("宋体", Font.PLAIN, 25));


    jf = new JTextArea(5, 6);
    jf.setFont(new Font("宋体", Font.PLAIN, 25));
    jf.setLineWrap(true); // 激活自动换行功能
    jf.setWrapStyleWord(true); // 激活断行不断字功能
    JScrollPane jsp = new JScrollPane(jf); // 添加滚动条


    lb_username = new JLabel("用户名");
    lb_username.setFont(new Font("宋体", Font.PLAIN, 25));

    tx_username = new JComboBox(item);
    tx_username.setFont(new Font("宋体", Font.PLAIN, 20));

    lb_tablename = new JLabel("表名");
    lb_tablename.setFont(new Font("宋体", Font.PLAIN, 25));

    tx_tablename = new JTextField();
    tx_tablename.setFont(new Font("宋体", Font.PLAIN, 25));

    tx_sqltype = new JComboBox(sqltypeitem);
    tx_sqltype.setFont(new Font("宋体", Font.PLAIN, 25));

    btn_fuzhi = new JButton("复制");
    btn_fuzhi.setFont(new Font("宋体", Font.PLAIN, 25));

    tantest = new JButton("更换数据库");
    tantest.setFont(new Font("宋体", Font.PLAIN, 25));

    lb_url = new JLabel("当前地址");
    lb_url.setFont(new Font("宋体", Font.PLAIN, 20));


    this.setSize(900, 700);
    this.add(lb_username);
    this.add(tx_username);
    this.add(btn_fuzhi);
    this.add(lb_tablename);
    this.add(tx_tablename);
    this.add(tx_sqltype);
    this.add(lb_url);
    this.add(jsp);
    this.setLayout(null);
    this.add(btn_submit);
    this.add(tantest);
    tantest.setBounds(600, 600, 200, 50);
    lb_username.setBounds(20, 10, 80, 20);
    tx_username.setBounds(160, 10, 200, 30);
    lb_tablename.setBounds(400, 10, 80, 20);
    tx_tablename.setBounds(500, 10, 200, 30);
    tx_sqltype.setBounds(730, 10, 150, 30);
    btn_fuzhi.setBounds(180, 600, 100, 50);
    btn_submit.setBounds(350, 600, 200, 50);
    lb_url.setBounds(40, 550, 800, 30);
    jsp.setBounds(30, 50, 800, 500);
    btn_fuzhi.addActionListener(actionlisten);
    btn_fuzhi.setActionCommand("fuzhi");
    btn_submit.addActionListener(actionlisten);
    btn_submit.setActionCommand("test");
    tantest.addActionListener(actionlisten);
    tantest.setActionCommand("tan");


    chuang = new chuang();// 加入弹窗


    this.setTitle("数据库创建转换");
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);



  }

  public static void main(String[] args) {
    Myframe.getMyFrame();
    new dbnetaction().getusernames();// 获取所有用户名
    JdkDataSource.jdkmysql();
  }

}

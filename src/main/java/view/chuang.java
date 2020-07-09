package view;

import control.Actionlisten;

import javax.swing.*;
import java.awt.*;



public class chuang extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6495726932339287789L;
	public JLabel lb_ip, lb_port, lb_dbname,lb_username,lb_password;
	public TextField tx_ip, tx_port, tx_dbname,tx_username,tx_password;
	public JButton bt_resert1;
	public Image imgback;
	public Resertpanel reset;
	public Actionlisten actionlisten=new Actionlisten(); 
	public chuang() {
		reset=new Resertpanel();
		this.setTitle("修改数据库连接");
		this.setSize(500, 600);
		// TODO 自动生成的构造函数存根
		this.setLayout(null);
		lb_ip = new JLabel("ip");
		lb_port = new JLabel("端口号");
		lb_dbname = new JLabel("数据库名");
		lb_username = new JLabel("用户名");
		lb_password = new JLabel("密码");
		
		lb_ip.setFont(new Font("宋体", Font.PLAIN, 25));
		lb_port.setFont(new Font("宋体", Font.PLAIN, 25));
		lb_dbname.setFont(new Font("宋体", Font.PLAIN, 25));
		lb_username.setFont(new Font("宋体", Font.PLAIN, 25));
		lb_password.setFont(new Font("宋体", Font.PLAIN, 25));
		
		lb_ip.setForeground(Color.black);
		lb_port.setForeground(Color.black);
		lb_dbname.setForeground(Color.black);
		lb_username.setForeground(Color.black);
		lb_password.setForeground(Color.black);
		
		
		tx_ip = new TextField();
		tx_port = new TextField();
		tx_dbname = new TextField();
		tx_username = new TextField();
		tx_password = new TextField();
		
		
		tx_ip.setFont(new Font("宋体", Font.PLAIN, 25));
		tx_port.setFont(new Font("宋体", Font.PLAIN, 25));
		tx_dbname.setFont(new Font("宋体", Font.PLAIN, 25));
		tx_username.setFont(new Font("宋体", Font.PLAIN, 25));
		tx_password.setFont(new Font("宋体", Font.PLAIN, 25));
		
		bt_resert1 = new JButton("确定");
		bt_resert1.setFont(new Font("宋体", Font.PLAIN, 25));
		bt_resert1.setSize(80, 50);
		
		this.add(lb_ip);
		this.add(lb_port);
		this.add(lb_dbname);
		this.add(lb_username);
		this.add(lb_password);
		
		this.add(tx_ip);
		this.add(tx_port);
		this.add(tx_dbname);
		this.add(tx_username);
		this.add(tx_password);
		
		this.add(bt_resert1);
		lb_ip.setBounds(20, 20, 80, 50);
		lb_port.setBounds(20, 100, 80, 50);
		lb_dbname.setBounds(10, 180, 100, 50);
		lb_username.setBounds(10, 260, 100, 50);
		lb_password.setBounds(10, 340, 100, 50);
		
		
		
		tx_ip.setBounds(120, 20, 300, 50);
		tx_port.setBounds(120, 100, 300, 50);
		tx_dbname.setBounds(120, 180, 300, 50);
		tx_username.setBounds(120, 260, 300, 50);
		tx_password.setBounds(120, 340, 300, 50);
		
		bt_resert1.setBounds(180, 440, 150, 50);
		bt_resert1.addActionListener(actionlisten);
		bt_resert1.setActionCommand("url");
		this.setLocationRelativeTo(null);
		this.add(reset);
	}
	class Resertpanel extends JPanel{
		public Resertpanel() {
			this.setSize(400, 400);
			
			imgback = Toolkit.getDefaultToolkit().getImage("img/back.jpg");
		}
		
		@Override
		protected void paintComponent(Graphics g) {
		// TODO 自动生成的方法存根
			super.paintComponents(g);
			g.drawImage(imgback, 0, 0, 400,400,this);
		}
	}
	
}

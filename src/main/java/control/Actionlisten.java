package control;

import action.dbnetaction;
import oraclecontest.Myframe;
import tools.ClipBoardUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Actionlisten implements ActionListener {

	public dbnetaction	dbnetaction	= new dbnetaction();

	/**
	 * 监听的方法
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("test")) {// 生成语句
			String username = (String) Myframe.myframe.tx_username.getSelectedItem();// 用户名
			String tablename = Myframe.myframe.tx_tablename.getText();// 表名
			if ("".equals(username)) {
				JOptionPane.showMessageDialog(null, "用户名不能为空");
			} else {
				if ("".equals(tablename)) {// 如果用户输入的表名是空，则查询用户所有表的信息
					dbnetaction.getallteXt(username);
				} else {// 如果不为空，那么就查询对应的用户和表
					dbnetaction.getteXt(username, tablename);
				}

			}

		}
		if (e.getActionCommand().equals("fuzhi")) {// 复制面板上的内容
			String fuzhitexts = "";
			fuzhitexts = Myframe.myframe.jf.getText();

			ClipBoardUtil.setSysClipboardText(fuzhitexts);
		}
		if (e.getActionCommand().equals("tan")) {// 弹窗
			Myframe.myframe.chuang.setVisible(true);
			// 下面是获取当前的数据库配置到界面当中去
			String urlString = "";
			String url = urlString.substring(urlString.indexOf("@") + 1, urlString.length());
			String ip = url.substring(0, url.indexOf(":"));
			String url2 = url.substring(url.indexOf(":") + 1, url.length());
			String port = url2.substring(0, url2.indexOf(":"));
			String dbname = url2.substring(url2.indexOf(":") + 1, url2.length());
			String username = "";
			String password = "";
			Myframe.myframe.chuang.tx_ip.setText(ip);
			Myframe.myframe.chuang.tx_port.setText(port);
			Myframe.myframe.chuang.tx_dbname.setText(dbname);
			Myframe.myframe.chuang.tx_username.setText(username);
			Myframe.myframe.chuang.tx_password.setText(password);
		}
		if (e.getActionCommand().equals("url")) {// 更改数据库连接配置
			String ip = Myframe.myframe.chuang.tx_ip.getText();// 获取ip
			String port = Myframe.myframe.chuang.tx_port.getText();// 获取端口号
			String dbname = Myframe.myframe.chuang.tx_dbname.getText();// 获取数据库名
			String username = Myframe.myframe.chuang.tx_username.getText();// 获取用户名
			String password = Myframe.myframe.chuang.tx_password.getText();// 获取密码
			if ("".equals(ip)) {
				JOptionPane.showMessageDialog(null, "ip不能为空");
			} else if ("".equals(port)) {
				JOptionPane.showMessageDialog(null, "端口号不能为空");
			} else if ("".equals(dbname)) {
				JOptionPane.showMessageDialog(null, "数据库名不能为空");
			} else if ("".equals(username)) {
				JOptionPane.showMessageDialog(null, "用户名不能为空");
			} else if ("".equals(password)) {
				JOptionPane.showMessageDialog(null, "密码不能为空");
			} else {
				String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dbname;
				Myframe.myframe.chuang.setVisible(false);

//				oraurl.url = url;
//				oraurl.user = username;
//				oraurl.password = password;
//				// System.out.println("修改前的url"+oraurl.url);
//
//				config config = oraclecontest.config.getconfig();
//				config.updateconfig(url, username, password);
				//config.createdateSource(config.ORACLE);// 重新连接数据源

				JOptionPane.showMessageDialog(null, "连接成功,已切换数据库");
				Myframe.myframe.lb_url.setText("当前url:" + url.substring(url.indexOf("@") + 1, url.length()));
				new dbnetaction().getusernames();

			}

		}
	}
}

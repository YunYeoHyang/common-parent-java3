package com.czxy.bos.util;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.czxy.bos.constant.Constants;

public class MailUtil {
	public static String smtp_host = "smtp.163.com"; // 网易
	public static String username = "itcast_lt@163.com"; // 邮箱
	public static String password = "1qaz2wsx"; // 授权码（登录邮箱-->设置-->邮箱安全设置-->客户端授权密码），这里不是邮箱的密码，切记！
	public static String from = "itcast_lt@163.com"; // 来源邮箱，使用当前账号
	public static String activeUrl = Constants.BOS_FORE_URL + "/customer/activeMail.html"; // 激活地址

	public static void sendMail(String to, String subject, String text) throws Exception {
		// 1 准备发送邮件需要的参数
		Properties props = new Properties();
		// 设置主机地址 smtp.qq.com smtp.126.com smtp.163.com
		props.put("mail.smtp.host", smtp_host);
		// 是否打开验证:只能设置true，必须打开
		props.put("mail.smtp.auth", true);

		// 2 连接邮件服务器
		Session session = Session.getDefaultInstance(props);
		// 3 创建邮件信息
		MimeMessage message = new MimeMessage(session);

		// 4 设置发送者
		InternetAddress fromAddress = new InternetAddress(from);
		message.setFrom(fromAddress);
		// 5 设置接收者
		InternetAddress toAddress = new InternetAddress(to);
		// to:直接接收者 cc：抄送 bcc暗送
		message.setRecipient(RecipientType.TO, toAddress);
		// 6 设置主题
		message.setSubject(subject);
		// 7 设置正文
		message.setText(text);

		// 设置HTML方式发送
		message.setContent(text, "text/html;charset=utf-8");

		// 8 发送:坐火箭
		Transport transport = session.getTransport("smtp");// 参数不能少，表示的是发送协议
		// 登录邮箱,此处的密码是授权码
		transport.connect(username, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();

		System.out.println("ok");
	}
	
	public static void main(String[] args) {
		try {
			MailUtil.sendMail("1638064027@qq.com", "激活测试", "test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
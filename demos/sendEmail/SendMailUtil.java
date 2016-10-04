package com.faceblog.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.faceblog.entity.UserMail;

public class SendMailUtil extends Thread{
	private String from = "qcm3771787@163.com";
    //邮箱的用户名
	private String username = "qcm3771787";
    //邮箱的密码
	private String password = "3771787";
    //发送邮件的服务器地址
	private String host = "smtp.163.com";
	//the receptor of the email
	private String receptor="qcm3771787@163.com";
	//the theme of the email
	private String subject=" ";
	// the content of the email
	private String content=" ";
	
	//constructor
	public SendMailUtil(String receptor,String subject,String content) {
		// TODO Auto-generated constructor stub
		this.receptor = receptor;
		this.subject = subject;
		this.content = content;
	}
	
	@Override
    public void run() {
        try{
        	//properties extends Hashtable
            Properties prop = new Properties();
            prop.setProperty("mail.host", host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
            //instance is a method used by single mode class to make its instance 
            Session session = Session.getInstance(prop);
            //Set the debug setting for this Session.
            session.setDebug(true);
            //Get a Transport object that implements 
            //this user's desired Transport protcol.
            Transport ts = session.getTransport();
            ts.connect(host, username, password);
            /*
                Connect to the specified address. This method provides a simple 
            authentication scheme that requires a username and password.
			    If the connection is successful, an "open" ConnectionEvent is 
			delivered to any ConnectionListeners on this service.
			    It is an error to connect to an already connected service.
			    The implementation in the Service class will collect defaults 
			for the host, user, and password from the session, from the URLName 
			for this service, and from the supplied parameters and then call the 
			protocolConnect method. If the protocolConnect method returns false, 
			the user will be prompted for any missing information and the 
			protocolConnect method will be called again. The subclass should 
			override the protocolConnect method. The subclass should also implement 
			the getURLName method, or use the implementation in this class.
				On a successful connection, the setURLName method is called with 
			a URLName that includes the information used to make the connection, 
			including the password.
				If the username passed in is null, a default value will be chosen as 
			described above. If the password passed in is null and this is the 
			first successful connection to this service, the user name and the 
			password collected from the user will be saved as defaults for subsequent 
			connection attempts to this same service when using other Service object 
			instances (the connection information is typically always saved within a 
			particular Service object instance). The password is saved using the Session 
			method setPasswordAuthentication. If the password passed in is not null, 
			it is not saved, on the assumption that the application is managing 
			passwords explicitly.
				Parameters:
			host - the host to connect to
			user - the user name
			password - this user's password 
              */
            Message message = createEmail(session);
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	private Message createEmail(Session session) 
			throws Exception, MessagingException {
		// TODO Auto-generated method stub
		// 1. establish a message instance using session
		MimeMessage message = new MimeMessage(session);
		// 2. set the from, recipient, subject and content 
        message.setFrom(new InternetAddress(from));//set where the email is from
        message.setRecipient(Message.RecipientType.TO, //set the recipient
        		new InternetAddress(receptor));
        message.setSubject("用户注册邮件");
        
        String info = content;
        message.setContent(info, "text/html;charset=UTF-8");
        message.saveChanges();
        // 3. return the message 
        return message;
	}
}

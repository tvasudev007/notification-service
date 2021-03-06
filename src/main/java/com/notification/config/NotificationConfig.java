package com.notification.config;

import java.util.Map;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.notification.service.email.EmailRequestGenerator;

@Configuration
public class NotificationConfig {

	@Value("${email.host}")
	private String host;

	@Value("${email.port}")
	private int port;

	@Value("${email.username}")
	private String username;

	@Value("${email.password}")
	private String password;

	@Value("${email.protocol}")
	private String protocol;

	public JavaMailSender emailSender() {
		JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
		emailSender.setHost(host);
		emailSender.setPort(port);
		emailSender.setUsername(username);
		emailSender.setPassword(password);

		Properties prop = new Properties();
		prop.setProperty("mail.transport.protocol", protocol);
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		prop.setProperty("mail.debug", "true");

		emailSender.setJavaMailProperties(prop);

		return emailSender;
	}

	public VelocityEngine velocityEngine() {
		Properties props = new Properties();
		props.setProperty("resource.loader", "class");
		props.setProperty("input.encoding", "UTF-8");
		props.setProperty("output.encoding", "UTF-8");
		props.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		return new VelocityEngine(props);
	}

	public EmailRequestGenerator requestGenerator(Map<String, Object> emailModel) {
		return new EmailRequestGenerator(velocityEngine(), emailModel);

	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getProtocol() {
		return protocol;
	}

}

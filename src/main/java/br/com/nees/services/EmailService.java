package br.com.nees.services;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.nees.dao.EmailDao;
import br.com.nees.enums.StatusEmail;
import br.com.nees.model.EmailLog;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailDao emailRepository;

	// vai receber o membro e a atividade -> e o tipo, e a data ou prazo de entrega
	// no texto
	// generico para o shludes, nova tarefa postada
//	@Async
//	public void enviaEmail(String to, String subject, String text) {
//		SimpleMailMessage email = new SimpleMailMessage();
//		email.setTo(to);
//		email.setSubject(subject);
//		email.setText(text);
//		mailSender.send(email);
//
//	}

	@Async
	public void enviaEmail(EmailLog emailModel) {
		Calendar cal = Calendar.getInstance();

		emailModel.setSendDateEmail(new Timestamp(cal.getTimeInMillis()));

		MimeMessage email = this.mailSender.createMimeMessage();

		try {
			MimeMessageHelper message = new MimeMessageHelper(email, true, "UTF-8"); // true = multipart

			message.setSubject(emailModel.getSubject());
			message.setTo(emailModel.getEmailTo());
			message.setText(emailModel.getText(), true); // true = isHtml

//			SimpleMailMessage email = new SimpleMailMessage();
//			email.setTo(emailModel.getEmailTo());
//			email.setSubject(emailModel.getSubject());
//			email.setText(emailModel.getText());
			mailSender.send(email);

			emailModel.setStatusEmail(StatusEmail.SENT);
		} catch (MessagingException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			emailModel.setStatusEmail(StatusEmail.ERROR);
		} finally {
			emailRepository.save(emailModel);
		}

	}

}

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleEmailNotifier implements EmailNotifier {//Class implementing the emailnotifier interface to send broadcast emails

    private final String senderEmail;
    private final String senderPassword;

    public SimpleEmailNotifier(String senderEmail, String senderPassword) {//Constructor to initialize the sender email and password
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
    	//Dummy account created for testing: siddharthmurali786@gmail.com with password ppxjxbmvjdzxpnna
    }

    public void sendNotification(String subject, String message, ArrayList<Client> clients) {//Function to send an email notification to all clients
        Properties properties = new Properties();//Setting properties for Simple Mail Transfer Protocol
        properties.put("mail.smtp.host", "smtp.gmail.com");//Assumes that the sender's email is gmail
        properties.put("mail.smtp.port", "587");//Use port 587 for smtp
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);//Authenticate email id and password
            }
        });

        try {
            for (Client client : clients) {
                String clientEmail = client.getEmail();//Obtain the email id of each client
                //Set message parameters:
                Message emailMessage = new MimeMessage(session);
                emailMessage.setFrom(new InternetAddress(senderEmail));
                emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(clientEmail));
                emailMessage.setSubject(subject);
                emailMessage.setText(message);

                Transport.send(emailMessage);//Send the email

                System.out.println("Email sent to " + clientEmail + " with subject: " + subject);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}


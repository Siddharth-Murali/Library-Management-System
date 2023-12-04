import java.util.ArrayList;

public interface EmailNotifier {//Interface implemented for email notification service
    void sendNotification(String subject, String message, ArrayList<Client> clients);
}


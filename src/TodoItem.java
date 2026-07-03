import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoItem {
    private final String title;
    private final String category;
    private final LocalDateTime reminder;

    public TodoItem(String title, String category, LocalDateTime reminder) {
        this.title = title;
        this.category = category;
        this.reminder = reminder;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getReminder() {
        return reminder;
    }

    @Override
    public String toString() {
        String when = reminder != null ? reminder.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "No reminder";
        return String.format("%s [%s] - %s", title, category, when);
    }
}

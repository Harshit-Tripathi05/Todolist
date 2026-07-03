import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoListApp extends JFrame {
    private final DefaultListModel<TodoItem> listModel = new DefaultListModel<>();
    private final JList<TodoItem> taskList = new JList<>(listModel);
    private final JTextField titleField = new JTextField(20);
    private final JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Work", "Personal", "Shopping", "Study", "Other"});
    private final JSpinner reminderSpinner;
    private final List<TodoItem> items = new ArrayList<>();

    public TodoListApp() {
        super("Minimal Java Todo List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Task:"), gbc);
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryBox.setEditable(true);
        formPanel.add(categoryBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Reminder:"), gbc);
        gbc.gridx = 1;
        reminderSpinner = new JSpinner(new SpinnerDateModel());
        reminderSpinner.setEditor(new JSpinner.DateEditor(reminderSpinner, "yyyy-MM-dd HH:mm"));
        formPanel.add(reminderSpinner, gbc);

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(this::onAddTask);
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> onRemoveTask());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(formPanel, BorderLayout.NORTH);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(taskList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Todo Items"));

        add(leftPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.CENTER);

        Timer reminderTimer = new Timer(60_000, e -> checkReminders());
        reminderTimer.setInitialDelay(0);
        reminderTimer.start();
    }

    private void onAddTask(ActionEvent event) {
        String title = titleField.getText().trim();
        String category = categoryBox.getSelectedItem().toString().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a task title.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDateTime reminder = LocalDateTime.ofInstant(((java.util.Date) reminderSpinner.getValue()).toInstant(), java.time.ZoneId.systemDefault());
        TodoItem item = new TodoItem(title, category.isEmpty() ? "Other" : category, reminder);
        items.add(item);
        listModel.addElement(item);

        titleField.setText("");
        titleField.requestFocus();
    }

    private void onRemoveTask() {
        int index = taskList.getSelectedIndex();
        if (index >= 0) {
            TodoItem removed = listModel.remove(index);
            items.remove(removed);
        }
    }

    private void checkReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<TodoItem> dueItems = new ArrayList<>();
        for (TodoItem item : new ArrayList<>(items)) {
            if (!item.getReminder().isAfter(now)) {
                dueItems.add(item);
                items.remove(item);
                listModel.removeElement(item);
            }
        }

        if (!dueItems.isEmpty()) {
            StringBuilder message = new StringBuilder("Reminder due:\n");
            for (TodoItem item : dueItems) {
                message.append(item.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Reminder", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TodoListApp app = new TodoListApp();
            app.setVisible(true);
        });
    }
}

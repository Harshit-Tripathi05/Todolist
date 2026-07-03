# Java Todo List App

A minimal Java Swing todo list application with categories and reminders.

## Files
- `src/TodoItem.java` - model for tasks.
- `src/TodoListApp.java` - main Swing UI application.

## Run
1. Open a terminal in `todolist-java`.
2. Compile:
   ```powershell
   javac -d out src\TodoItem.java src\TodoListApp.java
   ```
3. Run:
   ```powershell
   java -cp out TodoListApp
   ```

## Features
- Add tasks with title, category, and reminder date/time.
- Remove selected tasks.
- Reminder notifications appear when tasks are due.

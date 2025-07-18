package TaskScheduler;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import javax.swing.*;

public class TaskSchedularUI extends JFrame{
	private TaskManager manager = new TaskManager();
	private DefaultListModel<Task> listModel = new DefaultListModel<>();
	private JList<Task> taskJList = new JList<>(listModel);
	
	public TaskSchedularUI() {
		setTitle("Smart Task Scheduler");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		manager.loadTaskFromFile();
		
		JPanel inputPanel = new JPanel(new GridLayout(6, 2));
		JTextField titleField = new JTextField();
		JComboBox<String> priorityBox = new JComboBox<>(new String[] {"1", "2", "3"});
		JTextField deadlineField = new JTextField("YYYY-MM-DD");
		
		inputPanel.add(new JLabel("Title : "));
		inputPanel.add(titleField);
		inputPanel.add(new JLabel("Priority (1=High) : "));
		inputPanel.add(priorityBox);
		inputPanel.add(new JLabel("DeadLine : "));
		inputPanel.add(deadlineField);
		
		JButton addButton = new JButton("Add Task");
		JButton removeButton = new JButton("Remove Selected");
		JButton saveButton = new JButton("Save Task");
		JButton todayButton = new JButton("Show Today,s Tasks");
		JButton highPriorityButton = new JButton("Show High Priority Tasks");
		JButton allButton = new JButton("Show All Tasks");
		
		inputPanel.add(addButton);
		inputPanel.add(removeButton);
		inputPanel.add(saveButton);
		inputPanel.add(todayButton);
		inputPanel.add(highPriorityButton);
		inputPanel.add(allButton);
		
		addButton.addActionListener(e -> {
			try {
				String title = titleField.getText();
				int priority = Integer.parseInt((String) priorityBox.getSelectedItem());
				LocalDate deadline = LocalDate.parse(deadlineField.getText());
				Task task = new Task(title, priority, deadline);
				manager.addTask(task);
				refreshList(manager.getTasks());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid input");
			}
		});
		
		removeButton.addActionListener(e -> {
			Task selected = taskJList.getSelectedValue();
			if(selected != null) {
				manager.removeTask(selected);
				refreshList(manager.getTasks());
			}
		});
		
		saveButton.addActionListener(e -> {
			manager.saveTasksToFile();
			JOptionPane.showMessageDialog(this, "Task saved");
		});
		
		todayButton.addActionListener(e -> refreshList(manager.getTodayTasks()));
		
		highPriorityButton.addActionListener(e -> refreshList(manager.getHighPriorityTasks()));
		
		allButton.addActionListener(e -> refreshList(manager.getTasks()));
		
		add(new JScrollPane(taskJList),BorderLayout.CENTER);
		add(inputPanel,BorderLayout.SOUTH);
		refreshList(manager.getTasks());
	}
	
	private void refreshList(java.util.List<Task> tasks) {
		listModel.clear();
		for(Task task : tasks) {
			listModel.addElement(task);
		}
	}
}

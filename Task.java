package TaskScheduler;

import java.time.LocalDate;

public class Task implements Comparable<Task>{
	private String title;
	private int priority;
	private LocalDate deadline;
	
	public Task(String title, int priority, LocalDate deadline) {
		this.title = title;
		this.priority = priority;
		this.deadline = deadline;
	}
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public LocalDate getDeadline() {
		return deadline;
	}


	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}


	public int compareTo(Task other) {
		if(this.priority != other.priority) {
			return Integer.compare(this.priority, other.priority);
		}
		return this.deadline.compareTo(other.deadline);
	}

	@Override
	public String toString() {
		return "Task [title=" + title + ", priority=" + priority + ", deadline=" + deadline + "]";
	}
	
}

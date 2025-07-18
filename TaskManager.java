package TaskScheduler;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


public class TaskManager {
	private PriorityQueue<Task> tasks = new PriorityQueue<>();
	private static final String FILE_PATH = "tasks.json";
	private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
		public LocalDate deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) {
			return LocalDate.parse(json.getAsString());
		}
	}).registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
		public JsonElement serialize(LocalDate src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}).setPrettyPrinting().create();
	
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public void removeTask(Task task) {
		tasks.remove(task);
	}
	
	public List<Task> getTasks(){
		return new ArrayList<>(tasks);
	}
	
	public List<Task> getTodayTasks(){
		LocalDate today = LocalDate.now();
		return tasks.stream()
				.filter(t -> t.getDeadline().equals(today))
				.collect(Collectors.toList());
	}
	
	public List<Task> getHighPriorityTasks(){
		return tasks.stream()
				.filter(t -> t.getPriority() == 1).collect(Collectors.toList());
	}
	
	public void saveTasksToFile() {
		try(Writer writer = Files.newBufferedWriter(Paths.get(FILE_PATH))){
			List<Task> list = new ArrayList<>(tasks);
			gson.toJson(list,writer);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadTaskFromFile() {
		try(Reader reader = Files.newBufferedReader(Paths.get(FILE_PATH))){
			Type listType = new TypeToken<List<Task>>() {}.getType();
			List<Task> list = gson.fromJson(reader, listType);
			tasks.clear();
			if(list != null) tasks.addAll(list);
		}catch(IOException e) {
			System.out.println("No previous tasks found or error reading file.");
		}
	}
	
}

package com.controller;

import com.model.Task;
import com.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:5500") // allow frontend (adjust port if needed)
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	// Get all tasks
	@GetMapping
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	// Get a task by ID
	@GetMapping("/{id}")
	public Optional<Task> getTask(@PathVariable Long id) {
		return taskRepository.findById(id);
	}

	// Create a new task
	@PostMapping
	public Task createTask(@RequestBody Task task) {
		return taskRepository.save(task);
	}

	// Update a task
	@PutMapping("/{id}")
	public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
		Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id " + id));

		task.setTitle(taskDetails.getTitle());
		task.setDescription(taskDetails.getDescription());
		task.setStartDate(taskDetails.getStartDate()); // ✅ Updated field
		task.setEndDate(taskDetails.getEndDate()); // ✅ Renamed from deadline
		task.setCompleted(taskDetails.isCompleted());

		return taskRepository.save(task);
	}

	// Delete a task
	@DeleteMapping("/{id}")
	public String deleteTask(@PathVariable Long id) {
		taskRepository.deleteById(id);
		return "Task deleted with id " + id;
	}
}

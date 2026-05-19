package com.engalladofc.vector.repository;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class TaskRepository {

    private List<Task> tasks = new ArrayList<>();
    
    private Integer id=0;

    private String file = "C:\\Projects\\vector-api\\data\\tasks.csv";
    
    //===============================//
    //            ADD TASK           //
    //===============================//
    public void addTask(Task task) {
    	task.setId(id);
    	tasks.add(task);
    	id++;
    }
    
    
    //===============================//
    //          DELETE TASK          //
    //===============================//
    public void deleteTask(int id) {
        for(int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                return;
            }
        }
    }
    
    
    //===============================//
    //           EDIT TASK           //
    //===============================//
    public void editTask(int id, String title, String subject, String description, LocalDate deadline, Integer difficulty, Status status) {
        for(int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.get(i).setTitle(title);
                tasks.get(i).setSubject(subject);
                tasks.get(i).setDescription(description);
                tasks.get(i).setDeadline(deadline);
                tasks.get(i).setDifficulty(difficulty);
                tasks.get(i).setStatus(status);
                return;
            }
        }
    }
    
    
    //===============================//
    //           ALL TASK            //
    //===============================//
    public List<Task> getAllTasks() {
    	return tasks;
    }
    
    
    //===============================//
    //            ID TASK            //
    //===============================//
    public Task findById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    
    
    //===============================//
    //           READ TASK           //
    //===============================//
    public void readTasks() {
        File f = new File(file);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1); //-1 so reader does not drop empty values

                Integer id         = Integer.parseInt(fields[0]);
                String title       = fields[1];
                String subject     = fields[2];
                String description = fields[3];
                LocalDate deadline = fields[4].isEmpty() ? null : LocalDate.parse(fields[4]);
                Integer difficulty = fields[5].isEmpty() ? null : Integer.parseInt(fields[5]);
                Status status      = Status.valueOf(fields[6]);

                Task task = new Task(id, title, subject, description, deadline, difficulty, status);
                tasks.add(task);

                //updates the current task id so future tasks gets added with no duplicate id
                if (id >= this.id) this.id = id + 1;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    
    //===============================//
    //           SAVE TASK           //
    //===============================//
    public void saveTasks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                String line = task.getId() + ","
                    + task.getTitle() + ","
                    + task.getSubject() + ","
                    + task.getDescription() + ","
                    + (task.getDeadline()   == null ? "" : task.getDeadline().toString()) + ","
                    + (task.getDifficulty() == null ? "" : task.getDifficulty().toString()) + ","
                    + task.getStatus().name();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}

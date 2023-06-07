package com.akram.springbootbasics.task;

import com.akram.springbootbasics.ErrorHandling.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    List<Task> taskList = new ArrayList<>();
    private int nextTaskid=1;

    @GetMapping("/")
    List<Task> getAllTask(){
        return taskList;
    }

    @PostMapping("/")
    ResponseEntity createTask(@RequestBody Task task)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(400);
        if(task.getName() == null) {
            errorResponse.setErrorMessage("Inavalid Name provided");
            return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);

        }
        else if(task.getDueDate()==null){
          errorResponse.setErrorMessage("Invalid due date provided!!");
            return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
        }
        else if(task.completed == null){
         errorResponse.setErrorMessage("Invalid completed value provided");
            return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
        }

        task.setId(nextTaskid++);
        taskList.add(task);
        return  ResponseEntity.ok(task);

    }

    @GetMapping("/id/{id}")
    ResponseEntity getTaskById(@PathVariable("id") Integer id){

        var thetask = taskList.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        System.out.printf("task " + thetask);
        if(id == null || thetask == null ){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorMessage("Invalid Id");
            errorResponse.setErrorCode(404);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            //throw new IllegalArgumentException("Id parameter is required");
        }
        return ResponseEntity.ok(thetask);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity deleteTask(@PathVariable("id") Integer id){

        var task = taskList.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        if(id == null || task == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorMessage("Invalid Id");
            errorResponse.setErrorCode(404);
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }
        taskList.remove(task);
        return ResponseEntity.ok("Task Id " + id + " Deleted");
    }

    @GetMapping("/completed/{completed}")
    ResponseEntity getCompletedTask(@PathVariable("completed") Boolean completed){
        System.out.printf("completed value " + completed);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(400);
        if(completed == null){
            errorResponse.setErrorMessage("Invalid value in request!! Completed value sent is null");
            return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
        }
        var tasks = taskList.stream().filter(t -> t.getCompleted().equals(completed));
        List t= tasks.toList();
        Collections.sort(t, Comparator.comparingInt(Task::getId));
        if(taskList.isEmpty()){
            errorResponse.setErrorMessage("Task list is empty");
            return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(tasks);
    }

}

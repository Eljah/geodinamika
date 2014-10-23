package geodinamika.service;

import geodinamika.dao.TaskDao;
import geodinamika.model.Task;

import java.util.List;

/**
 * Created by Leon on 14.10.2014.
 */
public interface TaskManager extends GenericManager<Task, Long> {

    void setTaskDao(TaskDao taskDao);

    List<Task> search(String searchTerm);

    Task saveTask(Task task) throws UserExistsException;
    /*
    Task getTask(String taskId);

        void removeTask(Task task);
    */
}

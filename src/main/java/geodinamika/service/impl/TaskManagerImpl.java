package geodinamika.service.impl;

import geodinamika.dao.TaskDao;
import geodinamika.model.Role;
import geodinamika.model.Task;
import geodinamika.service.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Leon on 14.10.2014.
 */

@Service("taskManager")
public class TaskManagerImpl extends GenericManagerImpl<Task, Long> implements TaskManager {
    TaskDao taskDao;

    @Autowired
    public TaskManagerImpl(TaskDao taskDao) {
        super(taskDao);
        this.taskDao = taskDao;
    }


    @Override
    @Autowired
    public void setTaskDao(final TaskDao taskDao) {
        this.dao = taskDao;
        this.taskDao = taskDao;
    }

    @Override
    public List<Task> search(final String searchTerm) {
        return super.search(searchTerm, Task.class);
    }

    public Task saveTask(Task task) {
        return taskDao.saveTask(task);
    }

}

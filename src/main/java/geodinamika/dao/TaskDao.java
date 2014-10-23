package geodinamika.dao;

import geodinamika.model.Task;

/**
 * Created by Leon on 14.10.2014.
 */
public interface TaskDao extends GenericDao<Task, Long> {

/*
    List<Task> getTasks();
  */
    Task saveTask(Task task);



}

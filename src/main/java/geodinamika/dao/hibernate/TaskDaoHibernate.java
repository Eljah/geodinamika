package geodinamika.dao.hibernate;

import geodinamika.dao.TaskDao;
import geodinamika.model.Task;
import geodinamika.model.steps.Step;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Leon on 14.10.2014.
 */
@Repository
public class TaskDaoHibernate extends GenericDaoHibernate<Task, Long> implements TaskDao {


    public TaskDaoHibernate() {
        super(Task.class);
    }


    public List<Task> getTasks() {
        Query qry = getSession().createQuery("from Task t order by upper(t.description)");
        return qry.list();
    }

    public Task saveTask(Task task) {
        if (log.isDebugEnabled()) {
            log.debug("task's id: " + task.getId());
        }
        getSession().saveOrUpdate(task.getAction());
        for (Step step : task.getAction().getTransformSteps()) {
            getSession().saveOrUpdate(step);
        }
        getSession().saveOrUpdate(task);


        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getSession().flush();
        return task;
    }

}

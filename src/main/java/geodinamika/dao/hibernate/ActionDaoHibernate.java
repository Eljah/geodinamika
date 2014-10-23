package geodinamika.dao.hibernate;

import geodinamika.dao.ActionDao;
import geodinamika.dao.TaskDao;
import geodinamika.model.Action;
import geodinamika.model.Task;
import geodinamika.model.User;
import geodinamika.model.steps.Step;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActionDaoHibernate extends GenericDaoHibernate<Action, Long> implements ActionDao {
    public ActionDaoHibernate() {
        super(Action.class);
    }


    public List<Task> getActions() {
        Query qry = getSession().createQuery("from Action t order by upper(t.name)");
        return qry.list();
    }

    public Action saveAction(Action action) {
        //if (log.isDebugEnabled()) {
        log.debug("action's id: " + action.getId());
        // }
        getSession().saveOrUpdate(action);
        log.debug("action's id: " + action.getId());
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        for (Step step : action.getTransformSteps()) {
            getSession().saveOrUpdate(step);
        }
        getSession().flush();
        return action;
    }


    public Action save(Action action) {
        return this.saveAction(action);
    }

    public Action getActionByName(String actionname) {
        List actions = getSession().createCriteria(Action.class).add(Restrictions.eq("description", actionname)).list();
        if (actions.isEmpty()) {
            return null;
        } else {
            return (Action) actions.get(0);
        }
    }

}

package geodinamika.dao;

import geodinamika.model.Action;
import geodinamika.model.Task;
import geodinamika.model.steps.Step;

/**
 * Created by Leon on 15.10.2014.
 */
public interface ActionDao  extends GenericDao<Action, Long> {
    public Action getActionByName(String actionname);
}

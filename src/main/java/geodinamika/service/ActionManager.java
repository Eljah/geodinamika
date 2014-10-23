package geodinamika.service;

import geodinamika.dao.ActionDao;
import geodinamika.dao.TaskDao;
import geodinamika.model.Action;
import geodinamika.model.Role;
import geodinamika.model.Task;

import java.util.List;

/**
 * Created by Leon on 15.10.2014.
 */
public interface ActionManager  extends GenericManager<Action, Long>{
    void setActionDao(ActionDao actionDao);

    List<Action> search(String searchTerm);

    public Action getAction(String actionname);
}

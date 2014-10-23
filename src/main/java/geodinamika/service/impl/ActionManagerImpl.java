package geodinamika.service.impl;

import geodinamika.dao.ActionDao;
import geodinamika.dao.TaskDao;
import geodinamika.model.Action;
import geodinamika.model.Task;
import geodinamika.model.steps.Step;
import geodinamika.service.ActionManager;
import geodinamika.service.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Leon on 15.10.2014.
 */
@Service("actionManager")
public class ActionManagerImpl extends GenericManagerImpl<Action, Long> implements ActionManager {
    ActionDao actionDao;

    @Autowired
    public ActionManagerImpl(ActionDao actionDao) {
        super(actionDao);
        this.actionDao = actionDao;
    }


    @Override
    @Autowired
    public void setActionDao(final ActionDao actionDao) {
        this.dao = actionDao;
        this.actionDao = actionDao;
    }

    @Override
    public List<Action> search(final String searchTerm) {
        return super.search(searchTerm, Action.class);
    }

    @Override
    public Action getAction(String actionname) {
        return actionDao.getActionByName(actionname);
    }


}

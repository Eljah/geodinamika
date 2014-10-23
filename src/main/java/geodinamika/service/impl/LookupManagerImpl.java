package geodinamika.service.impl;

import geodinamika.dao.LookupDao;
import geodinamika.model.Action;
import geodinamika.model.LabelValue;
import geodinamika.model.Role;
import geodinamika.model.steps.Step;
import geodinamika.service.LookupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Autowired
    LookupDao dao;

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getName(), role1.getName()));
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllSteps() {
        List<Step> steps = dao.getSteps();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Step step1 : steps) {
            list.add(new LabelValue(step1.toString(), step1.toString()));
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllActions() {
        List<Action> actions = dao.getActions();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Action action1 : actions) {
            list.add(new LabelValue(action1.toString(), action1.toString()));
        }

        return list;
    }
}

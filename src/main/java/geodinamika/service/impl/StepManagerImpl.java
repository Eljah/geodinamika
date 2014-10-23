package geodinamika.service.impl;

import geodinamika.dao.RoleDao;
import geodinamika.dao.StepDao;
import geodinamika.model.Role;
import geodinamika.model.steps.Step;
import geodinamika.service.StepManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ilya Evlampiev on 16.10.2014.
 */
@Service("stepManager")
public class StepManagerImpl extends GenericManagerImpl<Step, Long> implements StepManager {

    StepDao stepDao;

    @Autowired
    public StepManagerImpl(StepDao stepDao) {
        super(stepDao);
        this.stepDao = stepDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<Step> getSteps(Step step) {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Step getStep(String stepname) {
        return stepDao.getStepByName(stepname);
    }


}

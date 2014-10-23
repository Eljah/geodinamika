package geodinamika.dao;

import geodinamika.model.steps.Step;

/**
 * Created by Ilya Evlampiev on 16.10.2014.
 */
public interface StepDao extends GenericDao<Step, Long>  {
    public Step getStepByName(String stepname);
}

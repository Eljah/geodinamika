package geodinamika.service;

import geodinamika.model.Role;
import geodinamika.model.steps.Step;

/**
 * Created by Ilya Evlampiev on 16.10.2014.
 */
public interface StepManager extends GenericManager<Step,Long> {
    Step getStep(String stepname);

}

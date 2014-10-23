package geodinamika.dao.hibernate;

import geodinamika.dao.StepDao;
import geodinamika.model.Role;
import geodinamika.model.steps.Step;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ilya Evlampiev on 16.10.2014.
 */
@Repository
public class StepDaoHibernate extends GenericDaoHibernate<Step,Long> implements StepDao {
    public StepDaoHibernate() {
        super(Step.class);
    }

    /**
     * {@inheritDoc}
     */
    public Step getStepByName(String stepname) {
        List steps = getSession().createCriteria(Step.class).add(Restrictions.eq("name", stepname)).list();
        if (steps.isEmpty()) {
            return null;
        } else {
            return (Step) steps.get(0);
        }
    }

}

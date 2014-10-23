package geodinamika.dao.hibernate;

import geodinamika.model.Action;
import geodinamika.model.steps.Step;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import geodinamika.dao.LookupDao;
import geodinamika.model.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.hibernate.Session;

/**
 * Hibernate implementation of LookupDao.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *      Modified by jgarcia: updated to hibernate 4
 */
@Repository
public class LookupDaoHibernate implements LookupDao {
    private Log log = LogFactory.getLog(LookupDaoHibernate.class);
    private final SessionFactory sessionFactory;

    /**
     * Initialize LookupDaoHibernate with Hibernate SessionFactory.
     * @param sessionFactory
     */
    @Autowired
    public LookupDaoHibernate(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Role.class).list();
    }

    @SuppressWarnings("unchecked")
    public List<Step> getSteps() {
        log.debug("Retrieving all step names...");
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Step.class).list();
    }

    @SuppressWarnings("unchecked")
    public List<Action> getActions() {
        log.debug("Retrieving all action names...");
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Action.class).list();
    }
}

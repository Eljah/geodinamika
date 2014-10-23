package geodinamika.model;

import geodinamika.model.steps.AcquireDataStep;
import geodinamika.model.steps.Step;
import geodinamika.model.steps.TransformDataStep;
import geodinamika.service.StepManager;
import org.hibernate.WrongClassException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Leon on 15.10.2014.
 */

@Entity
@Table(name = "action")
@Indexed
public class Action extends BaseObject implements Serializable {

    private static final long serialVersionUID = 3832626162173354412L;

    private Long id;
    private String description;                    // required

    private AcquireDataStep acquireStep;
    private List<TransformDataStep> transformSteps = new LinkedList<TransformDataStep>();
    private Integer version;

    public Action() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    public Long getId() {
        return id;
    }

    @Column(nullable = false, length = 200, unique = true)
    @Field
    public String getDescription() {
        return description;
    }




    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "action_step2",
            joinColumns = {@JoinColumn(name = "action_id")},
            inverseJoinColumns = @JoinColumn(name = "step_id")
    )
    public AcquireDataStep getAcquireStep() {
        return acquireStep;
    }

    ;
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "action_step",
            joinColumns = {@JoinColumn(name = "action_id")},
            inverseJoinColumns = @JoinColumn(name = "step_id")

    )
    public List<TransformDataStep> getTransformSteps() {
        return transformSteps;
    }


    @Version
    public Integer getVersion() {
        return version;
    }

    @Transient
    @Field
    public String getActionStepsDescription() {
        String toReturn = "";
        if (acquireStep != null) {
            toReturn += acquireStep.getName();
        }
        if (!transformSteps.isEmpty()) {
            toReturn += "->";
            Iterator<TransformDataStep> it = transformSteps.iterator();
            while (it.hasNext()) {
                Step u = it.next();
                toReturn += u.getName();
                if (it.hasNext()) {
                    toReturn += "->";
                }
            }
        }
        if (toReturn != "") {
            return toReturn;
        } else {
            return "No steps attached";
        }
    }
/*
    @Transient
    public List<LabelValue> getStepList() {
        List<LabelValue> actionSteps = new ArrayList<LabelValue>();

        if (this.transformSteps != null) {
            for (Step step : transformSteps) {
                // convert the user's roles to LabelValue Objects
                actionSteps.add(new LabelValue(step.getName(), step.getName()));
            }
        }

        return actionSteps;
    }
*/

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransformSteps(List<TransformDataStep> transformSteps) {
        this.transformSteps = transformSteps;
    }

    public void setAcquireStep(AcquireDataStep acquireStep) {
        this.acquireStep = acquireStep;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void addStep(Step step) {

        if (step instanceof AcquireDataStep) {
            this.acquireStep = (AcquireDataStep) step;

            log.debug("id of acquireStep just after addition is " + step.getId());

        } else if (step instanceof TransformDataStep && !transformSteps.contains(step)) {
            this.transformSteps.add((TransformDataStep) step);
            log.debug("id of transformStep just after addition is " + step.getId());

        }
        {
            if (this.acquireStep != null) {
                log.debug("id of acquireStep is " + this.acquireStep.getId());
            }
            ;
            if (!this.transformSteps.isEmpty()) {
                log.debug("id of transformStep is " + this.transformSteps.get(0).getId());
            }
        }
        ;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}

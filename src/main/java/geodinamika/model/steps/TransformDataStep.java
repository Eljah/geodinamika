package geodinamika.model.steps;

import geodinamika.model.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;

/**
 * Created by Leon on 15.10.2014.
 */

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="CANONICAL_NAME", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Transform Data Step")
public abstract class TransformDataStep extends Step {
    @Override
    public Data doStep(Object data) {
        return null;
    }


}

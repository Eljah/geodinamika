package geodinamika.model.steps;

import geodinamika.model.Data;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Acquire Data Zetlab Load File")
public class AcquireDataZetlabLoadFile extends AcquireDataStep {
    @Override
    public Data doStep(Object data) {
        return null;
    }
}

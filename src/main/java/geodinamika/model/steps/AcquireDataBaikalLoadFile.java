package geodinamika.model.steps;

import geodinamika.model.Data;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@DiscriminatorValue("Acquire Data Baikal Load File")
public class AcquireDataBaikalLoadFile extends AcquireDataStep {
    @Override
    public Data doStep(Object data) {
        return null;
    }
}

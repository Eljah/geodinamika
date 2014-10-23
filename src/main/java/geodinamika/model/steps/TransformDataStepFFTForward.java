package geodinamika.model.steps;

import geodinamika.model.Data;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("Transform Data Step FTT Forward")
public class TransformDataStepFFTForward extends TransformDataStep {
    @Override
    public Data doStep(Object data) {
        return null;
    }
}

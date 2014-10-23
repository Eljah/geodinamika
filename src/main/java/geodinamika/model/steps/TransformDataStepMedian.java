package geodinamika.model.steps;

import geodinamika.model.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Ilya Evlampiev on 20.10.2014.
 */

@Entity
@DiscriminatorValue("Transform Data Step Median")
public class TransformDataStepMedian extends TransformDataStep {
    @Override
    public Data doStep(Object data) {
        return null;
    }
}

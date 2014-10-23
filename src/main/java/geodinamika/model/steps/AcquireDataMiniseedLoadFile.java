package geodinamika.model.steps;

/**
 * Created by Ilya Evlampiev on 20.10.2014.
 */

import geodinamika.model.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Acquire Data Miniseed Load File")
public class AcquireDataMiniseedLoadFile extends AcquireDataStep {
    @Override
    public Data doStep(Object data) {
        return null;
    }
}

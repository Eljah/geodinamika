package geodinamika.model.steps;

import geodinamika.model.BaseObject;
import geodinamika.model.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Leon on 15.10.2014.
 */
@Entity
@Table(name = "step")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="CANONICAL_NAME", discriminatorType = DiscriminatorType.STRING, length = 50)
@Indexed
public abstract class Step<PK>  implements Serializable {
    private Long id;
    private String name;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    public Long getId() {
        return id;
    }

    @Column
    @Field
    public String getName() {
        return this.name;
    }

    @Column
    @Field
    public String getDescription() {
        return this.description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Data doStep(Object data) {
        return null;
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.name)
                .toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Step)) {
            return false;
        }

        final Step step = (Step) o;

        return !(name != null ? !name.equals(step.name) : step.name != null);

    }

}

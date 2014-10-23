package geodinamika.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Leon on 14.10.2014.
 */
@Entity
@Table(name = "task")
@Indexed
@XmlRootElement
public class Task extends BaseObject implements Serializable {


    private static final long serialVersionUID = 3832626162173359412L;

    private Long id;
    private String description;                    // required


    private Set<User> users = new HashSet<User>();
    private Integer version;

    private Action action;

    public Task() {

    }


    public Task(User user) {
        this.users.add(user);
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

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "task_user",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public Set<User> getUsers() {
        return users;
    }

    @OneToOne
    //@Field(index=Index.TOKENIZED)
    public Action getAction() {return action;}

    @Version
    public Integer getVersion() {
        return version;
    }

    @Transient
    @Field
    public String getUsersNames() {
        String toReturn="";
        Iterator<User> it = users.iterator();
        while(it.hasNext()) {
            User u = it.next();
            toReturn+=u.getUsername()+" ("+u.getFirstName()+" "+u.getLastName()+")";
            if (it.hasNext()){toReturn+=", ";}
        }
        return toReturn;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void addUser(User user) {
        getUsers().add(user);
    }


    @Override
    public String toString() {
        return null;
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

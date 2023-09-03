package model;

import com.google.gson.annotations.JsonAdapter;
import model.jsonAdapters.EventJsonAdapter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "events",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")})
    @JsonAdapter(EventJsonAdapter.class)
    private List<EventEntity> events;

    public UserEntity() {}

    public UserEntity(String name) {
        this.name = name;
        this.events = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(List<EventEntity> eventEntities) {
        this.events = eventEntities;
    }

    public void addEvent(EventEntity eventEntity) {
        this.events.add(eventEntity);
    }

    public void deleteEvent(int id) {
        events = events.stream().filter(e -> e.getId() != id).toList();
    }
}

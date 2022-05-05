package mx.nopaloverflow.vetapi.core.entities;


import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityPk;

    public Long getEntityPk() {
        return entityPk;
    }

    public void setEntityPk(Long entityPk) {
        this.entityPk = entityPk;
    }
}

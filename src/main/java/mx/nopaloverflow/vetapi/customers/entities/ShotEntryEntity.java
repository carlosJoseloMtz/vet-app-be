package mx.nopaloverflow.vetapi.customers.entities;

import mx.nopaloverflow.vetapi.core.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name = "shot_entries")
public class ShotEntryEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "pet")
    private PetEntity pet;
    @Column(name = "shot_date")
    private LocalDate shotDate;
    @Column(name = "next_shot_date")
    private LocalDate nextShotDate;
    @Column
    private String notes;
    // TODO: add shot type - shot type should have a definition of the: code, animal, shot-name, shot-description

    public PetEntity getPet() {
        return pet;
    }

    public void setPet(PetEntity pet) {
        this.pet = pet;
    }

    public LocalDate getShotDate() {
        return shotDate;
    }

    public void setShotDate(LocalDate shotDate) {
        this.shotDate = shotDate;
    }

    public LocalDate getNextShotDate() {
        return nextShotDate;
    }

    public void setNextShotDate(LocalDate nextShotDate) {
        this.nextShotDate = nextShotDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

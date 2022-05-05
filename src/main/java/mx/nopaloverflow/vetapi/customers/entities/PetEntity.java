package mx.nopaloverflow.vetapi.customers.entities;

import com.j256.ormlite.field.DatabaseField;
import mx.nopaloverflow.vetapi.core.entities.BaseEntity;
import mx.nopaloverflow.vetapi.core.utils.db.VetLocalDateTimeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name = "pets")
public class PetEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "pet_owner")
    private CustomerEntity petOwner;
    @Column
    private String name;
    @Column
    private String species;
    @Column
    private String breed;
    @Column
    private String sex;
    @Column(name = "birth_date")
    @DatabaseField(persisterClass = VetLocalDateTimeType.class, columnName = "birth_date")
    private LocalDate birthDate;
    @Column
    private Integer age;

    public CustomerEntity getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(CustomerEntity petOwner) {
        this.petOwner = petOwner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

package ch.fhnw.skyguide.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Field {
    private Integer id;
    private String name;
    private String label;
    private String placeholder;
    private Boolean isMandatory;

    private Set<FormType> fields;

    public Field() {

    }

    public Field(String name, String label, String placeholder, boolean isMandatory) {
        this.name = name;
        this.label =label;
        this.placeholder=placeholder;
        this.isMandatory = isMandatory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public Boolean getMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }

    @ManyToMany(mappedBy = "fields")
    @JsonIgnore
    public Set<FormType> getFields() {
        return fields;
    }

    public void setFields(Set<FormType> fields) {
        this.fields = fields;
    }
}

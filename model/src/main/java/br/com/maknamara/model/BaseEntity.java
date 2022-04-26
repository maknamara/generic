package br.com.maknamara.model;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class BaseEntity {
    @DatabaseField(generatedId = true)
    public Long id;
    @DatabaseField
    private Date registrationDate;

    public boolean isNew() {
        return id == null;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
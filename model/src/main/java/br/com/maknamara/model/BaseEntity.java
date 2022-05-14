package br.com.maknamara.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import br.com.maknamara.component.LocalDateTimeDeserializer;
import br.com.maknamara.component.LocalDateTimeSerializer;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BaseEntity {
    @DatabaseField(generatedId = true)
    public Long id;
    @DatabaseField
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private Date registrationDate;

    @JsonIgnore
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
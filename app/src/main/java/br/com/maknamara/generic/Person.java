package br.com.maknamara.generic;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.maknamara.model.BaseEntity;

@DatabaseTable(tableName = "person")
public class Person extends BaseEntity {
    @DatabaseField()
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

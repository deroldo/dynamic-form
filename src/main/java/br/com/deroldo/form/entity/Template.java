package br.com.deroldo.form.entity;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Document
public class Template {

    @Id
    private ObjectId id;

    @NotBlank
    private String title;

    @NotEmpty
    private List<TemplateField> fields;

    private List<Map<String, String>> data;

    public Template() {
    }

    public Template(final String title, final List<TemplateField> fields) {
        this.title = title;
        this.fields = fields;
    }

    public Template(final ObjectId id, final String title, final List<TemplateField> fields) {
        this.id = id;
        this.title = title;
        this.fields = fields;
    }

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<TemplateField> getFields() {
        return fields;
    }

    public List<Map<String, String>> getData() {
        return Optional.ofNullable(this.data).orElse(new ArrayList<>());
    }

    public void addData(final Map<String, String> params) {
        final List<Map<String, String>> data = this.getData();
        data.add(params);
        this.data = data;
    }
}

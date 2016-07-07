package br.com.deroldo.form.repository;

import br.com.deroldo.form.entity.Template;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends MongoRepository<Template, ObjectId> {
}

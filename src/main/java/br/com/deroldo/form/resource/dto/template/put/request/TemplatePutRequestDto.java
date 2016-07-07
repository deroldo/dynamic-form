package br.com.deroldo.form.resource.dto.template.put.request;

import br.com.deroldo.form.entity.FieldType;
import br.com.deroldo.form.entity.Radio;
import br.com.deroldo.form.entity.Template;
import br.com.deroldo.form.entity.TemplateField;
import br.com.deroldo.form.resource.dto.template.post.request.RadioPostRequestDto;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TemplatePutRequestDto {

    @NotBlank
    private String title;

    @NotEmpty
    private List<TemplateFieldPutRequestDto> fields;

    public String getTitle() {
        return title;
    }

    public List<TemplateFieldPutRequestDto> getFields() {
        return fields;
    }

    public Template createEntity(final String id) {
        final List<TemplateField> fields = this.getFields().stream()
                .map(fieldDto -> parseFieldDto(fieldDto))
                .collect(Collectors.toList());

        return new Template(new ObjectId(id), this.getTitle(), fields);
    }

    private TemplateField parseFieldDto(final TemplateFieldPutRequestDto fieldDto) {
        final List<RadioPutRequestDto> radiosDto = Optional.ofNullable(fieldDto.getRadios())
                .orElse(Collections.emptyList());

        final List<Radio> radios = radiosDto.stream()
                .map(radioDto -> parseRadioDto(radioDto))
                .collect(Collectors.toList());

        return new TemplateField(fieldDto.getLabel(), FieldType.get(fieldDto.getType()), fieldDto.isRequired(),
                fieldDto.isReadOnly(), fieldDto.getMaxLength(), radios,
                fieldDto.getPlaceholder(), fieldDto.getValue());
    }

    private Radio parseRadioDto(final RadioPutRequestDto radioDto) {
        return new Radio(radioDto.getLabel(), radioDto.getValue());
    }
}

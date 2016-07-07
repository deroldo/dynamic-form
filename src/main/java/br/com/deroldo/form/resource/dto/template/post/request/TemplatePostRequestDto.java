package br.com.deroldo.form.resource.dto.template.post.request;

import br.com.deroldo.form.entity.FieldType;
import br.com.deroldo.form.entity.Radio;
import br.com.deroldo.form.entity.Template;
import br.com.deroldo.form.entity.TemplateField;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TemplatePostRequestDto {

    @NotBlank
    private String title;

    @NotEmpty
    private List<TemplateFieldPostRequestDto> fields;

    public String getTitle() {
        return title;
    }

    public List<TemplateFieldPostRequestDto> getFields() {
        return fields;
    }

    public Template createEntity() {
        final List<TemplateField> fields = this.getFields().stream()
                .map(fieldDto -> parseFieldDto(fieldDto))
                .collect(Collectors.toList());

        return new Template(this.getTitle(), fields);
    }

    private TemplateField parseFieldDto(final TemplateFieldPostRequestDto fieldDto) {
        final List<RadioPostRequestDto> radiosDto = Optional.ofNullable(fieldDto.getRadios())
                .orElse(Collections.emptyList());

        final List<Radio> radios = radiosDto.stream()
                .map(radioDto -> parseRadioDto(radioDto))
                .collect(Collectors.toList());

        return new TemplateField(fieldDto.getLabel(), FieldType.get(fieldDto.getType()), fieldDto.isRequired(),
                fieldDto.isReadOnly(), fieldDto.getMaxLength(), radios,
                fieldDto.getPlaceholder(), fieldDto.getValue());
    }

    private Radio parseRadioDto(final RadioPostRequestDto radioDto) {
        return new Radio(radioDto.getLabel(), radioDto.getValue());
    }
}

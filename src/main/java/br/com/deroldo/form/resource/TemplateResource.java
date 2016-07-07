package br.com.deroldo.form.resource;

import br.com.deroldo.form.resource.dto.data.get.response.TemplateDataGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.id.response.TemplateGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.list.response.TemplateGetListResponseDto;
import br.com.deroldo.form.resource.dto.template.post.request.TemplatePostRequestDto;
import br.com.deroldo.form.resource.dto.template.post.response.TemplatePostResponseDto;
import br.com.deroldo.form.resource.dto.template.put.request.TemplatePutRequestDto;
import br.com.deroldo.form.resource.dto.template.put.response.TemplatePutResponseDto;
import br.com.deroldo.form.service.TemplateService;
import br.com.deroldo.form.validator.ContractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coletor/templates")
public class TemplateResource {

    @Autowired
    private TemplateService service;

    @Autowired
    private ContractValidator validator;

    @RequestMapping(method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<TemplatePostResponseDto> createTemplate(@RequestBody final TemplatePostRequestDto dto){
        this.validator.validate(dto);
        final TemplatePostResponseDto response = this.service.createTemplate(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<TemplatePutResponseDto> updateTemplate(@PathVariable("id") final String id,
                                                                 @RequestBody final TemplatePutRequestDto dto){
        this.validator.validate(dto);
        final TemplatePutResponseDto response = this.service.updateTemplate(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<List<TemplateGetListResponseDto>> listTemplate(){
        final List<TemplateGetListResponseDto> responses = this.service.listTemplate();
        return ResponseEntity.ok().body(responses);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<TemplateGetResponseDto> getTemplate(@PathVariable("id") final String id){
        final TemplateGetResponseDto response = this.service.getTemplate(id);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTemplate(@PathVariable("id") final String id){
        this.service.deleteTemplate(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "{id}/data", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<Void> createData(@PathVariable("id") final String id, @RequestBody Map<String, String> data){
        this.service.createData(id, data);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "{id}/data", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<TemplateDataGetResponseDto> createData(@PathVariable("id") final String id){
        final TemplateDataGetResponseDto response = this.service.getData(id);
        return ResponseEntity.ok().body(response);
    }

}

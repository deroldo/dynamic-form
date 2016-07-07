package br.com.deroldo.form.resource;

import br.com.deroldo.form.DynamicFormApplication;
import br.com.deroldo.form.entity.FieldType;
import br.com.deroldo.form.entity.Template;
import br.com.deroldo.form.exception.response.GenericError;
import br.com.deroldo.form.exception.response.ValidationError;
import br.com.deroldo.form.repository.TemplateRepository;
import br.com.deroldo.form.resource.dto.data.get.response.TemplateDataGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.id.response.RadioGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.id.response.TemplateFieldGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.id.response.TemplateGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.list.response.TemplateGetListResponseDto;
import br.com.deroldo.form.resource.dto.template.post.response.RadioPostResponseDto;
import br.com.deroldo.form.resource.dto.template.post.response.TemplateFieldPostResponseDto;
import br.com.deroldo.form.resource.dto.template.post.response.TemplatePostResponseDto;
import br.com.deroldo.form.resource.dto.template.put.response.RadioPutResponseDto;
import br.com.deroldo.form.resource.dto.template.put.response.TemplateFieldPutResponseDto;
import br.com.deroldo.form.resource.dto.template.put.response.TemplatePutResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DynamicFormApplication.class)
@WebAppConfiguration
public class TemplateResourceTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private TemplateRepository repository;

    private MockMvc mvc;

    @Before
    public void before(){
        this.repository.deleteAll();
        assertTrue(this.repository.findAll().isEmpty());
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void should_create_template() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostTemplate());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplatePostResponseDto response = this.objectMapper.readValue(json, TemplatePostResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.get_id());
        assertEquals("Nome do formulário", response.getTitle());
        assertNotNull(response.getFields());
        assertEquals(1, response.getFields().size());

        TemplateFieldPostResponseDto field = response.getFields().get(0);
        assertNotNull(field);
        assertEquals("masculino", field.getValue());
        assertEquals("Sexo", field.getLabel());
        assertNull(field.getPlaceholder());
        assertEquals(FieldType.RADIO.getTextType(), field.getType());
        assertNull(field.getMaxLength());

        assertNotNull(field.getRadios());
        assertEquals(2, field.getRadios().size());

        RadioPostResponseDto masculino = field.getRadios().get(0);
        assertNotNull(masculino);
        assertEquals("M", masculino.getLabel());
        assertEquals("masculino", masculino.getValue());

        RadioPostResponseDto feminino = field.getRadios().get(1);
        assertNotNull(feminino);
        assertEquals("F", feminino.getLabel());
        assertEquals("feminino", feminino.getValue());

        assertFalse(this.repository.findAll().isEmpty());
    }

    @Test
    public void should_retrun_bad_request_when_radio_value_not_present() throws Exception {
        testInvalidTemplate(getInvalidRadioPostTemplate(), "Radio type should have radios value", "Sexo");
    }

    @Test
    public void should_retrun_bad_request_when_read_only_dont_have_deafult_value() throws Exception {
        testInvalidTemplate(getInvalidReadOnlyPostTemplate(), "Read only field should have value", "System");
    }

    @Test
    public void should_retrun_bad_request_when_invalid_type_is_sent() throws Exception {
        testInvalidTemplate(getInvalidTypePostTemplate(), "Invalid field type", "Sexo");
    }

    @Test
    public void should_update_template() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostTemplate());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplatePostResponseDto post = this.objectMapper.readValue(json, TemplatePostResponseDto.class);

        requestBuilder = MockMvcRequestBuilders
                .put("/coletor/templates/" + post.get_id())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPutTemplate());

        resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));

        json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplatePutResponseDto response = this.objectMapper.readValue(json, TemplatePutResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.get_id());
        assertEquals("Alterando o titulo.", response.getTitle());
        assertNotNull(response.getFields());
        assertEquals(2, response.getFields().size());

        TemplateFieldPutResponseDto radio = response.getFields().get(0);
        assertNotNull(radio);
        assertEquals("masculino", radio.getValue());
        assertEquals("Sexo", radio.getLabel());
        assertNull(radio.getPlaceholder());
        assertEquals(FieldType.RADIO.getTextType(), radio.getType());
        assertNull(radio.getMaxLength());

        TemplateFieldPutResponseDto text = response.getFields().get(1);
        assertNotNull(text);
        assertNull(text.getValue());
        assertEquals("Nome", text.getLabel());
        assertNull(text.getPlaceholder());
        assertEquals(FieldType.TEXT.getTextType(), text.getType());
        assertNull(text.getMaxLength());

        assertNotNull(radio.getRadios());
        assertEquals(2, radio.getRadios().size());

        RadioPutResponseDto masculino = radio.getRadios().get(0);
        assertNotNull(masculino);
        assertEquals("M", masculino.getLabel());
        assertEquals("masculino", masculino.getValue());

        RadioPutResponseDto feminino = radio.getRadios().get(1);
        assertNotNull(feminino);
        assertEquals("F", feminino.getLabel());
        assertEquals("feminino", feminino.getValue());

        assertFalse(this.repository.findAll().isEmpty());
    }

    @Test
    public void should_list_templates() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostTemplate());

        this.mvc.perform(requestBuilder);

        requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostTemplate());

        this.mvc.perform(requestBuilder);

        requestBuilder = MockMvcRequestBuilders.get("/coletor/templates");

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        List<TemplateGetListResponseDto> responses = this.objectMapper.readValue(json, new TypeReference<List<TemplateGetListResponseDto>>(){});

        assertNotNull(responses);
        assertEquals(2, responses.size());

        for (TemplateGetListResponseDto response : responses) {
            assertNotNull(response.get_id());
            assertEquals("Nome do formulário", response.getTitle());
            assertEquals("0", response.getDataCount());
            assertNotNull(response.getFields());
            assertEquals(1, response.getFields().size());
            assertEquals("Sexo", response.getFields().get(0).getLabel());
        }

        assertFalse(this.repository.findAll().isEmpty());
    }

    @Test
    public void should_get_template() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostTemplate());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplatePostResponseDto post = this.objectMapper.readValue(json, TemplatePostResponseDto.class);

        requestBuilder = MockMvcRequestBuilders.get("/coletor/templates/" + post.get_id());

        resultActions = this.mvc.perform(requestBuilder);

        json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplateGetResponseDto response = this.objectMapper.readValue(json, TemplateGetResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.get_id());
        assertEquals("Nome do formulário", response.getTitle());
        assertNotNull(response.getFields());
        assertEquals(1, response.getFields().size());

        TemplateFieldGetResponseDto field = response.getFields().get(0);
        assertNotNull(field);
        assertEquals("masculino", field.getValue());
        assertEquals("Sexo", field.getLabel());
        assertNull(field.getPlaceholder());
        assertEquals(FieldType.RADIO.getTextType(), field.getType());
        assertNull(field.getMaxLength());

        assertNotNull(field.getRadios());
        assertEquals(2, field.getRadios().size());

        RadioGetResponseDto masculino = field.getRadios().get(0);
        assertNotNull(masculino);
        assertEquals("M", masculino.getLabel());
        assertEquals("masculino", masculino.getValue());

        RadioGetResponseDto feminino = field.getRadios().get(1);
        assertNotNull(feminino);
        assertEquals("F", feminino.getLabel());
        assertEquals("feminino", feminino.getValue());

        assertFalse(this.repository.findAll().isEmpty());
    }

    @Test
    public void should_delete_template() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostTemplate());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        assertFalse(this.repository.findAll().isEmpty());

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplatePostResponseDto post = this.objectMapper.readValue(json, TemplatePostResponseDto.class);

        requestBuilder = MockMvcRequestBuilders.delete("/coletor/templates/" + post.get_id());

        resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        assertTrue(this.repository.findAll().isEmpty());
    }

    @Test
    public void should_return_ok_when_delete_not_found_template() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/coletor/templates/577e5c387f88b7a9a91b3a17");

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        assertTrue(this.repository.findAll().isEmpty());
    }

    @Test
    public void should_create_data() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostTemplateData());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        assertFalse(this.repository.findAll().isEmpty());

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplatePostResponseDto post = this.objectMapper.readValue(json, TemplatePostResponseDto.class);

        requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates/" + post.get_id() + "/data")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostData());

        resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        final List<Template> templates = this.repository.findAll();
        assertFalse(templates.isEmpty());
        assertEquals(1, templates.size());

        Template template = templates.get(0);
        assertNotNull(template.getData());
        assertEquals(1, template.getData().size());

        Map<String, String> map = template.getData().get(0);

        assertTrue(map.containsKey("nome"));
        assertEquals("Guilherme", map.get("nome"));

        assertTrue(map.containsKey("telefone"));
        assertEquals("(11) 99999-9999", map.get("telefone"));

        assertTrue(map.containsKey("email"));
        assertEquals("email@gfg.com.br", map.get("email"));

        assertTrue(map.containsKey("apelido"));
        assertEquals("GFG", map.get("apelido"));
    }

    @Test
    public void should_return_not_found_data() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates/577e5c387f88b7a9a91b3a17/data")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostData());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        GenericError response = this.objectMapper.readValue(json, GenericError.class);

        assertNotNull(response);
        assertEquals(true, response.isError());
        assertEquals("Template não encontrado", response.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

        assertTrue(this.repository.findAll().isEmpty());
    }

    @Test
    public void should_get_data() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostTemplateData());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        assertFalse(this.repository.findAll().isEmpty());

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplatePostResponseDto post = this.objectMapper.readValue(json, TemplatePostResponseDto.class);

        requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates/" + post.get_id() + "/data")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostData());

        this.mvc.perform(requestBuilder);

        requestBuilder = MockMvcRequestBuilders.get("/coletor/templates/" + post.get_id() + "/data");

        resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplateDataGetResponseDto response = this.objectMapper.readValue(json, TemplateDataGetResponseDto.class);

        assertNotNull(response);

        assertNotNull(response.getFields());
        assertEquals(4, response.getFields().size());

        assertEquals("Nome", response.getFields().get(0).getLabel());
        assertEquals("Telefone", response.getFields().get(1).getLabel());
        assertEquals("Email", response.getFields().get(2).getLabel());
        assertEquals("Apelido", response.getFields().get(3).getLabel());

        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        Map<String, String> map = response.getData().get(0);

        assertTrue(map.containsKey("nome"));
        assertEquals("Guilherme", map.get("nome"));

        assertTrue(map.containsKey("telefone"));
        assertEquals("(11) 99999-9999", map.get("telefone"));

        assertTrue(map.containsKey("email"));
        assertEquals("email@gfg.com.br", map.get("email"));

        assertTrue(map.containsKey("apelido"));
        assertEquals("GFG", map.get("apelido"));
    }

    @Test
    public void should_return_bad_request_when_post_invalid_value_data() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getPostTemplateData());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        assertFalse(this.repository.findAll().isEmpty());

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        TemplatePostResponseDto post = this.objectMapper.readValue(json, TemplatePostResponseDto.class);

        requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates/" + post.get_id() + "/data")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getInvalidPostData());

        resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        ValidationError response = this.objectMapper.readValue(json, ValidationError.class);

        assertNotNull(response);

        assertEquals(1, response.getErrors().size());
        assertEquals("Field's value is invalid", response.getErrors().get(0).getMessage());
        assertEquals("email", response.getErrors().get(0).getCategory());
    }

    private void testInvalidTemplate(String content, String message, String category) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coletor/templates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content);

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        String json = new String(resultActions.andReturn().getResponse().getContentAsByteArray());

        ValidationError response = this.objectMapper.readValue(json, ValidationError.class);

        assertNotNull(response);

        assertEquals(1, response.getErrors().size());
        assertEquals(message, response.getErrors().get(0).getMessage());
        assertEquals(category, response.getErrors().get(0).getCategory());
    }

    public String getPostTemplate() {
        return "{\"title\":\"Nome do formulário\",\"fields\":[{\"label\":\"Sexo\",\"type\":\"radio\",\"required\":true,\"readOnly\":false,\"value\":\"masculino\",\"radios\":[{\"label\":\"M\",\"value\":\"masculino\"},{\"label\":\"F\",\"value\":\"feminino\"}]}]}";
    }

    public String getPostTemplateData() {
        return "{\"title\":\"Nome do formulário\",\"fields\":[{\"label\":\"Nome\",\"type\":\"text\",\"required\":true},{\"label\":\"Telefone\",\"type\":\"tel\",\"required\":true},{\"label\":\"Email\",\"type\":\"email\",\"required\":true},{\"label\":\"Apelido\",\"type\":\"text\"}]}";
    }

    public String getInvalidRadioPostTemplate(){
        return "{\"title\":\"Nome do formulário\",\"fields\":[{\"label\":\"Sexo\",\"type\":\"radio\",\"required\":true,\"readOnly\":false,\"value\":\"masculino\"}]}";
    }

    public String getInvalidTypePostTemplate(){
        return "{\"title\":\"Nome do formulário\",\"fields\":[{\"label\":\"Sexo\",\"type\":\"xpto\",\"required\":true,\"readOnly\":false,\"value\":\"masculino\"}]}";
    }

    public String getInvalidReadOnlyPostTemplate(){
        return "{\"title\":\"Nome do formulário\",\"fields\":[{\"label\":\"System\",\"type\":\"text\",\"readOnly\":true}]}";
    }

    public String getPutTemplate(){
        return "{\"title\":\"Alterando o titulo.\",\"fields\":[{\"label\":\"Sexo\",\"type\":\"radio\",\"required\":true,\"readOnly\":false,\"value\":\"masculino\",\"radios\":[{\"label\":\"M\",\"value\":\"masculino\"},{\"label\":\"F\",\"value\":\"feminino\"}]},{\"label\":\"Nome\",\"type\":\"text\",\"required\":true}]}";
    }

    public String getPostData(){
        return "{\"nome\":\"Guilherme\",\"telefone\":\"(11) 99999-9999\",\"email\":\"email@gfg.com.br\",\"apelido\":\"GFG\"}";
    }

    public String getInvalidPostData(){
        return "{\"nome\":\"Guilherme\",\"telefone\":\"(11) 99999-9999\",\"email\":\"email@gfg\",\"apelido\":\"GFG\"}";
    }
}
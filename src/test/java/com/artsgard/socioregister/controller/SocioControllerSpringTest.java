package com.artsgard.socioregister.controller;

import com.artsgard.socioregister.mock.SocioMock;
import com.artsgard.socioregister.DTO.SocioDTO;
import com.artsgard.socioregister.service.MapperService;
import com.artsgard.socioregister.serviceimpl.MapperServiceImpl;
import com.artsgard.socioregister.serviceimpl.SocioServiceImpl;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author WillemDragstra
 */

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(SocioController.class)
public class SocioControllerSpringTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SocioServiceImpl socioService;

    @InjectMocks
    SocioController socioController;

    @Autowired
    private JacksonTester<SocioDTO> jsonSocio;
    
    @Autowired
    private JacksonTester<List<SocioDTO>> jsonSocios;
    
    private SocioDTO socioMock;
    private List<SocioDTO> sociosMock;

    private final MapperService mapperService = new MapperServiceImpl();

    @BeforeEach
    public void setup() {
        socioMock = mapperService.mapSocioModelToSocioDTO(SocioMock.generateSocio());
        sociosMock = new ArrayList();
        SocioMock.generateSocios().forEach((sci) -> {
            sociosMock.add(mapperService.mapSocioModelToSocioDTO(sci));
        });
    }

    @Test
    public void testFindAllSocios() throws Exception {

        given(socioService.findAllSocios()).willReturn(sociosMock);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/socio"))
                .andExpect((content()
                .contentType(MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSocios.write(sociosMock).getJson()
        );
    }

    @Test
    public void testFindSocioById() throws Exception {
        given(socioService.findSocioById(1L)).willReturn(socioMock);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/socio/1"))
                .andExpect((content()
                .contentType(MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSocio.write(socioMock).getJson()
        );
    }

    @Test
    public void testFindSocioByUsername() throws Exception {
        given(socioService.findSocioByUsername("wd")).willReturn(socioMock);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/socio/username/wd"))
                .andExpect((content()
                .contentType(MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSocio.write(socioMock).getJson()
        );
    }

    @Test
    public void testSaveSocio() throws Exception {
        SocioDTO socio = new SocioDTO(1L, "js edited", "secret", "Johann Sebastian", "Bach", "jsbach@gmail.com", true);
        socio.setRegisterDate(new Timestamp(1479250540110L));
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/socio/")
                //.andExpect(MockMvcResultMatchers.status().isOk())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSocio.write(socio)
                .getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void testUpdatesocio() throws Exception {
        SocioDTO socio = new SocioDTO(1L, "js edited", "secret", "Johann Sebastian", "Bach", "jsbach@gmail.com", true);
        socio.setRegisterDate(new Timestamp(1479250540110L));
        given(socioService.updateSocio(socio, 1L)).willReturn(socio);
        
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/socio/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonSocio.write(socio)
                                .getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSocio.write(socio).getJson());
    }

    @Test
    public void testDeleteSocio() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/socio/{id}", "1"))
            .andExpect(status().isOk());
    }
    
     
    @Test
    public void testAddAssociatedSociobyIds() throws Exception {
          this.mockMvc.perform(MockMvcRequestBuilders.post("/socio/{socioId}/{associatedSocioId}", "1", "2"))
            .andExpect(status().isOk());
    }
}

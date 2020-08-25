package com.artsgard.socioregister.service;

import com.artsgard.socioregister.service.MapperService;
import com.artsgard.socioregister.DTO.SocioDTO;
import com.artsgard.socioregister.model.SocioModel;
import com.artsgard.socioregister.serviceimpl.MapperServiceImpl;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author WillemDragstra
 */
public class MapperServiceTest {

    private final MapperService mapperService = new MapperServiceImpl();
    private SocioDTO socioMockDTO;
    private SocioModel socioMockModel;

    @BeforeEach
    public void setup() {
        socioMockDTO = new SocioDTO(1L, "js", "Johann Sebastian", "Bach", "jsbach@gmail.com", true);
        socioMockModel = new SocioModel(1L, "js", "Johann Sebastian", "Bach", "jsbach@gmail.com", true);
    }

    @Test
    public void mapSocioDTOToSocioModelTest() {
        assertNotNull(socioMockModel);
        SocioDTO dto = mapperService.mapSocioModelToSocioDTO(socioMockModel);
        assertThat(dto, isA(SocioDTO.class));
        assertEquals(dto.getUsername(), socioMockDTO.getUsername());
    }
    
    @Test
    public void mapSocioModelToSocioDTOTest() {
        assertNotNull(socioMockDTO);
        SocioModel model = mapperService.mapSocioDTOToSocioModel(socioMockDTO);
        assertThat(model, isA(SocioModel.class));
        assertEquals(model.getEmail(), socioMockModel.getEmail());
    }
}

package com.artsgard.socioregister.serviceimpl;

import com.artsgard.socioregister.service.MapperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artsgard.socioregister.DTO.SocioDTO;
import com.artsgard.socioregister.model.SocioModel;
import com.artsgard.socioregister.service.SocioService;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author WillemDragstra
 */
@Service
public class MapperServiceImpl implements MapperService {
    
    org.slf4j.Logger logger;
    
    ModelMapper modelMapper = new ModelMapper();

    public MapperServiceImpl() {
        this.logger = LoggerFactory.getLogger(MapperServiceImpl.class);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * 
     * @param dto
     * @return SocioModel from SocioDTO or null
     */
    @Override
    public SocioModel mapSocioDTOToSocioModel(SocioDTO dto) {
        if (dto != null) {
            return modelMapper.map(dto, SocioModel.class);
        } else {
            return null;
        }
    }

    /**
     * 
     * @param ent
     * @return SocioDTO from SocioModel or null
     */
    @Override
    public SocioDTO mapSocioModelToSocioDTO(SocioModel ent) {
        if (ent != null) {
            return modelMapper.map(ent, SocioDTO.class);
        } else {
            return null;
        }
    }
}

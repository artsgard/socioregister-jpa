package com.artsgard.socioregister.service;

import com.artsgard.socioregister.DTO.SocioDTO;
import com.artsgard.socioregister.model.SocioModel;

public interface MapperService  {
   SocioModel mapSocioDTOToSocioModel(SocioDTO dto);
   SocioDTO mapSocioModelToSocioDTO(SocioModel ent);
}
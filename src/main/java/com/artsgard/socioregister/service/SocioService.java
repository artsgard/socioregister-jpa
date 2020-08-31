package com.artsgard.socioregister.service;

import com.artsgard.socioregister.DTO.SocioDTO;
import com.artsgard.socioregister.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 
 * @author WillemDragstra
 */
@Service
public interface SocioService  {
    List<SocioDTO> findAllSocios() throws ResourceNotFoundException;
    SocioDTO findSocioById(Long id) throws ResourceNotFoundException;
    SocioDTO findSocioByUsername(String username) throws ResourceNotFoundException;
    SocioDTO saveSocio(SocioDTO socioDTO);
    SocioDTO updateSocio(SocioDTO socioDTO, Long id) throws ResourceNotFoundException;
    void deleteSocioById(Long id);
    boolean isSocioActiveById(Long id) throws ResourceNotFoundException;
    boolean hasSocioById(Long id);
    void addAssociatedSocioBySocioIds(Long socioId, Long associatedSocioId) throws ResourceNotFoundException;
}
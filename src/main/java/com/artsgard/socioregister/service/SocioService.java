package com.artsgard.socioregister.service;

import com.artsgard.socioregister.DTO.SocioDTO;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 
 * @author WillemDragstra
 */
@Service
public interface SocioService  {
    List<SocioDTO> findAllSocios();
    SocioDTO findSocioById(Long id); 
    SocioDTO findSocioByUsername(String username); 
    SocioDTO saveSocio(SocioDTO socioDTO);
    SocioDTO updateSocio(SocioDTO socioDTO, Long id);
    void deleteSocioById(Long id);
    boolean isSocioActiveById(Long id);
    boolean hasSocioById(Long id);
    void addAssociatedSociobyIds(Long socioId, Long associatedSocioId);
}
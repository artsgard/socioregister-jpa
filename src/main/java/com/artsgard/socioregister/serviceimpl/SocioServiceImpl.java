package com.artsgard.socioregister.serviceimpl;

import com.artsgard.socioregister.service.MapperService;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artsgard.socioregister.DTO.SocioDTO;
import com.artsgard.socioregister.exception.ResourceNotFoundException;
import com.artsgard.socioregister.model.SocioModel;
import com.artsgard.socioregister.repository.SocioRepository;
import com.artsgard.socioregister.service.SocioService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author WillemDragstra
 */
@Service
public class SocioServiceImpl implements SocioService {

    org.slf4j.Logger logger;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private SocioRepository socioRepo;

    public SocioServiceImpl() { 
        logger = LoggerFactory.getLogger(SocioServiceImpl.class);
    }

   /**
    * 
    * @return list of all socios
    * @throws ResourceNotFoundException 
    */
    @Override
    public List<SocioDTO> findAllSocios() throws ResourceNotFoundException {
        List<SocioModel> socios = socioRepo.findAll();
        if (socios.isEmpty()) {
            logger.error("no Socios found!");
            throw new ResourceNotFoundException("no Socios found!");
        } else {
            List<SocioDTO> list = new ArrayList();
            socios.forEach((sci) -> {
                list.add(mapperService.mapSocioModelToSocioDTO(sci));
            });
            return list;
        }
    }

    @Override
    public SocioDTO findSocioById(Long id) throws ResourceNotFoundException {
        Optional<SocioModel> opSocio = socioRepo.findById(id);
        if (opSocio.isPresent()) {
            return mapperService.mapSocioModelToSocioDTO(opSocio.get());
        } else {
            logger.error("no Socio found with id: " + id);
            throw new ResourceNotFoundException("no Socio found with id: " + id);
        }
    }

    @Override
    public SocioDTO findSocioByUsername(String username) throws ResourceNotFoundException {
        Optional<SocioModel> opSocio = socioRepo.findByUsername(username);
        if (opSocio.isPresent()) {
            return mapperService.mapSocioModelToSocioDTO(opSocio.get());
        } else {
            logger.error("no Socio found with user name: " + username);
            throw new ResourceNotFoundException("no Socio found with user name: " + username);
        }
    }

    @Override
    public SocioDTO saveSocio(SocioDTO socioDTO) throws ResourceNotFoundException {
        SocioModel socio = mapperService.mapSocioDTOToSocioModel(socioDTO);
        socio.setRegisterDate(new Timestamp(System.currentTimeMillis()));
        return mapperService.mapSocioModelToSocioDTO(socioRepo.save(socio));
    }

    @Override
    public SocioDTO updateSocio(SocioDTO socioDTO, Long id) throws ResourceNotFoundException {
        Optional <SocioModel> optSocio = socioRepo.findById(id);
        
        if (optSocio.isPresent()) {
            SocioModel repoSocio = optSocio.get();
            if (socioDTO.getUsername() == null) {
                socioDTO.setUsername(repoSocio.getUsername());
            }

            if (socioDTO.getFirstName() == null) {
                socioDTO.setFirstName(repoSocio.getFirstName());
            }

            if (socioDTO.getLastName() == null) {
                socioDTO.setLastName(repoSocio.getLastName());
            }

            if (socioDTO.getEmail() == null) {
                socioDTO.setEmail(repoSocio.getEmail());
            }
            socioDTO.setRegisterDate(repoSocio.getRegisterDate());
            socioDTO.setId(id);
            SocioModel socio = mapperService.mapSocioDTOToSocioModel(socioDTO);
            return mapperService.mapSocioModelToSocioDTO(socioRepo.save(socio));

        } else {
            logger.error("no Socio found with id: " + id);
            throw new ResourceNotFoundException("no Socio found with id: " + id);
        }
    }

    @Override
    public void deleteSocioById(Long id) throws ResourceNotFoundException {
        Optional <SocioModel> optSocio = socioRepo.findById(id);
        
        if (optSocio.isPresent()) {
            socioRepo.deleteById(id);
        } else {
            logger.error("no Socio found with id: " + id);
            throw new ResourceNotFoundException("no Socio found with id: " + id);
        }
    }

    @Override
    public boolean hasSocioById(Long id) {
        return socioRepo.existsById(id);
    }
    
    @Override
    public boolean isSocioActiveById(Long id) {
        Optional<SocioModel> opSocio = socioRepo.findById(id);
        if (opSocio.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addAssociatedSociobyIds(Long socioId, Long associatedSocioId) throws ResourceNotFoundException {
        Optional<SocioModel> opt1 = socioRepo.findById(socioId);
        Optional<SocioModel> opt2 = socioRepo.findById(associatedSocioId);

        if (opt1.isPresent() && opt2.isPresent()) {
            socioRepo.addByIds(socioId, associatedSocioId);
        } else {
            logger.error("No Socio present with socio id:" + socioId + " and or associated socio id:  " + associatedSocioId);
            throw new ResourceNotFoundException("No Socio present with socio id:" + socioId + " and or associated socio id:  " + associatedSocioId);
        }
    }
}

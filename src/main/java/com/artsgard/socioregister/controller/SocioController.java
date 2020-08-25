package com.artsgard.socioregister.controller;

import com.artsgard.socioregister.service.MapperService;
import com.artsgard.socioregister.DTO.SocioDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.artsgard.socioregister.service.SocioService;

/**
 *
 * @author WillemDragstra
 */
@RestController
@RequestMapping("/socio")
public class SocioController {

    org.slf4j.Logger logger = LoggerFactory.getLogger(SocioController.class);

    @Autowired
    private SocioService socioService;

    @Autowired
    private MapperService mapperService;

    /**
     *
     * @return list of socios
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SocioDTO>> findAllSocios() {
        return new ResponseEntity<>(socioService.findAllSocios(), HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return single socio
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SocioDTO> findSocioById(@PathVariable Long id) {
        SocioDTO socio = socioService.findSocioById(id);
        if (socio != null) {
            return new ResponseEntity<>(socio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param username
     * @return single socio
     */
    @GetMapping(path = "/username/{username}", produces = "application/json")
    public ResponseEntity<SocioDTO> findSocioByUsername(@PathVariable String username) {
        SocioDTO socio = socioService.findSocioByUsername(username);
        if (socio != null) {
            return new ResponseEntity<>(socio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param socioDTO
     * @return status created/ save socio
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<SocioDTO> saveSocio(@Valid @RequestBody SocioDTO socioDTO) {
        SocioDTO socio = socioService.saveSocio(socioDTO);
        return new ResponseEntity<>(socio, HttpStatus.CREATED);
        //return ResponseEntity.status(HttpStatus.CREATED).body(socio);
    }

    /**
     *
     * @param socioDTO
     * @param id
     * @return status created/ update socio
     */
    @PutMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<SocioDTO> updateSocio(@Valid @RequestBody SocioDTO socioDTO, @PathVariable Long id) {
        SocioDTO socio = socioService.updateSocio(socioDTO, id);
        if (socio != null) {
            return new ResponseEntity<>(socio, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param id delete single socio
     * @return status
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SocioDTO> deleteSocioById(@PathVariable("id") Long id) {
        socioService.deleteSocioById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

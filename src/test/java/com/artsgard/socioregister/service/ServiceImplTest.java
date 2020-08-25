package com.artsgard.socioregister.service;

import com.artsgard.socioregister.model.SocioModel;
import com.artsgard.socioregister.repository.SocioRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Timestamp;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class ServiceImplTest {

    @Autowired
    private SocioRepository repo;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(repo).isNotNull();
    }

    @Test
    public void findAllSociosTest() {
        List<SocioModel> socios = repo.findAll();
        assertThat(socios).isNotEmpty();
        assertThat(socios).hasSize(3);
    }

    @Test
    public void findSocioByIdTest() {
        SocioModel sc = repo.getOne(1L);
        assertThat(sc).isNotNull();
    }

    @Test
    public void findSocioByUsernameTest() {
    List<SocioModel> socios = repo.findAll();
        Optional<SocioModel> optSocio = repo.findByUsername("js");
        assertThat(optSocio.get().getId()).isNotNull();
        assertThat(optSocio.get().getUsername()).isEqualTo("js");
    }

    @Test
    public void saveSocioTest() {
        SocioModel socio = new SocioModel(null, "username", "first name", "last name", "username@gmail.com", true);
        socio.setRegisterDate(new Timestamp(System.currentTimeMillis()));
        repo.save(socio);
        assertThat(socio.getId()).isNotNull();
        assertThat(socio.getUsername()).isEqualTo("username");
    }

    @Test
    public void updateSocioTest() {
        
        Optional<SocioModel> optSocio = repo.findByUsername("js");
        SocioModel updateSocio = optSocio.get();
        updateSocio.setUsername("js edited");
        updateSocio.setActive(false);
        SocioModel updatedSocioFromDB = repo.save(updateSocio);
        assertThat(optSocio.get()).isEqualTo(updatedSocioFromDB);
    }

    @Test
    public void deleteSocioByIdTest() {
        SocioModel socio = new SocioModel(null, "username to delete", "first name to delete", "last name to delete", "todelete@gmail.com", true);
        socio.setRegisterDate(new Timestamp(System.currentTimeMillis()));
        repo.save(socio);
        Long id = socio.getId();
        repo.deleteById(id);
        Optional<SocioModel> deletedSocio = repo.findById(id);
        assertThat(deletedSocio.isPresent()).isFalse();
    }

    @Test
    public void hasSocioByIdTest() {
       Optional<SocioModel> optSocio = repo.findByUsername("js");
       SocioModel socio = optSocio.get();
       assertThat(repo.existsById(socio.getId())).isTrue();
    }

    @Test
    public void isSocioActiveByIdTest() {
        Optional<SocioModel> optSocio = repo.findByUsername("js");
        SocioModel socio = optSocio.get();
        repo.save(socio);
        socio.setActive(Boolean.FALSE);
        assertThat(socio.getActive()).isFalse();
    }
}

package com.artsgard.socioregister.service;

import com.artsgard.socioregister.exception.ResourceNotFoundException;
import com.artsgard.socioregister.model.SocioModel;
import com.artsgard.socioregister.repository.SocioRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Timestamp;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
    public void findSocioByIdTest_not_found() {
        SocioModel sc = repo.getOne(700L);
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }

    @Test
    public void findSocioByUsernameTest() {
        Optional<SocioModel> optSocio = repo.findByUsername("js");
        assertThat(optSocio.get().getId()).isNotNull();
        assertThat(optSocio.get().getUsername()).isEqualTo("js");
    }
    
    @Test
    public void findSocioByUsernameTest_not_found() {
        Optional<SocioModel> optSocio = repo.findByUsername("xxxxxxxxx");
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }

    @Test
    public void saveSocioTest() {
        SocioModel socio = new SocioModel(null, "username", "secret", "first name", "last name", "username@gmail.com", true);
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
        SocioModel socio = new SocioModel(null, "username to delete", "secret to delete", "first name to delete", "last name to delete", "todelete@gmail.com", true);
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
    public void hasSocioByIdTest_not_found() {
        Optional<SocioModel> optSocio = repo.findByUsername("xxxxxxxxx");
        assertThat(optSocio.isPresent()).isFalse();
    }

    @Test
    public void isSocioActiveByIdTest() {
        Optional<SocioModel> optSocio = repo.findByUsername("js");
        SocioModel socio = optSocio.get();
        socio.setActive(Boolean.FALSE);
        repo.save(socio);
        assertThat(socio.getActive()).isFalse();
    }

    @Test
    public void addAssociatedSociobyIds() {
        Long id1 = new Long("1");
        Long id2 = new Long("2");
        Optional<SocioModel> opt1 = repo.findById(id1);
        Optional<SocioModel> opt2 = repo.findById(id2);

        assertThat(opt1.isPresent()).isTrue();
        assertThat(opt2.isPresent()).isTrue();
    }

    @Test
    public void addAssociatedSociobyIds_not_found() {
        Long id1 = new Long("1");
        Long id2 = new Long("700"); // not present
        repo.findById(id1);
        repo.findById(id2);
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }
}

package com.artsgard.socioregister.service;

import com.artsgard.socioregister.exception.ResourceNotFoundException;
import com.artsgard.socioregister.model.SocioModel;
import com.artsgard.socioregister.repository.SocioRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ServiceImplTest {

    @Autowired
    private SocioRepository repo;
    
    public static final Long NON_EXISTING_ID = 7000L;
    public static final String NON_EXISTING_USERNAME = "SDFSDFSFSDFSDF";
    
    private Long existingId;
    private Long existingSecondId;
    private String existingUsername;
    
    @BeforeEach
    public void setup() {
        repo.deleteAll();
        repo.findAll();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SocioModel socio1 = new SocioModel(null, "js", "secret", "Johann Sebastian", "Bach", "jsbach@gmail.com", true);
        socio1.setRegisterDate(now);
        SocioModel socio2 = new SocioModel(null, "rw", "secret", "Richard", "Wagner", "rwagner@gmail.com", true);
        socio2.setRegisterDate(now);
        SocioModel socio3 = new SocioModel(null, "bb", "secret", "Bela", "Bartok", "bbartok@gmail.com", true);
        socio3.setRegisterDate(now);
        List<SocioModel> socios = new ArrayList();
   
        socios.add(socio1);
        socios.add(socio2);
        socios.add(socio3);

        repo.saveAll(socios);
        existingId = socio1.getId();
        existingSecondId = socio2.getId();
        existingUsername = socio1.getUsername();
    }

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(repo).isNotNull();
    }

    @Test
    public void findAllSociosTest() {
        List<SocioModel> socios = repo.findAll();
        assertThat(socios).isNotEmpty();
        assertThat(socios).hasSize(3);
    }
    
    @Test
    public void findAllSociosTest_not_found() {
        repo.deleteAll();
        List<SocioModel> socios = repo.findAll();
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }

    @Test
    public void findSocioByIdTest() {
        SocioModel sc = repo.getOne(existingId);
        assertThat(sc).isNotNull();
    }
    
    @Test
    public void findSocioByIdTest_not_found() {
        SocioModel sc = repo.getOne(NON_EXISTING_ID);
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }

    @Test
    public void findSocioByUsernameTest() {
        Optional<SocioModel> optSocio = repo.findByUsername(existingUsername);
        assertThat(optSocio.get().getId()).isNotNull();
        assertThat(optSocio.get().getUsername()).isEqualTo(existingUsername);
    }
    
    @Test
    public void findSocioByUsernameTest_not_found() {
        Optional<SocioModel> optSocio = repo.findByUsername(NON_EXISTING_USERNAME);
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
        Optional<SocioModel> optSocio = repo.findById(existingId);
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
        Optional<SocioModel> optSocio = repo.findById(existingId);
        SocioModel socio = optSocio.get();
        assertThat(repo.existsById(socio.getId())).isTrue();
    }
    
    @Test
    public void hasSocioByIdTest_not_found() {
        Optional<SocioModel> optSocio = repo.findById(NON_EXISTING_ID);
        assertThat(optSocio.isPresent()).isFalse();
    }

    @Test
    public void isSocioActiveByIdTest() {
        Optional<SocioModel> optSocio = repo.findById(existingId);
        SocioModel socio = optSocio.get();
        socio.setActive(Boolean.FALSE);
        repo.save(socio);
        assertThat(socio.getActive()).isFalse();
    }

    @Test
    public void addAssociatedSociobyIds() {
        Optional<SocioModel> opt1 = repo.findById(existingId);
        Optional<SocioModel> opt2 = repo.findById(existingSecondId);
        repo.addAssociatedSocioBySocioIds(opt1.get().getId(), opt2.get().getId());
        opt1.get().getAssociatedSocios().add(opt1.get());
        assertThat(opt1.get().getAssociatedSocios().size()).isEqualTo(1);
    }

    @Test
    public void addAssociatedSociobyIds_not_found() {
        Optional<SocioModel> opt1 = repo.findById(existingId);
        Optional<SocioModel> opt2 = repo.findById(NON_EXISTING_ID);
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }
}

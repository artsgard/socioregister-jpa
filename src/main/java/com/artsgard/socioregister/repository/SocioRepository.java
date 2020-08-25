package com.artsgard.socioregister.repository;

import com.artsgard.socioregister.model.SocioModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<SocioModel, Long> {
    Optional<SocioModel> findByUsername(String username);
}

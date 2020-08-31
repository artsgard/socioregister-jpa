package com.artsgard.socioregister.repository;

import com.artsgard.socioregister.model.SocioModel;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<SocioModel, Long> {
    
     static final String INSERT_ASSOCIATED_SOCIO_BY_IDS = 
             "INSERT INTO socio_associated_socio (socio_id, associated_socio_id) VALUES (:socioId,:associatedSocioId)";
    
    Optional<SocioModel> findByUsername(String username);
    
    @Modifying
    @Transactional
    @Query(value = INSERT_ASSOCIATED_SOCIO_BY_IDS, nativeQuery = true)
    void addAssociatedSocioBySocioIds(@Param("socioId") Long socioId, @Param("associatedSocioId") Long associatedSocioId);
}

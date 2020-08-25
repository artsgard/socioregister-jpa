package com.artsgard.socioregister.DTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * @author WillemDragstra
 * 
 */
@Data
@NoArgsConstructor
public class SocioDTO implements Serializable {
    
    private Long id;
    
    @NotEmpty
    @NotNull
    @Size(min = 2, max = 20)
    private String username;
    
    private String firstName;
    
    private String lastName;
    
    @NotEmpty
    @NotNull
    @Email
    private String email;
    
    private Timestamp registerDate;
   
    @NotNull
    private Boolean active;
    
    private List<SocioDTO> associatedSocios;

    public SocioDTO(Long id, String username, String firstName, String lastName, String email, Boolean active) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.active = active;
    }
}

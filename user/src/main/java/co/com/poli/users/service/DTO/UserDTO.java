package co.com.poli.users.service.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String last_name;
}

package antifraud.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ChangeAccessRequest {
    @NotBlank
    String username;
    @NotBlank
    @Pattern(regexp = "LOCK|UNLOCK")
    String operation;
}

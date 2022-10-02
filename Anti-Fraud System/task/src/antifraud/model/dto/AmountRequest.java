package antifraud.model.dto;

import antifraud.validation.ValidIP;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.LuhnCheck;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class AmountRequest {
    @Positive
    @NotNull
    Long amount;
    @NotEmpty
    @Column(unique = true)
    @ValidIP
    String ip;
    @NotEmpty
    @Column(unique = true)
    @LuhnCheck
    String number;
}

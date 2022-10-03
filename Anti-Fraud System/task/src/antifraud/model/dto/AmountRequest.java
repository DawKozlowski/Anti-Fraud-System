package antifraud.model.dto;

import antifraud.model.Region;
import antifraud.validation.ValidIP;
import antifraud.validation.ValidRegion;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.LuhnCheck;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "amount_request")
public class AmountRequest {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    @Positive
    @NotNull
    Long amount;
    @NotEmpty
    @ValidIP
    String ip;
    @NotEmpty
    @LuhnCheck
    String number;
    @ValidRegion(enumClass = Region.class)
    String region;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    LocalDateTime date;
}

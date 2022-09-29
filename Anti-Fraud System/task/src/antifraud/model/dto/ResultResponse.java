package antifraud.model.dto;

import antifraud.model.Result;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ResultResponse {
    @NotNull
    Result result;
}

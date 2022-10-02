package antifraud.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "^([0-1]?\\d?\\d|2[0-4]\\d|25[0-5])(\\.([0-1]?\\d?\\d|2[0-4]\\d|25[0-5])){3}$")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
@ReportAsSingleViolation
public @interface ValidIP {
    String message() default "must be ipv4";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

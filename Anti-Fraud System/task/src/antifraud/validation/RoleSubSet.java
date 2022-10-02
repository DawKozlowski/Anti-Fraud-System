package antifraud.validation;


import antifraud.model.Role;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = RoleSubSetValidator.class)
public @interface RoleSubSet {

        Role[] anyOf();
        String message() default "must be any of {anyOf}";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
}

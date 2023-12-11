package univtln.hafsaoui.rouge.entities.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LegalCountryValidator.class)
public @interface LegalCountry {
    String message() default "Le nom n'est pas valide";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

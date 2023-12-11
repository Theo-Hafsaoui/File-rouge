package univtln.hafsaoui.rouge.entities.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LegalCountryValidator implements ConstraintValidator<LegalCountry, String> {
    private Set<String> validNames;

    @Override
    public void initialize(LegalCountry constraintAnnotation) {
        validNames = new HashSet<>(Arrays.asList("North korea,",
                "cuba","Iran","Syria"));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !validNames.contains(value);
    }
}

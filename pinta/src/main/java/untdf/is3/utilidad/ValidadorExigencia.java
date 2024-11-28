package untdf.is3.utilidad;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorExigencia implements ConstraintValidator<ValidarExigencia, Character>{

    @Override
    public void initialize(ValidarExigencia constraintAnnotation) {
    }

    @Override
    public boolean isValid(Character exigencia, ConstraintValidatorContext context) {
        return (exigencia == null || exigencia == 'R' || exigencia == 'O');
    }

}

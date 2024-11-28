package untdf.is3.utilidad;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidadorExigencia.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidarExigencia {

    String message() default "exigencia debe ser nulo, 'R' o 'O'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

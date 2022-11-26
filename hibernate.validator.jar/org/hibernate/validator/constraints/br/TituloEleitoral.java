package org.hibernate.validator.constraints.br;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import org.hibernate.validator.constraints.Mod11Check;

@Pattern(
   regexp = "[0-9]{12}"
)
@Mod11Check.List({@Mod11Check(
   threshold = 9,
   endIndex = 7,
   checkDigitIndex = 10
), @Mod11Check(
   threshold = 9,
   startIndex = 8,
   endIndex = 10,
   checkDigitIndex = 11
)})
@ReportAsSingleViolation
@Documented
@Constraint(
   validatedBy = {}
)
@SupportedValidationTarget({ValidationTarget.ANNOTATED_ELEMENT})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface TituloEleitoral {
   String message() default "{org.hibernate.validator.constraints.br.TituloEleitoral.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface List {
      TituloEleitoral[] value();
   }
}

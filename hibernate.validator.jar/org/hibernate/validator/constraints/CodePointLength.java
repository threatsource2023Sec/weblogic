package org.hibernate.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import javax.validation.Constraint;

@Documented
@Constraint(
   validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface CodePointLength {
   int min() default 0;

   int max() default Integer.MAX_VALUE;

   NormalizationStrategy normalizationStrategy() default CodePointLength.NormalizationStrategy.NONE;

   String message() default "{org.hibernate.validator.constraints.CodePointLength.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   public static enum NormalizationStrategy {
      NONE((Normalizer.Form)null),
      NFD(Form.NFD),
      NFC(Form.NFC),
      NFKD(Form.NFKD),
      NFKC(Form.NFKC);

      private final Normalizer.Form form;

      private NormalizationStrategy(Normalizer.Form form) {
         this.form = form;
      }

      public CharSequence normalize(CharSequence value) {
         return (CharSequence)(this.form != null && value != null && value.length() != 0 ? Normalizer.normalize(value, this.form) : value);
      }
   }

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface List {
      CodePointLength[] value();
   }
}

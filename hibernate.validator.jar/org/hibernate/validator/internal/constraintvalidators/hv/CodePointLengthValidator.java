package org.hibernate.validator.internal.constraintvalidators.hv;

import java.lang.invoke.MethodHandles;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class CodePointLengthValidator implements ConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private int min;
   private int max;
   private CodePointLength.NormalizationStrategy normalizationStrategy;

   public void initialize(CodePointLength parameters) {
      this.min = parameters.min();
      this.max = parameters.max();
      this.normalizationStrategy = parameters.normalizationStrategy();
      this.validateParameters();
   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
      if (value == null) {
         return true;
      } else {
         String stringValue = this.normalizationStrategy.normalize(value).toString();
         int length = stringValue.codePointCount(0, stringValue.length());
         return length >= this.min && length <= this.max;
      }
   }

   private void validateParameters() {
      if (this.min < 0) {
         throw LOG.getMinCannotBeNegativeException();
      } else if (this.max < 0) {
         throw LOG.getMaxCannotBeNegativeException();
      } else if (this.max < this.min) {
         throw LOG.getLengthCannotBeNegativeException();
      }
   }
}

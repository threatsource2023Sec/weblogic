package org.hibernate.validator.internal.constraintvalidators.hv;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.constraints.Mod11Check;
import org.hibernate.validator.internal.util.ModUtil;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class Mod11CheckValidator extends ModCheckBase implements ConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private boolean reverseOrder;
   private char treatCheck10As;
   private char treatCheck11As;
   private int threshold;
   private int[] customWeights;

   public void initialize(Mod11Check constraintAnnotation) {
      this.initialize(constraintAnnotation.startIndex(), constraintAnnotation.endIndex(), constraintAnnotation.checkDigitIndex(), constraintAnnotation.ignoreNonDigitCharacters(), constraintAnnotation.threshold(), constraintAnnotation.treatCheck10As(), constraintAnnotation.treatCheck11As(), constraintAnnotation.processingDirection());
   }

   public void initialize(int startIndex, int endIndex, int checkDigitIndex, boolean ignoreNonDigitCharacters, int threshold, char treatCheck10As, char treatCheck11As, Mod11Check.ProcessingDirection direction, int... customWeights) {
      super.initialize(startIndex, endIndex, checkDigitIndex, ignoreNonDigitCharacters);
      this.threshold = threshold;
      this.reverseOrder = direction == Mod11Check.ProcessingDirection.LEFT_TO_RIGHT;
      this.treatCheck10As = treatCheck10As;
      this.treatCheck11As = treatCheck11As;
      this.customWeights = customWeights;
      if (!Character.isLetterOrDigit(this.treatCheck10As)) {
         throw LOG.getTreatCheckAsIsNotADigitNorALetterException(this.treatCheck10As);
      } else if (!Character.isLetterOrDigit(this.treatCheck11As)) {
         throw LOG.getTreatCheckAsIsNotADigitNorALetterException(this.treatCheck11As);
      }
   }

   public boolean isCheckDigitValid(List digits, char checkDigit) {
      if (this.reverseOrder) {
         Collections.reverse(digits);
      }

      int modResult = ModUtil.calculateModXCheckWithWeights(digits, 11, this.threshold, this.customWeights);
      switch (modResult) {
         case 10:
            return checkDigit == this.treatCheck10As;
         case 11:
            return checkDigit == this.treatCheck11As;
         default:
            return Character.isDigit(checkDigit) && modResult == this.extractDigit(checkDigit);
      }
   }
}

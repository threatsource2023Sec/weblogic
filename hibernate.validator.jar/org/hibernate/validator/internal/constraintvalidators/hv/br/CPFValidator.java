package org.hibernate.validator.internal.constraintvalidators.hv.br;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.Mod11Check;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.internal.constraintvalidators.hv.Mod11CheckValidator;

public class CPFValidator implements ConstraintValidator {
   private static final Pattern DIGITS_ONLY = Pattern.compile("\\d+");
   private static final Pattern SINGLE_DASH_SEPARATOR = Pattern.compile("\\d+-\\d\\d");
   private final Mod11CheckValidator withSeparatorMod11Validator1 = new Mod11CheckValidator();
   private final Mod11CheckValidator withSeparatorMod11Validator2 = new Mod11CheckValidator();
   private final Mod11CheckValidator withDashOnlySeparatorMod11Validator1 = new Mod11CheckValidator();
   private final Mod11CheckValidator withDashOnlySeparatorMod11Validator2 = new Mod11CheckValidator();
   private final Mod11CheckValidator withoutSeparatorMod11Validator1 = new Mod11CheckValidator();
   private final Mod11CheckValidator withoutSeparatorMod11Validator2 = new Mod11CheckValidator();

   public void initialize(CPF constraintAnnotation) {
      this.withSeparatorMod11Validator1.initialize(0, 10, 12, true, Integer.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
      this.withSeparatorMod11Validator2.initialize(0, 12, 13, true, Integer.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
      this.withDashOnlySeparatorMod11Validator1.initialize(0, 8, 10, true, Integer.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
      this.withDashOnlySeparatorMod11Validator2.initialize(0, 10, 11, true, Integer.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
      this.withoutSeparatorMod11Validator1.initialize(0, 8, 9, true, Integer.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
      this.withoutSeparatorMod11Validator2.initialize(0, 9, 10, true, Integer.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else if (DIGITS_ONLY.matcher(value).matches()) {
         return this.withoutSeparatorMod11Validator1.isValid(value, context) && this.withoutSeparatorMod11Validator2.isValid(value, context);
      } else if (SINGLE_DASH_SEPARATOR.matcher(value).matches()) {
         return this.withDashOnlySeparatorMod11Validator1.isValid(value, context) && this.withDashOnlySeparatorMod11Validator2.isValid(value, context);
      } else {
         return this.withSeparatorMod11Validator1.isValid(value, context) && this.withSeparatorMod11Validator2.isValid(value, context);
      }
   }
}

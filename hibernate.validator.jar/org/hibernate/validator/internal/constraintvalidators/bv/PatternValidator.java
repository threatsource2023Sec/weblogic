package org.hibernate.validator.internal.constraintvalidators.bv;

import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.internal.engine.messageinterpolation.util.InterpolationHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class PatternValidator implements ConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private Pattern pattern;
   private String escapedRegexp;

   public void initialize(javax.validation.constraints.Pattern parameters) {
      javax.validation.constraints.Pattern.Flag[] flags = parameters.flags();
      int intFlag = 0;
      javax.validation.constraints.Pattern.Flag[] var4 = flags;
      int var5 = flags.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         javax.validation.constraints.Pattern.Flag flag = var4[var6];
         intFlag |= flag.getValue();
      }

      try {
         this.pattern = Pattern.compile(parameters.regexp(), intFlag);
      } catch (PatternSyntaxException var8) {
         throw LOG.getInvalidRegularExpressionException(var8);
      }

      this.escapedRegexp = InterpolationHelper.escapeMessageParameter(parameters.regexp());
   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
      if (value == null) {
         return true;
      } else {
         if (constraintValidatorContext instanceof HibernateConstraintValidatorContext) {
            ((HibernateConstraintValidatorContext)constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class)).addMessageParameter("regexp", this.escapedRegexp);
         }

         Matcher m = this.pattern.matcher(value);
         return m.matches();
      }
   }
}

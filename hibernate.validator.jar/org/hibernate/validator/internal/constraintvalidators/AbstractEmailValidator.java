package org.hibernate.validator.internal.constraintvalidators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.util.DomainNameUtil;

public class AbstractEmailValidator implements ConstraintValidator {
   private static final int MAX_LOCAL_PART_LENGTH = 64;
   private static final String LOCAL_PART_ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~\u0080-\uffff-]";
   private static final String LOCAL_PART_INSIDE_QUOTES_ATOM = "([a-z0-9!#$%&'*.(),<>\\[\\]:;  @+/=?^_`{|}~\u0080-\uffff-]|\\\\\\\\|\\\\\\\")";
   private static final Pattern LOCAL_PART_PATTERN = Pattern.compile("([a-z0-9!#$%&'*+/=?^_`{|}~\u0080-\uffff-]+|\"([a-z0-9!#$%&'*.(),<>\\[\\]:;  @+/=?^_`{|}~\u0080-\uffff-]|\\\\\\\\|\\\\\\\")+\")(\\.([a-z0-9!#$%&'*+/=?^_`{|}~\u0080-\uffff-]+|\"([a-z0-9!#$%&'*.(),<>\\[\\]:;  @+/=?^_`{|}~\u0080-\uffff-]|\\\\\\\\|\\\\\\\")+\"))*", 2);

   public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
      if (value != null && value.length() != 0) {
         String stringValue = value.toString();
         int splitPosition = stringValue.lastIndexOf(64);
         if (splitPosition < 0) {
            return false;
         } else {
            String localPart = stringValue.substring(0, splitPosition);
            String domainPart = stringValue.substring(splitPosition + 1);
            return !this.isValidEmailLocalPart(localPart) ? false : DomainNameUtil.isValidEmailDomainAddress(domainPart);
         }
      } else {
         return true;
      }
   }

   private boolean isValidEmailLocalPart(String localPart) {
      if (localPart.length() > 64) {
         return false;
      } else {
         Matcher matcher = LOCAL_PART_PATTERN.matcher(localPart);
         return matcher.matches();
      }
   }
}

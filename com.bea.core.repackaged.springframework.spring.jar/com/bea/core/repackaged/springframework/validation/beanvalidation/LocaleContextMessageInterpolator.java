package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.springframework.context.i18n.LocaleContextHolder;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Locale;
import javax.validation.MessageInterpolator;

public class LocaleContextMessageInterpolator implements MessageInterpolator {
   private final MessageInterpolator targetInterpolator;

   public LocaleContextMessageInterpolator(MessageInterpolator targetInterpolator) {
      Assert.notNull(targetInterpolator, (String)"Target MessageInterpolator must not be null");
      this.targetInterpolator = targetInterpolator;
   }

   public String interpolate(String message, MessageInterpolator.Context context) {
      return this.targetInterpolator.interpolate(message, context, LocaleContextHolder.getLocale());
   }

   public String interpolate(String message, MessageInterpolator.Context context, Locale locale) {
      return this.targetInterpolator.interpolate(message, context, locale);
   }
}

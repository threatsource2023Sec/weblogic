package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.springframework.context.MessageSource;
import com.bea.core.repackaged.springframework.context.support.MessageSourceResourceBundle;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Locale;
import java.util.ResourceBundle;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public class MessageSourceResourceBundleLocator implements ResourceBundleLocator {
   private final MessageSource messageSource;

   public MessageSourceResourceBundleLocator(MessageSource messageSource) {
      Assert.notNull(messageSource, (String)"MessageSource must not be null");
      this.messageSource = messageSource;
   }

   public ResourceBundle getResourceBundle(Locale locale) {
      return new MessageSourceResourceBundle(this.messageSource, locale);
   }
}

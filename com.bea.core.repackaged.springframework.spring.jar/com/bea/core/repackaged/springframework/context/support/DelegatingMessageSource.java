package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.context.HierarchicalMessageSource;
import com.bea.core.repackaged.springframework.context.MessageSource;
import com.bea.core.repackaged.springframework.context.MessageSourceResolvable;
import com.bea.core.repackaged.springframework.context.NoSuchMessageException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Locale;

public class DelegatingMessageSource extends MessageSourceSupport implements HierarchicalMessageSource {
   @Nullable
   private MessageSource parentMessageSource;

   public void setParentMessageSource(@Nullable MessageSource parent) {
      this.parentMessageSource = parent;
   }

   @Nullable
   public MessageSource getParentMessageSource() {
      return this.parentMessageSource;
   }

   @Nullable
   public String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale) {
      if (this.parentMessageSource != null) {
         return this.parentMessageSource.getMessage(code, args, defaultMessage, locale);
      } else {
         return defaultMessage != null ? this.renderDefaultMessage(defaultMessage, args, locale) : null;
      }
   }

   public String getMessage(String code, @Nullable Object[] args, Locale locale) throws NoSuchMessageException {
      if (this.parentMessageSource != null) {
         return this.parentMessageSource.getMessage(code, args, locale);
      } else {
         throw new NoSuchMessageException(code, locale);
      }
   }

   public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
      if (this.parentMessageSource != null) {
         return this.parentMessageSource.getMessage(resolvable, locale);
      } else if (resolvable.getDefaultMessage() != null) {
         return this.renderDefaultMessage(resolvable.getDefaultMessage(), resolvable.getArguments(), locale);
      } else {
         String[] codes = resolvable.getCodes();
         String code = codes != null && codes.length > 0 ? codes[0] : "";
         throw new NoSuchMessageException(code, locale);
      }
   }

   public String toString() {
      return this.parentMessageSource != null ? this.parentMessageSource.toString() : "Empty MessageSource";
   }
}

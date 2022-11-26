package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.context.MessageSource;
import com.bea.core.repackaged.springframework.context.NoSuchMessageException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageSourceResourceBundle extends ResourceBundle {
   private final MessageSource messageSource;
   private final Locale locale;

   public MessageSourceResourceBundle(MessageSource source, Locale locale) {
      Assert.notNull(source, (String)"MessageSource must not be null");
      this.messageSource = source;
      this.locale = locale;
   }

   public MessageSourceResourceBundle(MessageSource source, Locale locale, ResourceBundle parent) {
      this(source, locale);
      this.setParent(parent);
   }

   @Nullable
   protected Object handleGetObject(String key) {
      try {
         return this.messageSource.getMessage(key, (Object[])null, this.locale);
      } catch (NoSuchMessageException var3) {
         return null;
      }
   }

   public boolean containsKey(String key) {
      try {
         this.messageSource.getMessage(key, (Object[])null, this.locale);
         return true;
      } catch (NoSuchMessageException var3) {
         return false;
      }
   }

   public Enumeration getKeys() {
      throw new UnsupportedOperationException("MessageSourceResourceBundle does not support enumerating its keys");
   }

   public Locale getLocale() {
      return this.locale;
   }
}

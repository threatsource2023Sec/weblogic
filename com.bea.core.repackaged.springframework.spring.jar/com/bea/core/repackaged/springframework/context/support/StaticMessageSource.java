package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StaticMessageSource extends AbstractMessageSource {
   private final Map messages = new HashMap();
   private final Map cachedMessageFormats = new HashMap();

   @Nullable
   protected String resolveCodeWithoutArguments(String code, Locale locale) {
      return (String)this.messages.get(code + '_' + locale.toString());
   }

   @Nullable
   protected MessageFormat resolveCode(String code, Locale locale) {
      String key = code + '_' + locale.toString();
      String msg = (String)this.messages.get(key);
      if (msg == null) {
         return null;
      } else {
         synchronized(this.cachedMessageFormats) {
            MessageFormat messageFormat = (MessageFormat)this.cachedMessageFormats.get(key);
            if (messageFormat == null) {
               messageFormat = this.createMessageFormat(msg, locale);
               this.cachedMessageFormats.put(key, messageFormat);
            }

            return messageFormat;
         }
      }
   }

   public void addMessage(String code, Locale locale, String msg) {
      Assert.notNull(code, (String)"Code must not be null");
      Assert.notNull(locale, (String)"Locale must not be null");
      Assert.notNull(msg, (String)"Message must not be null");
      this.messages.put(code + '_' + locale.toString(), msg);
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Added message [" + msg + "] for code [" + code + "] and Locale [" + locale + "]");
      }

   }

   public void addMessages(Map messages, Locale locale) {
      Assert.notNull(messages, (String)"Messages Map must not be null");
      messages.forEach((code, msg) -> {
         this.addMessage(code, locale, msg);
      });
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.messages;
   }
}

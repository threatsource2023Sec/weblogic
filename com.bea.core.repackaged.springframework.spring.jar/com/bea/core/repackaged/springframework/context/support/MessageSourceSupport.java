package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class MessageSourceSupport {
   private static final MessageFormat INVALID_MESSAGE_FORMAT = new MessageFormat("");
   protected final Log logger = LogFactory.getLog(this.getClass());
   private boolean alwaysUseMessageFormat = false;
   private final Map messageFormatsPerMessage = new HashMap();

   public void setAlwaysUseMessageFormat(boolean alwaysUseMessageFormat) {
      this.alwaysUseMessageFormat = alwaysUseMessageFormat;
   }

   protected boolean isAlwaysUseMessageFormat() {
      return this.alwaysUseMessageFormat;
   }

   protected String renderDefaultMessage(String defaultMessage, @Nullable Object[] args, Locale locale) {
      return this.formatMessage(defaultMessage, args, locale);
   }

   protected String formatMessage(String msg, @Nullable Object[] args, Locale locale) {
      if (!this.isAlwaysUseMessageFormat() && ObjectUtils.isEmpty(args)) {
         return msg;
      } else {
         MessageFormat messageFormat = null;
         synchronized(this.messageFormatsPerMessage) {
            Map messageFormatsPerLocale = (Map)this.messageFormatsPerMessage.get(msg);
            if (messageFormatsPerLocale != null) {
               messageFormat = (MessageFormat)((Map)messageFormatsPerLocale).get(locale);
            } else {
               messageFormatsPerLocale = new HashMap();
               this.messageFormatsPerMessage.put(msg, messageFormatsPerLocale);
            }

            if (messageFormat == null) {
               try {
                  messageFormat = this.createMessageFormat(msg, locale);
               } catch (IllegalArgumentException var11) {
                  if (this.isAlwaysUseMessageFormat()) {
                     throw var11;
                  }

                  messageFormat = INVALID_MESSAGE_FORMAT;
               }

               ((Map)messageFormatsPerLocale).put(locale, messageFormat);
            }
         }

         if (messageFormat == INVALID_MESSAGE_FORMAT) {
            return msg;
         } else {
            synchronized(messageFormat) {
               return messageFormat.format(this.resolveArguments(args, locale));
            }
         }
      }
   }

   protected MessageFormat createMessageFormat(String msg, Locale locale) {
      return new MessageFormat(msg, locale);
   }

   protected Object[] resolveArguments(@Nullable Object[] args, Locale locale) {
      return args != null ? args : new Object[0];
   }
}

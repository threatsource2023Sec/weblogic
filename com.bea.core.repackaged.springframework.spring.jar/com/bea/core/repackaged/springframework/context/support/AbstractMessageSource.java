package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.context.HierarchicalMessageSource;
import com.bea.core.repackaged.springframework.context.MessageSource;
import com.bea.core.repackaged.springframework.context.MessageSourceResolvable;
import com.bea.core.repackaged.springframework.context.NoSuchMessageException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public abstract class AbstractMessageSource extends MessageSourceSupport implements HierarchicalMessageSource {
   @Nullable
   private MessageSource parentMessageSource;
   @Nullable
   private Properties commonMessages;
   private boolean useCodeAsDefaultMessage = false;

   public void setParentMessageSource(@Nullable MessageSource parent) {
      this.parentMessageSource = parent;
   }

   @Nullable
   public MessageSource getParentMessageSource() {
      return this.parentMessageSource;
   }

   public void setCommonMessages(@Nullable Properties commonMessages) {
      this.commonMessages = commonMessages;
   }

   @Nullable
   protected Properties getCommonMessages() {
      return this.commonMessages;
   }

   public void setUseCodeAsDefaultMessage(boolean useCodeAsDefaultMessage) {
      this.useCodeAsDefaultMessage = useCodeAsDefaultMessage;
   }

   protected boolean isUseCodeAsDefaultMessage() {
      return this.useCodeAsDefaultMessage;
   }

   public final String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale) {
      String msg = this.getMessageInternal(code, args, locale);
      if (msg != null) {
         return msg;
      } else {
         return defaultMessage == null ? this.getDefaultMessage(code) : this.renderDefaultMessage(defaultMessage, args, locale);
      }
   }

   public final String getMessage(String code, @Nullable Object[] args, Locale locale) throws NoSuchMessageException {
      String msg = this.getMessageInternal(code, args, locale);
      if (msg != null) {
         return msg;
      } else {
         String fallback = this.getDefaultMessage(code);
         if (fallback != null) {
            return fallback;
         } else {
            throw new NoSuchMessageException(code, locale);
         }
      }
   }

   public final String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
      String[] codes = resolvable.getCodes();
      if (codes != null) {
         String[] var4 = codes;
         int var5 = codes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String code = var4[var6];
            String message = this.getMessageInternal(code, resolvable.getArguments(), locale);
            if (message != null) {
               return message;
            }
         }
      }

      String defaultMessage = this.getDefaultMessage(resolvable, locale);
      if (defaultMessage != null) {
         return defaultMessage;
      } else {
         throw new NoSuchMessageException(!ObjectUtils.isEmpty((Object[])codes) ? codes[codes.length - 1] : "", locale);
      }
   }

   @Nullable
   protected String getMessageInternal(@Nullable String code, @Nullable Object[] args, @Nullable Locale locale) {
      if (code == null) {
         return null;
      } else {
         if (locale == null) {
            locale = Locale.getDefault();
         }

         Object[] argsToUse = args;
         if (!this.isAlwaysUseMessageFormat() && ObjectUtils.isEmpty(args)) {
            String message = this.resolveCodeWithoutArguments(code, locale);
            if (message != null) {
               return message;
            }
         } else {
            argsToUse = this.resolveArguments(args, locale);
            MessageFormat messageFormat = this.resolveCode(code, locale);
            if (messageFormat != null) {
               synchronized(messageFormat) {
                  return messageFormat.format(argsToUse);
               }
            }
         }

         Properties commonMessages = this.getCommonMessages();
         if (commonMessages != null) {
            String commonMessage = commonMessages.getProperty(code);
            if (commonMessage != null) {
               return this.formatMessage(commonMessage, args, locale);
            }
         }

         return this.getMessageFromParent(code, argsToUse, locale);
      }
   }

   @Nullable
   protected String getMessageFromParent(String code, @Nullable Object[] args, Locale locale) {
      MessageSource parent = this.getParentMessageSource();
      if (parent != null) {
         return parent instanceof AbstractMessageSource ? ((AbstractMessageSource)parent).getMessageInternal(code, args, locale) : parent.getMessage(code, args, (String)null, locale);
      } else {
         return null;
      }
   }

   @Nullable
   protected String getDefaultMessage(MessageSourceResolvable resolvable, Locale locale) {
      String defaultMessage = resolvable.getDefaultMessage();
      String[] codes = resolvable.getCodes();
      if (defaultMessage != null) {
         if (resolvable instanceof DefaultMessageSourceResolvable && !((DefaultMessageSourceResolvable)resolvable).shouldRenderDefaultMessage()) {
            return defaultMessage;
         } else {
            return !ObjectUtils.isEmpty((Object[])codes) && defaultMessage.equals(codes[0]) ? defaultMessage : this.renderDefaultMessage(defaultMessage, resolvable.getArguments(), locale);
         }
      } else {
         return !ObjectUtils.isEmpty((Object[])codes) ? this.getDefaultMessage(codes[0]) : null;
      }
   }

   @Nullable
   protected String getDefaultMessage(String code) {
      return this.isUseCodeAsDefaultMessage() ? code : null;
   }

   protected Object[] resolveArguments(@Nullable Object[] args, Locale locale) {
      if (ObjectUtils.isEmpty(args)) {
         return super.resolveArguments(args, locale);
      } else {
         List resolvedArgs = new ArrayList(args.length);
         Object[] var4 = args;
         int var5 = args.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Object arg = var4[var6];
            if (arg instanceof MessageSourceResolvable) {
               resolvedArgs.add(this.getMessage((MessageSourceResolvable)arg, locale));
            } else {
               resolvedArgs.add(arg);
            }
         }

         return resolvedArgs.toArray();
      }
   }

   @Nullable
   protected String resolveCodeWithoutArguments(String code, Locale locale) {
      MessageFormat messageFormat = this.resolveCode(code, locale);
      if (messageFormat != null) {
         synchronized(messageFormat) {
            return messageFormat.format(new Object[0]);
         }
      } else {
         return null;
      }
   }

   @Nullable
   protected abstract MessageFormat resolveCode(String var1, Locale var2);
}

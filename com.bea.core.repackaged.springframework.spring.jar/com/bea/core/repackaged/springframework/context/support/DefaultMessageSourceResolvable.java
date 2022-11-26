package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.context.MessageSourceResolvable;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.Serializable;

public class DefaultMessageSourceResolvable implements MessageSourceResolvable, Serializable {
   @Nullable
   private final String[] codes;
   @Nullable
   private final Object[] arguments;
   @Nullable
   private final String defaultMessage;

   public DefaultMessageSourceResolvable(String code) {
      this(new String[]{code}, (Object[])null, (String)null);
   }

   public DefaultMessageSourceResolvable(String[] codes) {
      this(codes, (Object[])null, (String)null);
   }

   public DefaultMessageSourceResolvable(String[] codes, String defaultMessage) {
      this(codes, (Object[])null, defaultMessage);
   }

   public DefaultMessageSourceResolvable(String[] codes, Object[] arguments) {
      this(codes, arguments, (String)null);
   }

   public DefaultMessageSourceResolvable(@Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage) {
      this.codes = codes;
      this.arguments = arguments;
      this.defaultMessage = defaultMessage;
   }

   public DefaultMessageSourceResolvable(MessageSourceResolvable resolvable) {
      this(resolvable.getCodes(), resolvable.getArguments(), resolvable.getDefaultMessage());
   }

   @Nullable
   public String getCode() {
      return this.codes != null && this.codes.length > 0 ? this.codes[this.codes.length - 1] : null;
   }

   @Nullable
   public String[] getCodes() {
      return this.codes;
   }

   @Nullable
   public Object[] getArguments() {
      return this.arguments;
   }

   @Nullable
   public String getDefaultMessage() {
      return this.defaultMessage;
   }

   public boolean shouldRenderDefaultMessage() {
      return true;
   }

   protected final String resolvableToString() {
      StringBuilder result = new StringBuilder(64);
      result.append("codes [").append(StringUtils.arrayToDelimitedString(this.codes, ","));
      result.append("]; arguments [").append(StringUtils.arrayToDelimitedString(this.arguments, ","));
      result.append("]; default message [").append(this.defaultMessage).append(']');
      return result.toString();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.resolvableToString();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MessageSourceResolvable)) {
         return false;
      } else {
         MessageSourceResolvable otherResolvable = (MessageSourceResolvable)other;
         return ObjectUtils.nullSafeEquals(this.getCodes(), otherResolvable.getCodes()) && ObjectUtils.nullSafeEquals(this.getArguments(), otherResolvable.getArguments()) && ObjectUtils.nullSafeEquals(this.getDefaultMessage(), otherResolvable.getDefaultMessage());
      }
   }

   public int hashCode() {
      int hashCode = ObjectUtils.nullSafeHashCode((Object[])this.getCodes());
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.getArguments());
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.getDefaultMessage());
      return hashCode;
   }
}

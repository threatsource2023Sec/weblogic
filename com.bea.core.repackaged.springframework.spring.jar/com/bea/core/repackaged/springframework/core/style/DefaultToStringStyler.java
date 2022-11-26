package com.bea.core.repackaged.springframework.core.style;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;

public class DefaultToStringStyler implements ToStringStyler {
   private final ValueStyler valueStyler;

   public DefaultToStringStyler(ValueStyler valueStyler) {
      Assert.notNull(valueStyler, (String)"ValueStyler must not be null");
      this.valueStyler = valueStyler;
   }

   protected final ValueStyler getValueStyler() {
      return this.valueStyler;
   }

   public void styleStart(StringBuilder buffer, Object obj) {
      if (!obj.getClass().isArray()) {
         buffer.append('[').append(ClassUtils.getShortName(obj.getClass()));
         this.styleIdentityHashCode(buffer, obj);
      } else {
         buffer.append('[');
         this.styleIdentityHashCode(buffer, obj);
         buffer.append(' ');
         this.styleValue(buffer, obj);
      }

   }

   private void styleIdentityHashCode(StringBuilder buffer, Object obj) {
      buffer.append('@');
      buffer.append(ObjectUtils.getIdentityHexString(obj));
   }

   public void styleEnd(StringBuilder buffer, Object o) {
      buffer.append(']');
   }

   public void styleField(StringBuilder buffer, String fieldName, @Nullable Object value) {
      this.styleFieldStart(buffer, fieldName);
      this.styleValue(buffer, value);
      this.styleFieldEnd(buffer, fieldName);
   }

   protected void styleFieldStart(StringBuilder buffer, String fieldName) {
      buffer.append(' ').append(fieldName).append(" = ");
   }

   protected void styleFieldEnd(StringBuilder buffer, String fieldName) {
   }

   public void styleValue(StringBuilder buffer, @Nullable Object value) {
      buffer.append(this.valueStyler.style(value));
   }

   public void styleFieldSeparator(StringBuilder buffer) {
      buffer.append(',');
   }
}

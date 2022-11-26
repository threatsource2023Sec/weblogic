package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.NumberUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

public class CustomNumberEditor extends PropertyEditorSupport {
   private final Class numberClass;
   @Nullable
   private final NumberFormat numberFormat;
   private final boolean allowEmpty;

   public CustomNumberEditor(Class numberClass, boolean allowEmpty) throws IllegalArgumentException {
      this(numberClass, (NumberFormat)null, allowEmpty);
   }

   public CustomNumberEditor(Class numberClass, @Nullable NumberFormat numberFormat, boolean allowEmpty) throws IllegalArgumentException {
      if (!Number.class.isAssignableFrom(numberClass)) {
         throw new IllegalArgumentException("Property class must be a subclass of Number");
      } else {
         this.numberClass = numberClass;
         this.numberFormat = numberFormat;
         this.allowEmpty = allowEmpty;
      }
   }

   public void setAsText(String text) throws IllegalArgumentException {
      if (this.allowEmpty && !StringUtils.hasText(text)) {
         this.setValue((Object)null);
      } else if (this.numberFormat != null) {
         this.setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
      } else {
         this.setValue(NumberUtils.parseNumber(text, this.numberClass));
      }

   }

   public void setValue(@Nullable Object value) {
      if (value instanceof Number) {
         super.setValue(NumberUtils.convertNumberToTargetClass((Number)value, this.numberClass));
      } else {
         super.setValue(value);
      }

   }

   public String getAsText() {
      Object value = this.getValue();
      if (value == null) {
         return "";
      } else {
         return this.numberFormat != null ? this.numberFormat.format(value) : value.toString();
      }
   }
}

package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

public class CustomBooleanEditor extends PropertyEditorSupport {
   public static final String VALUE_TRUE = "true";
   public static final String VALUE_FALSE = "false";
   public static final String VALUE_ON = "on";
   public static final String VALUE_OFF = "off";
   public static final String VALUE_YES = "yes";
   public static final String VALUE_NO = "no";
   public static final String VALUE_1 = "1";
   public static final String VALUE_0 = "0";
   @Nullable
   private final String trueString;
   @Nullable
   private final String falseString;
   private final boolean allowEmpty;

   public CustomBooleanEditor(boolean allowEmpty) {
      this((String)null, (String)null, allowEmpty);
   }

   public CustomBooleanEditor(@Nullable String trueString, @Nullable String falseString, boolean allowEmpty) {
      this.trueString = trueString;
      this.falseString = falseString;
      this.allowEmpty = allowEmpty;
   }

   public void setAsText(@Nullable String text) throws IllegalArgumentException {
      String input = text != null ? text.trim() : null;
      if (this.allowEmpty && !StringUtils.hasLength(input)) {
         this.setValue((Object)null);
      } else if (this.trueString != null && this.trueString.equalsIgnoreCase(input)) {
         this.setValue(Boolean.TRUE);
      } else if (this.falseString != null && this.falseString.equalsIgnoreCase(input)) {
         this.setValue(Boolean.FALSE);
      } else if (this.trueString != null || !"true".equalsIgnoreCase(input) && !"on".equalsIgnoreCase(input) && !"yes".equalsIgnoreCase(input) && !"1".equals(input)) {
         if (this.falseString != null || !"false".equalsIgnoreCase(input) && !"off".equalsIgnoreCase(input) && !"no".equalsIgnoreCase(input) && !"0".equals(input)) {
            throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
         }

         this.setValue(Boolean.FALSE);
      } else {
         this.setValue(Boolean.TRUE);
      }

   }

   public String getAsText() {
      if (Boolean.TRUE.equals(this.getValue())) {
         return this.trueString != null ? this.trueString : "true";
      } else if (Boolean.FALSE.equals(this.getValue())) {
         return this.falseString != null ? this.falseString : "false";
      } else {
         return "";
      }
   }
}

package com.bea.core.repackaged.springframework.core.style;

public abstract class StylerUtils {
   static final ValueStyler DEFAULT_VALUE_STYLER = new DefaultValueStyler();

   public static String style(Object value) {
      return DEFAULT_VALUE_STYLER.style(value);
   }
}

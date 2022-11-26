package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleEditor extends PropertyEditorSupport {
   public static final String BASE_NAME_SEPARATOR = "_";

   public void setAsText(String text) throws IllegalArgumentException {
      Assert.hasText(text, "'text' must not be empty");
      String name = text.trim();
      int separator = name.indexOf("_");
      if (separator == -1) {
         this.setValue(ResourceBundle.getBundle(name));
      } else {
         String baseName = name.substring(0, separator);
         if (!StringUtils.hasText(baseName)) {
            throw new IllegalArgumentException("Invalid ResourceBundle name: '" + text + "'");
         }

         String localeString = name.substring(separator + 1);
         Locale locale = StringUtils.parseLocaleString(localeString);
         this.setValue(locale != null ? ResourceBundle.getBundle(baseName, locale) : ResourceBundle.getBundle(baseName));
      }

   }
}

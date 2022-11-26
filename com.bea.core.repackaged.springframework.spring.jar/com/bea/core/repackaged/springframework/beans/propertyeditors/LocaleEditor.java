package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

public class LocaleEditor extends PropertyEditorSupport {
   public void setAsText(String text) {
      this.setValue(StringUtils.parseLocaleString(text));
   }

   public String getAsText() {
      Object value = this.getValue();
      return value != null ? value.toString() : "";
   }
}

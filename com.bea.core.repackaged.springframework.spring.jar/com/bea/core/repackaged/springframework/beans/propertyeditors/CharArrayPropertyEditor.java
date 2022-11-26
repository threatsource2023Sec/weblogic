package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.PropertyEditorSupport;

public class CharArrayPropertyEditor extends PropertyEditorSupport {
   public void setAsText(@Nullable String text) {
      this.setValue(text != null ? text.toCharArray() : null);
   }

   public String getAsText() {
      char[] value = (char[])((char[])this.getValue());
      return value != null ? new String(value) : "";
   }
}

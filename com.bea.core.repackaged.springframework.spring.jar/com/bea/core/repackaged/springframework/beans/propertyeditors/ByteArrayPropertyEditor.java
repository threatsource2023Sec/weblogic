package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.PropertyEditorSupport;

public class ByteArrayPropertyEditor extends PropertyEditorSupport {
   public void setAsText(@Nullable String text) {
      this.setValue(text != null ? text.getBytes() : null);
   }

   public String getAsText() {
      byte[] value = (byte[])((byte[])this.getValue());
      return value != null ? new String(value) : "";
   }
}

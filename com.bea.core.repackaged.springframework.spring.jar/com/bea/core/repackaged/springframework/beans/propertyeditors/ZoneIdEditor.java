package com.bea.core.repackaged.springframework.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.time.ZoneId;

public class ZoneIdEditor extends PropertyEditorSupport {
   public void setAsText(String text) throws IllegalArgumentException {
      this.setValue(ZoneId.of(text));
   }

   public String getAsText() {
      ZoneId value = (ZoneId)this.getValue();
      return value != null ? value.getId() : "";
   }
}

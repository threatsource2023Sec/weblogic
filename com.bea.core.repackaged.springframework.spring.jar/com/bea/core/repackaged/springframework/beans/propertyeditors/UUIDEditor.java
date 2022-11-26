package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;
import java.util.UUID;

public class UUIDEditor extends PropertyEditorSupport {
   public void setAsText(String text) throws IllegalArgumentException {
      if (StringUtils.hasText(text)) {
         this.setValue(UUID.fromString(text));
      } else {
         this.setValue((Object)null);
      }

   }

   public String getAsText() {
      UUID value = (UUID)this.getValue();
      return value != null ? value.toString() : "";
   }
}

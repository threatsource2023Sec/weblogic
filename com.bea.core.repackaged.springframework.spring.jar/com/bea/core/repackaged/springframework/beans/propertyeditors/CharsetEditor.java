package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;
import java.nio.charset.Charset;

public class CharsetEditor extends PropertyEditorSupport {
   public void setAsText(String text) throws IllegalArgumentException {
      if (StringUtils.hasText(text)) {
         this.setValue(Charset.forName(text));
      } else {
         this.setValue((Object)null);
      }

   }

   public String getAsText() {
      Charset value = (Charset)this.getValue();
      return value != null ? value.name() : "";
   }
}

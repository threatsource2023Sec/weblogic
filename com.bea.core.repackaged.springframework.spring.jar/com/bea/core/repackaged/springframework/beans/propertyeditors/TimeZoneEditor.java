package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;
import java.util.TimeZone;

public class TimeZoneEditor extends PropertyEditorSupport {
   public void setAsText(String text) throws IllegalArgumentException {
      this.setValue(StringUtils.parseTimeZoneString(text));
   }

   public String getAsText() {
      TimeZone value = (TimeZone)this.getValue();
      return value != null ? value.getID() : "";
   }
}

package com.bea.core.repackaged.springframework.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.Currency;

public class CurrencyEditor extends PropertyEditorSupport {
   public void setAsText(String text) throws IllegalArgumentException {
      this.setValue(Currency.getInstance(text));
   }

   public String getAsText() {
      Currency value = (Currency)this.getValue();
      return value != null ? value.getCurrencyCode() : "";
   }
}

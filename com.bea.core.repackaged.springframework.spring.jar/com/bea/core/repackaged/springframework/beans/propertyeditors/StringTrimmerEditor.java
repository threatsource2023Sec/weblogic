package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

public class StringTrimmerEditor extends PropertyEditorSupport {
   @Nullable
   private final String charsToDelete;
   private final boolean emptyAsNull;

   public StringTrimmerEditor(boolean emptyAsNull) {
      this.charsToDelete = null;
      this.emptyAsNull = emptyAsNull;
   }

   public StringTrimmerEditor(String charsToDelete, boolean emptyAsNull) {
      this.charsToDelete = charsToDelete;
      this.emptyAsNull = emptyAsNull;
   }

   public void setAsText(@Nullable String text) {
      if (text == null) {
         this.setValue((Object)null);
      } else {
         String value = text.trim();
         if (this.charsToDelete != null) {
            value = StringUtils.deleteAny(value, this.charsToDelete);
         }

         if (this.emptyAsNull && "".equals(value)) {
            this.setValue((Object)null);
         } else {
            this.setValue(value);
         }
      }

   }

   public String getAsText() {
      Object value = this.getValue();
      return value != null ? value.toString() : "";
   }
}

package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.PropertyEditorSupport;
import java.util.regex.Pattern;

public class PatternEditor extends PropertyEditorSupport {
   private final int flags;

   public PatternEditor() {
      this.flags = 0;
   }

   public PatternEditor(int flags) {
      this.flags = flags;
   }

   public void setAsText(@Nullable String text) {
      this.setValue(text != null ? Pattern.compile(text, this.flags) : null);
   }

   public String getAsText() {
      Pattern value = (Pattern)this.getValue();
      return value != null ? value.pattern() : "";
   }
}

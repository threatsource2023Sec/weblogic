package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

public class CharacterEditor extends PropertyEditorSupport {
   private static final String UNICODE_PREFIX = "\\u";
   private static final int UNICODE_LENGTH = 6;
   private final boolean allowEmpty;

   public CharacterEditor(boolean allowEmpty) {
      this.allowEmpty = allowEmpty;
   }

   public void setAsText(@Nullable String text) throws IllegalArgumentException {
      if (this.allowEmpty && !StringUtils.hasLength(text)) {
         this.setValue((Object)null);
      } else {
         if (text == null) {
            throw new IllegalArgumentException("null String cannot be converted to char type");
         }

         if (this.isUnicodeCharacterSequence(text)) {
            this.setAsUnicode(text);
         } else {
            if (text.length() != 1) {
               throw new IllegalArgumentException("String [" + text + "] with length " + text.length() + " cannot be converted to char type: neither Unicode nor single character");
            }

            this.setValue(text.charAt(0));
         }
      }

   }

   public String getAsText() {
      Object value = this.getValue();
      return value != null ? value.toString() : "";
   }

   private boolean isUnicodeCharacterSequence(String sequence) {
      return sequence.startsWith("\\u") && sequence.length() == 6;
   }

   private void setAsUnicode(String text) {
      int code = Integer.parseInt(text.substring("\\u".length()), 16);
      this.setValue((char)code);
   }
}

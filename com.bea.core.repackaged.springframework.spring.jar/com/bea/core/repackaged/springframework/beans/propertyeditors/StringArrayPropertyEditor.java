package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

public class StringArrayPropertyEditor extends PropertyEditorSupport {
   public static final String DEFAULT_SEPARATOR = ",";
   private final String separator;
   @Nullable
   private final String charsToDelete;
   private final boolean emptyArrayAsNull;
   private final boolean trimValues;

   public StringArrayPropertyEditor() {
      this(",", (String)null, false);
   }

   public StringArrayPropertyEditor(String separator) {
      this(separator, (String)null, false);
   }

   public StringArrayPropertyEditor(String separator, boolean emptyArrayAsNull) {
      this(separator, (String)null, emptyArrayAsNull);
   }

   public StringArrayPropertyEditor(String separator, boolean emptyArrayAsNull, boolean trimValues) {
      this(separator, (String)null, emptyArrayAsNull, trimValues);
   }

   public StringArrayPropertyEditor(String separator, @Nullable String charsToDelete, boolean emptyArrayAsNull) {
      this(separator, charsToDelete, emptyArrayAsNull, true);
   }

   public StringArrayPropertyEditor(String separator, @Nullable String charsToDelete, boolean emptyArrayAsNull, boolean trimValues) {
      this.separator = separator;
      this.charsToDelete = charsToDelete;
      this.emptyArrayAsNull = emptyArrayAsNull;
      this.trimValues = trimValues;
   }

   public void setAsText(String text) throws IllegalArgumentException {
      String[] array = StringUtils.delimitedListToStringArray(text, this.separator, this.charsToDelete);
      if (this.trimValues) {
         array = StringUtils.trimArrayElements(array);
      }

      if (this.emptyArrayAsNull && array.length == 0) {
         this.setValue((Object)null);
      } else {
         this.setValue(array);
      }

   }

   public String getAsText() {
      return StringUtils.arrayToDelimitedString(ObjectUtils.toObjectArray(this.getValue()), this.separator);
   }
}

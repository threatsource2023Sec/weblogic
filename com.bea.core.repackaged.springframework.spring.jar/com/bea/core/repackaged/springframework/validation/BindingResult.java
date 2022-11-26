package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistry;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.PropertyEditor;
import java.util.Map;

public interface BindingResult extends Errors {
   String MODEL_KEY_PREFIX = BindingResult.class.getName() + ".";

   @Nullable
   Object getTarget();

   Map getModel();

   @Nullable
   Object getRawFieldValue(String var1);

   @Nullable
   PropertyEditor findEditor(@Nullable String var1, @Nullable Class var2);

   @Nullable
   PropertyEditorRegistry getPropertyEditorRegistry();

   String[] resolveMessageCodes(String var1);

   String[] resolveMessageCodes(String var1, String var2);

   void addError(ObjectError var1);

   default void recordFieldValue(String field, Class type, @Nullable Object value) {
   }

   default void recordSuppressedField(String field) {
   }

   default String[] getSuppressedFields() {
      return new String[0];
   }
}

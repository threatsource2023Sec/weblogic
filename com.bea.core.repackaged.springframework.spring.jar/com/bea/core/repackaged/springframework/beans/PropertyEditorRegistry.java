package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.PropertyEditor;

public interface PropertyEditorRegistry {
   void registerCustomEditor(Class var1, PropertyEditor var2);

   void registerCustomEditor(@Nullable Class var1, @Nullable String var2, PropertyEditor var3);

   @Nullable
   PropertyEditor findCustomEditor(@Nullable Class var1, @Nullable String var2);
}

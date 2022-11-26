package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

public class ClassEditor extends PropertyEditorSupport {
   @Nullable
   private final ClassLoader classLoader;

   public ClassEditor() {
      this((ClassLoader)null);
   }

   public ClassEditor(@Nullable ClassLoader classLoader) {
      this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
   }

   public void setAsText(String text) throws IllegalArgumentException {
      if (StringUtils.hasText(text)) {
         this.setValue(ClassUtils.resolveClassName(text.trim(), this.classLoader));
      } else {
         this.setValue((Object)null);
      }

   }

   public String getAsText() {
      Class clazz = (Class)this.getValue();
      return clazz != null ? ClassUtils.getQualifiedName(clazz) : "";
   }
}

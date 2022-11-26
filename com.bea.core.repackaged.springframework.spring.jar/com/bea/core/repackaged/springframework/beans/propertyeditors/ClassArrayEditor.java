package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

public class ClassArrayEditor extends PropertyEditorSupport {
   @Nullable
   private final ClassLoader classLoader;

   public ClassArrayEditor() {
      this((ClassLoader)null);
   }

   public ClassArrayEditor(@Nullable ClassLoader classLoader) {
      this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
   }

   public void setAsText(String text) throws IllegalArgumentException {
      if (StringUtils.hasText(text)) {
         String[] classNames = StringUtils.commaDelimitedListToStringArray(text);
         Class[] classes = new Class[classNames.length];

         for(int i = 0; i < classNames.length; ++i) {
            String className = classNames[i].trim();
            classes[i] = ClassUtils.resolveClassName(className, this.classLoader);
         }

         this.setValue(classes);
      } else {
         this.setValue((Object)null);
      }

   }

   public String getAsText() {
      Class[] classes = (Class[])((Class[])this.getValue());
      if (ObjectUtils.isEmpty((Object[])classes)) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < classes.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(ClassUtils.getQualifiedName(classes[i]));
         }

         return sb.toString();
      }
   }
}

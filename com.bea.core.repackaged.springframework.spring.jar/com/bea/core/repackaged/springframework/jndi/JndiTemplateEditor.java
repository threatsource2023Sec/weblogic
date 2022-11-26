package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.springframework.beans.propertyeditors.PropertiesEditor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.PropertyEditorSupport;
import java.util.Properties;

public class JndiTemplateEditor extends PropertyEditorSupport {
   private final PropertiesEditor propertiesEditor = new PropertiesEditor();

   public void setAsText(@Nullable String text) throws IllegalArgumentException {
      if (text == null) {
         throw new IllegalArgumentException("JndiTemplate cannot be created from null string");
      } else {
         if ("".equals(text)) {
            this.setValue(new JndiTemplate());
         } else {
            this.propertiesEditor.setAsText(text);
            Properties props = (Properties)this.propertiesEditor.getValue();
            this.setValue(new JndiTemplate(props));
         }

      }
   }
}

package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.beans.propertyeditors.PropertiesEditor;
import java.beans.PropertyEditorSupport;
import java.util.Properties;

public class PropertyValuesEditor extends PropertyEditorSupport {
   private final PropertiesEditor propertiesEditor = new PropertiesEditor();

   public void setAsText(String text) throws IllegalArgumentException {
      this.propertiesEditor.setAsText(text);
      Properties props = (Properties)this.propertiesEditor.getValue();
      this.setValue(new MutablePropertyValues(props));
   }
}

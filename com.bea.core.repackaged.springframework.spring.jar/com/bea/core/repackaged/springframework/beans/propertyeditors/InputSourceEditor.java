package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceEditor;
import com.bea.core.repackaged.springframework.util.Assert;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import org.xml.sax.InputSource;

public class InputSourceEditor extends PropertyEditorSupport {
   private final ResourceEditor resourceEditor;

   public InputSourceEditor() {
      this.resourceEditor = new ResourceEditor();
   }

   public InputSourceEditor(ResourceEditor resourceEditor) {
      Assert.notNull(resourceEditor, (String)"ResourceEditor must not be null");
      this.resourceEditor = resourceEditor;
   }

   public void setAsText(String text) throws IllegalArgumentException {
      this.resourceEditor.setAsText(text);
      Resource resource = (Resource)this.resourceEditor.getValue();

      try {
         this.setValue(resource != null ? new InputSource(resource.getURL().toString()) : null);
      } catch (IOException var4) {
         throw new IllegalArgumentException("Could not retrieve URL for " + resource + ": " + var4.getMessage());
      }
   }

   public String getAsText() {
      InputSource value = (InputSource)this.getValue();
      return value != null ? value.getSystemId() : "";
   }
}

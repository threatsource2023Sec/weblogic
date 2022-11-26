package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceEditor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.beans.PropertyEditorSupport;
import java.io.IOException;

public class InputStreamEditor extends PropertyEditorSupport {
   private final ResourceEditor resourceEditor;

   public InputStreamEditor() {
      this.resourceEditor = new ResourceEditor();
   }

   public InputStreamEditor(ResourceEditor resourceEditor) {
      Assert.notNull(resourceEditor, (String)"ResourceEditor must not be null");
      this.resourceEditor = resourceEditor;
   }

   public void setAsText(String text) throws IllegalArgumentException {
      this.resourceEditor.setAsText(text);
      Resource resource = (Resource)this.resourceEditor.getValue();

      try {
         this.setValue(resource != null ? resource.getInputStream() : null);
      } catch (IOException var4) {
         throw new IllegalArgumentException("Failed to retrieve InputStream for " + resource, var4);
      }
   }

   @Nullable
   public String getAsText() {
      return null;
   }
}

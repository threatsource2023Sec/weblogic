package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.PropertyEditorSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class PropertiesEditor extends PropertyEditorSupport {
   public void setAsText(@Nullable String text) throws IllegalArgumentException {
      Properties props = new Properties();
      if (text != null) {
         try {
            props.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.ISO_8859_1)));
         } catch (IOException var4) {
            throw new IllegalArgumentException("Failed to parse [" + text + "] into Properties", var4);
         }
      }

      this.setValue(props);
   }

   public void setValue(Object value) {
      if (!(value instanceof Properties) && value instanceof Map) {
         Properties props = new Properties();
         props.putAll((Map)value);
         super.setValue(props);
      } else {
         super.setValue(value);
      }

   }
}

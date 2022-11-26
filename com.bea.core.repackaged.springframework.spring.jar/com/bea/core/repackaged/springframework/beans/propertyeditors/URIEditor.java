package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.core.io.ClassPathResource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class URIEditor extends PropertyEditorSupport {
   @Nullable
   private final ClassLoader classLoader;
   private final boolean encode;

   public URIEditor() {
      this(true);
   }

   public URIEditor(boolean encode) {
      this.classLoader = null;
      this.encode = encode;
   }

   public URIEditor(@Nullable ClassLoader classLoader) {
      this(classLoader, true);
   }

   public URIEditor(@Nullable ClassLoader classLoader, boolean encode) {
      this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
      this.encode = encode;
   }

   public void setAsText(String text) throws IllegalArgumentException {
      if (StringUtils.hasText(text)) {
         String uri = text.trim();
         if (this.classLoader != null && uri.startsWith("classpath:")) {
            ClassPathResource resource = new ClassPathResource(uri.substring("classpath:".length()), this.classLoader);

            try {
               this.setValue(resource.getURI());
            } catch (IOException var6) {
               throw new IllegalArgumentException("Could not retrieve URI for " + resource + ": " + var6.getMessage());
            }
         } else {
            try {
               this.setValue(this.createURI(uri));
            } catch (URISyntaxException var5) {
               throw new IllegalArgumentException("Invalid URI syntax: " + var5);
            }
         }
      } else {
         this.setValue((Object)null);
      }

   }

   protected URI createURI(String value) throws URISyntaxException {
      int colonIndex = value.indexOf(58);
      if (this.encode && colonIndex != -1) {
         int fragmentIndex = value.indexOf(35, colonIndex + 1);
         String scheme = value.substring(0, colonIndex);
         String ssp = value.substring(colonIndex + 1, fragmentIndex > 0 ? fragmentIndex : value.length());
         String fragment = fragmentIndex > 0 ? value.substring(fragmentIndex + 1) : null;
         return new URI(scheme, ssp, fragment);
      } else {
         return new URI(value);
      }
   }

   public String getAsText() {
      URI value = (URI)this.getValue();
      return value != null ? value.toString() : "";
   }
}

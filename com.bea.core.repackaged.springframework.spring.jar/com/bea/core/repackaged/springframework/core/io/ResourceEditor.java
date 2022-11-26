package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.core.env.PropertyResolver;
import com.bea.core.repackaged.springframework.core.env.StandardEnvironment;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;
import java.io.IOException;

public class ResourceEditor extends PropertyEditorSupport {
   private final ResourceLoader resourceLoader;
   @Nullable
   private PropertyResolver propertyResolver;
   private final boolean ignoreUnresolvablePlaceholders;

   public ResourceEditor() {
      this(new DefaultResourceLoader(), (PropertyResolver)null);
   }

   public ResourceEditor(ResourceLoader resourceLoader, @Nullable PropertyResolver propertyResolver) {
      this(resourceLoader, propertyResolver, true);
   }

   public ResourceEditor(ResourceLoader resourceLoader, @Nullable PropertyResolver propertyResolver, boolean ignoreUnresolvablePlaceholders) {
      Assert.notNull(resourceLoader, (String)"ResourceLoader must not be null");
      this.resourceLoader = resourceLoader;
      this.propertyResolver = propertyResolver;
      this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
   }

   public void setAsText(String text) {
      if (StringUtils.hasText(text)) {
         String locationToUse = this.resolvePath(text).trim();
         this.setValue(this.resourceLoader.getResource(locationToUse));
      } else {
         this.setValue((Object)null);
      }

   }

   protected String resolvePath(String path) {
      if (this.propertyResolver == null) {
         this.propertyResolver = new StandardEnvironment();
      }

      return this.ignoreUnresolvablePlaceholders ? this.propertyResolver.resolvePlaceholders(path) : this.propertyResolver.resolveRequiredPlaceholders(path);
   }

   @Nullable
   public String getAsText() {
      Resource value = (Resource)this.getValue();

      try {
         return value != null ? value.getURL().toExternalForm() : "";
      } catch (IOException var3) {
         return null;
      }
   }
}

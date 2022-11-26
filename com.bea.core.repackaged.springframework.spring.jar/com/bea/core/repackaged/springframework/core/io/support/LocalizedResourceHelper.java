package com.bea.core.repackaged.springframework.core.io.support;

import com.bea.core.repackaged.springframework.core.io.DefaultResourceLoader;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Locale;

public class LocalizedResourceHelper {
   public static final String DEFAULT_SEPARATOR = "_";
   private final ResourceLoader resourceLoader;
   private String separator = "_";

   public LocalizedResourceHelper() {
      this.resourceLoader = new DefaultResourceLoader();
   }

   public LocalizedResourceHelper(ResourceLoader resourceLoader) {
      Assert.notNull(resourceLoader, (String)"ResourceLoader must not be null");
      this.resourceLoader = resourceLoader;
   }

   public void setSeparator(@Nullable String separator) {
      this.separator = separator != null ? separator : "_";
   }

   public Resource findLocalizedResource(String name, String extension, @Nullable Locale locale) {
      Assert.notNull(name, (String)"Name must not be null");
      Assert.notNull(extension, (String)"Extension must not be null");
      Resource resource = null;
      String lang;
      if (locale != null) {
         lang = locale.getLanguage();
         String country = locale.getCountry();
         String variant = locale.getVariant();
         String location;
         if (variant.length() > 0) {
            location = name + this.separator + lang + this.separator + country + this.separator + variant + extension;
            resource = this.resourceLoader.getResource(location);
         }

         if ((resource == null || !resource.exists()) && country.length() > 0) {
            location = name + this.separator + lang + this.separator + country + extension;
            resource = this.resourceLoader.getResource(location);
         }

         if ((resource == null || !resource.exists()) && lang.length() > 0) {
            location = name + this.separator + lang + extension;
            resource = this.resourceLoader.getResource(location);
         }
      }

      if (resource == null || !resource.exists()) {
         lang = name + extension;
         resource = this.resourceLoader.getResource(lang);
      }

      return resource;
   }
}

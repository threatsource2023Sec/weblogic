package com.sun.faces.application;

import com.sun.faces.util.Util;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ApplicationResourceBundle {
   public static final String DEFAULT_KEY = "DEFAULT";
   private final String baseName;
   private final Map displayNames;
   private final Map descriptions;
   private volatile Map resources;

   public ApplicationResourceBundle(String baseName, Map displayNames, Map descriptions) {
      if (baseName == null) {
         throw new IllegalArgumentException();
      } else {
         this.baseName = baseName;
         this.displayNames = displayNames;
         this.descriptions = descriptions;
         this.resources = new HashMap(4, 1.0F);
      }
   }

   public String getBaseName() {
      return this.baseName;
   }

   public ResourceBundle getResourceBundle(Locale locale) {
      if (locale == null) {
         locale = Locale.getDefault();
      }

      ResourceBundle bundle = (ResourceBundle)this.resources.get(locale);
      if (bundle == null) {
         ClassLoader loader = Util.getCurrentLoader(this);
         synchronized(this) {
            bundle = (ResourceBundle)this.resources.get(locale);
            if (bundle == null) {
               bundle = ResourceBundle.getBundle(this.baseName, locale, loader);
               this.resources.put(locale, bundle);
            }
         }
      }

      return bundle;
   }

   public String getDisplayName(Locale locale) {
      String displayName = null;
      if (this.displayNames != null) {
         displayName = this.queryMap(locale, this.displayNames);
      }

      return displayName != null ? displayName : "";
   }

   public String getDescription(Locale locale) {
      String description = null;
      if (this.descriptions != null) {
         description = this.queryMap(locale, this.descriptions);
      }

      return description != null ? description : "";
   }

   private String queryMap(Locale locale, Map map) {
      if (locale == null) {
         return (String)map.get("DEFAULT");
      } else {
         String key = locale.toString();
         String description = (String)map.get(key);
         return description == null ? (String)map.get("DEFAULT") : null;
      }
   }
}

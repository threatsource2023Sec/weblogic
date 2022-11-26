package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Map;

public class SystemEnvironmentPropertySource extends MapPropertySource {
   public SystemEnvironmentPropertySource(String name, Map source) {
      super(name, source);
   }

   public boolean containsProperty(String name) {
      return this.getProperty(name) != null;
   }

   @Nullable
   public Object getProperty(String name) {
      String actualName = this.resolvePropertyName(name);
      if (this.logger.isDebugEnabled() && !name.equals(actualName)) {
         this.logger.debug("PropertySource '" + this.getName() + "' does not contain property '" + name + "', but found equivalent '" + actualName + "'");
      }

      return super.getProperty(actualName);
   }

   protected final String resolvePropertyName(String name) {
      Assert.notNull(name, (String)"Property name must not be null");
      String resolvedName = this.checkPropertyName(name);
      if (resolvedName != null) {
         return resolvedName;
      } else {
         String uppercasedName = name.toUpperCase();
         if (!name.equals(uppercasedName)) {
            resolvedName = this.checkPropertyName(uppercasedName);
            if (resolvedName != null) {
               return resolvedName;
            }
         }

         return name;
      }
   }

   @Nullable
   private String checkPropertyName(String name) {
      if (this.containsKey(name)) {
         return name;
      } else {
         String noDotName = name.replace('.', '_');
         if (!name.equals(noDotName) && this.containsKey(noDotName)) {
            return noDotName;
         } else {
            String noHyphenName = name.replace('-', '_');
            if (!name.equals(noHyphenName) && this.containsKey(noHyphenName)) {
               return noHyphenName;
            } else {
               String noDotNoHyphenName = noDotName.replace('-', '_');
               return !noDotName.equals(noDotNoHyphenName) && this.containsKey(noDotNoHyphenName) ? noDotNoHyphenName : null;
            }
         }
      }
   }

   private boolean containsKey(String name) {
      return this.isSecurityManagerPresent() ? ((Map)this.source).keySet().contains(name) : ((Map)this.source).containsKey(name);
   }

   protected boolean isSecurityManagerPresent() {
      return System.getSecurityManager() != null;
   }
}

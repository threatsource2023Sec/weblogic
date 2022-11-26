package org.apache.xml.security.utils.resolver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ResourceResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(ResourceResolverSpi.class);
   protected Map properties;

   public abstract XMLSignatureInput engineResolveURI(ResourceResolverContext var1) throws ResourceResolverException;

   public void engineSetProperty(String key, String value) {
      if (this.properties == null) {
         this.properties = new HashMap();
      }

      this.properties.put(key, value);
   }

   public String engineGetProperty(String key) {
      return this.properties == null ? null : (String)this.properties.get(key);
   }

   public void engineAddProperies(Map newProperties) {
      if (newProperties != null && !newProperties.isEmpty()) {
         if (this.properties == null) {
            this.properties = new HashMap();
         }

         this.properties.putAll(newProperties);
      }

   }

   public boolean engineIsThreadSafe() {
      return false;
   }

   public abstract boolean engineCanResolveURI(ResourceResolverContext var1);

   public String[] engineGetPropertyKeys() {
      return new String[0];
   }

   public boolean understandsProperty(String propertyToTest) {
      String[] understood = this.engineGetPropertyKeys();
      if (understood != null) {
         String[] var3 = understood;
         int var4 = understood.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String str = var3[var5];
            if (str.equals(propertyToTest)) {
               return true;
            }
         }
      }

      return false;
   }

   public static String fixURI(String str) {
      str = str.replace(File.separatorChar, '/');
      char ch1;
      char ch0;
      if (str.length() >= 4) {
         ch1 = Character.toUpperCase(str.charAt(0));
         ch0 = str.charAt(1);
         char ch2 = str.charAt(2);
         char ch3 = str.charAt(3);
         boolean isDosFilename = 'A' <= ch1 && ch1 <= 'Z' && ch0 == ':' && ch2 == '/' && ch3 != '/';
         if (isDosFilename) {
            LOG.debug("Found DOS filename: {}", str);
         }
      }

      if (str.length() >= 2) {
         ch1 = str.charAt(1);
         if (ch1 == ':') {
            ch0 = Character.toUpperCase(str.charAt(0));
            if ('A' <= ch0 && ch0 <= 'Z') {
               str = "/" + str;
            }
         }
      }

      return str;
   }
}

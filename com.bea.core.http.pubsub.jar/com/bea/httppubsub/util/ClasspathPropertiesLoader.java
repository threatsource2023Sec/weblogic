package com.bea.httppubsub.util;

import com.bea.httppubsub.PubSubLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClasspathPropertiesLoader implements PropertiesLoader {
   private final Properties props;

   public ClasspathPropertiesLoader(String name) {
      this.props = this.initializeProperties(name);
   }

   public Properties load() {
      return this.props;
   }

   private Properties initializeProperties(String name) {
      String nameForUse = name.startsWith("/") ? name : "/" + name;
      InputStream is = this.getClass().getResourceAsStream(nameForUse);
      return is == null ? null : this.loadPropertiesFromInputStream(is, name);
   }

   private Properties loadPropertiesFromInputStream(InputStream is, String name) {
      Properties var4;
      try {
         Properties result = new Properties();
         result.load(is);
         var4 = result;
      } catch (IOException var8) {
         PubSubLogger.logCannotLoadProperty(name, var8);
         throw new RuntimeException(PubSubLogger.logCannotLoadPropertyLoggable(name, var8).getMessage());
      } finally {
         IOUtils.closeInputStreamIfNecessary(is);
      }

      return var4;
   }
}

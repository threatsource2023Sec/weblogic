package com.bea.core.repackaged.springframework.jmx.support;

import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public final class ObjectNameManager {
   private ObjectNameManager() {
   }

   public static ObjectName getInstance(Object objectName) throws MalformedObjectNameException {
      if (objectName instanceof ObjectName) {
         return (ObjectName)objectName;
      } else if (!(objectName instanceof String)) {
         throw new MalformedObjectNameException("Invalid ObjectName value type [" + objectName.getClass().getName() + "]: only ObjectName and String supported.");
      } else {
         return getInstance((String)objectName);
      }
   }

   public static ObjectName getInstance(String objectName) throws MalformedObjectNameException {
      return ObjectName.getInstance(objectName);
   }

   public static ObjectName getInstance(String domainName, String key, String value) throws MalformedObjectNameException {
      return ObjectName.getInstance(domainName, key, value);
   }

   public static ObjectName getInstance(String domainName, Hashtable properties) throws MalformedObjectNameException {
      return ObjectName.getInstance(domainName, properties);
   }
}

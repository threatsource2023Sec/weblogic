package org.apache.openjpa.conf;

import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;

public class OpenJPAVersionAndConfigurationTypeValidationPolicy implements CacheMarshaller.ValidationPolicy, Configurable {
   private String confClassName;

   public Object getCacheableData(Object o) {
      return new Object[]{OpenJPAVersion.VERSION_ID, this.confClassName, o};
   }

   public Object getValidData(Object o) {
      Object[] array = (Object[])((Object[])o);
      if (array.length != 3) {
         return null;
      } else {
         return OpenJPAVersion.VERSION_ID.equals(array[0]) && this.confClassName.equals(array[1]) ? array[2] : null;
      }
   }

   public void setConfiguration(Configuration conf) {
      this.confClassName = conf.getClass().getName();
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }
}

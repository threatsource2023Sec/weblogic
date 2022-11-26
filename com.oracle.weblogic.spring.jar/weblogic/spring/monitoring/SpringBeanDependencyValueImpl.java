package weblogic.spring.monitoring;

import java.io.Serializable;
import weblogic.management.runtime.SpringBeanDependencyValue;

public class SpringBeanDependencyValueImpl implements Serializable, SpringBeanDependencyValue {
   private final int injectionType;
   private final String key;
   private final String value;

   public SpringBeanDependencyValueImpl(int injectionType, String key, String value) {
      this.injectionType = injectionType;
      this.key = key;
      this.value = value;
   }

   public int getInjectionType() {
      return this.injectionType;
   }

   public String getKey() {
      return this.key;
   }

   public String getStringValue() {
      return this.value;
   }
}

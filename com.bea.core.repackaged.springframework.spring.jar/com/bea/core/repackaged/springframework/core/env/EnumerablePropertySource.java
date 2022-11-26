package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.util.ObjectUtils;

public abstract class EnumerablePropertySource extends PropertySource {
   public EnumerablePropertySource(String name, Object source) {
      super(name, source);
   }

   protected EnumerablePropertySource(String name) {
      super(name);
   }

   public boolean containsProperty(String name) {
      return ObjectUtils.containsElement(this.getPropertyNames(), name);
   }

   public abstract String[] getPropertyNames();
}

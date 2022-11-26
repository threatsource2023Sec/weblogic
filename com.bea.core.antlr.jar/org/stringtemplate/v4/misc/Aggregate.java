package org.stringtemplate.v4.misc;

import java.util.HashMap;

public class Aggregate {
   public HashMap properties = new HashMap();

   protected void put(String propName, Object propValue) {
      this.properties.put(propName, propValue);
   }

   public Object get(String propName) {
      return this.properties.get(propName);
   }

   public String toString() {
      return this.properties.toString();
   }
}

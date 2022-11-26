package com.asn1c.codec;

import java.util.HashMap;

public class FactoryMap {
   protected HashMap factories = new HashMap();

   public void put(Factory var1) {
      this.factories.put(var1.getModuleName(), var1);
   }

   public void remove(Factory var1) {
      this.factories.remove(var1.getModuleName());
   }

   public Factory get(String var1) {
      return (Factory)this.factories.get(var1);
   }
}

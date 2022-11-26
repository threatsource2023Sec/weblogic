package org.glassfish.admin.rest.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PropertyExceptions extends Exception {
   private List propertyExceptions = new ArrayList();

   public void add(PropertyException pe) {
      this.propertyExceptions.add(pe);
   }

   public void add(PropertyExceptions pes) {
      Iterator var2 = pes.getPropertyExceptions().iterator();

      while(var2.hasNext()) {
         PropertyException pe = (PropertyException)var2.next();
         this.add(pe);
      }

   }

   public List getPropertyExceptions() {
      return this.propertyExceptions;
   }
}

package com.bea.xbean.tool;

import java.util.ArrayList;
import java.util.List;

public class Extension {
   private Class className;
   private List params = new ArrayList();

   public Class getClassName() {
      return this.className;
   }

   public void setClassName(Class className) {
      this.className = className;
   }

   public List getParams() {
      return this.params;
   }

   public Param createParam() {
      Param p = new Param();
      this.params.add(p);
      return p;
   }

   public class Param {
      private String name;
      private String value;

      public String getName() {
         return this.name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }
   }
}

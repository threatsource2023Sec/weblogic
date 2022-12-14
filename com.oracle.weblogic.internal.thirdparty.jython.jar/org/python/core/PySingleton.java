package org.python.core;

@Untraversable
public class PySingleton extends PyObject {
   private String name;

   public PySingleton(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
   }
}

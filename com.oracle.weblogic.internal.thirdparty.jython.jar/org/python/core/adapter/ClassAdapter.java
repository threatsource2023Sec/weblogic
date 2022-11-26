package org.python.core.adapter;

public abstract class ClassAdapter implements PyObjectAdapter {
   private Class adaptedClass;

   public ClassAdapter(Class adaptedClass) {
      this.adaptedClass = adaptedClass;
   }

   public Class getAdaptedClass() {
      return this.adaptedClass;
   }

   public boolean canAdapt(Object o) {
      return this.adaptedClass.getClass().equals(this.adaptedClass);
   }
}

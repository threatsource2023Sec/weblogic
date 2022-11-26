package org.apache.xmlbeans.impl.jam;

import java.util.Iterator;

public class JamClassIterator implements Iterator {
   private JamClassLoader mLoader;
   private String[] mClassNames;
   private int mIndex = 0;

   public JamClassIterator(JamClassLoader loader, String[] classes) {
      if (loader == null) {
         throw new IllegalArgumentException("null loader");
      } else if (classes == null) {
         throw new IllegalArgumentException("null classes");
      } else {
         this.mLoader = loader;
         this.mClassNames = classes;
      }
   }

   public JClass nextClass() {
      if (!this.hasNext()) {
         throw new IndexOutOfBoundsException();
      } else {
         ++this.mIndex;
         return this.mLoader.loadClass(this.mClassNames[this.mIndex - 1]);
      }
   }

   public boolean hasNext() {
      return this.mIndex < this.mClassNames.length;
   }

   public Object next() {
      return this.nextClass();
   }

   public int getSize() {
      return this.mClassNames.length;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}

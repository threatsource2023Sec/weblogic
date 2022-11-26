package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.util.ClassLoaderReference;
import com.bea.core.repackaged.aspectj.weaver.WeakClassLoaderReference;

public class BcelWeakClassLoaderReference extends WeakClassLoaderReference implements ClassLoaderReference {
   public BcelWeakClassLoaderReference(ClassLoader loader) {
      super(loader);
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof BcelWeakClassLoaderReference)) {
         return false;
      } else {
         BcelWeakClassLoaderReference other = (BcelWeakClassLoaderReference)obj;
         return other.hashcode == this.hashcode;
      }
   }
}

package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class DecoratingClassLoader extends ClassLoader {
   private final Set excludedPackages = Collections.newSetFromMap(new ConcurrentHashMap(8));
   private final Set excludedClasses = Collections.newSetFromMap(new ConcurrentHashMap(8));

   public DecoratingClassLoader() {
   }

   public DecoratingClassLoader(@Nullable ClassLoader parent) {
      super(parent);
   }

   public void excludePackage(String packageName) {
      Assert.notNull(packageName, (String)"Package name must not be null");
      this.excludedPackages.add(packageName);
   }

   public void excludeClass(String className) {
      Assert.notNull(className, (String)"Class name must not be null");
      this.excludedClasses.add(className);
   }

   protected boolean isExcluded(String className) {
      if (this.excludedClasses.contains(className)) {
         return true;
      } else {
         Iterator var2 = this.excludedPackages.iterator();

         String packageName;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            packageName = (String)var2.next();
         } while(!className.startsWith(packageName));

         return true;
      }
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}

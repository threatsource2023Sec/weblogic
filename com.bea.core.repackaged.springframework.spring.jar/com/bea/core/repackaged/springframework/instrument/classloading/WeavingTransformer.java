package com.bea.core.repackaged.springframework.instrument.classloading;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeavingTransformer {
   @Nullable
   private final ClassLoader classLoader;
   private final List transformers = new ArrayList();

   public WeavingTransformer(@Nullable ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public void addTransformer(ClassFileTransformer transformer) {
      Assert.notNull(transformer, (String)"Transformer must not be null");
      this.transformers.add(transformer);
   }

   public byte[] transformIfNecessary(String className, byte[] bytes) {
      String internalName = StringUtils.replace(className, ".", "/");
      return this.transformIfNecessary(className, internalName, bytes, (ProtectionDomain)null);
   }

   public byte[] transformIfNecessary(String className, String internalName, byte[] bytes, @Nullable ProtectionDomain pd) {
      byte[] result = bytes;
      Iterator var6 = this.transformers.iterator();

      while(var6.hasNext()) {
         ClassFileTransformer cft = (ClassFileTransformer)var6.next();

         try {
            byte[] transformed = cft.transform(this.classLoader, internalName, (Class)null, pd, result);
            if (transformed != null) {
               result = transformed;
            }
         } catch (IllegalClassFormatException var9) {
            throw new IllegalStateException("Class file transformation failed", var9);
         }
      }

      return result;
   }
}

package com.bea.core.repackaged.springframework.instrument.classloading;

import com.bea.core.repackaged.springframework.core.DecoratingClassLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.FileCopyUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShadowingClassLoader extends DecoratingClassLoader {
   public static final String[] DEFAULT_EXCLUDED_PACKAGES = new String[]{"java.", "javax.", "jdk.", "sun.", "oracle.", "com.sun.", "com.ibm.", "COM.ibm.", "org.w3c.", "org.xml.", "org.dom4j.", "org.eclipse", "com.bea.core.repackaged.aspectj.", "net.sf.cglib", "com.bea.core.repackaged.springframework.cglib", "org.apache.xerces.", "com.bea.core.repackaged.apache.commons.logging."};
   private final ClassLoader enclosingClassLoader;
   private final List classFileTransformers;
   private final Map classCache;

   public ShadowingClassLoader(ClassLoader enclosingClassLoader) {
      this(enclosingClassLoader, true);
   }

   public ShadowingClassLoader(ClassLoader enclosingClassLoader, boolean defaultExcludes) {
      this.classFileTransformers = new LinkedList();
      this.classCache = new HashMap();
      Assert.notNull(enclosingClassLoader, (String)"Enclosing ClassLoader must not be null");
      this.enclosingClassLoader = enclosingClassLoader;
      if (defaultExcludes) {
         String[] var3 = DEFAULT_EXCLUDED_PACKAGES;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String excludedPackage = var3[var5];
            this.excludePackage(excludedPackage);
         }
      }

   }

   public void addTransformer(ClassFileTransformer transformer) {
      Assert.notNull(transformer, (String)"Transformer must not be null");
      this.classFileTransformers.add(transformer);
   }

   public void copyTransformers(ShadowingClassLoader other) {
      Assert.notNull(other, (String)"Other ClassLoader must not be null");
      this.classFileTransformers.addAll(other.classFileTransformers);
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      if (this.shouldShadow(name)) {
         Class cls = (Class)this.classCache.get(name);
         return cls != null ? cls : this.doLoadClass(name);
      } else {
         return this.enclosingClassLoader.loadClass(name);
      }
   }

   private boolean shouldShadow(String className) {
      return !className.equals(this.getClass().getName()) && !className.endsWith("ShadowingClassLoader") && this.isEligibleForShadowing(className);
   }

   protected boolean isEligibleForShadowing(String className) {
      return !this.isExcluded(className);
   }

   private Class doLoadClass(String name) throws ClassNotFoundException {
      String internalName = StringUtils.replace(name, ".", "/") + ".class";
      InputStream is = this.enclosingClassLoader.getResourceAsStream(internalName);
      if (is == null) {
         throw new ClassNotFoundException(name);
      } else {
         try {
            byte[] bytes = FileCopyUtils.copyToByteArray(is);
            bytes = this.applyTransformers(name, bytes);
            Class cls = this.defineClass(name, bytes, 0, bytes.length);
            if (cls.getPackage() == null) {
               int packageSeparator = name.lastIndexOf(46);
               if (packageSeparator != -1) {
                  String packageName = name.substring(0, packageSeparator);
                  this.definePackage(packageName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (URL)null);
               }
            }

            this.classCache.put(name, cls);
            return cls;
         } catch (IOException var8) {
            throw new ClassNotFoundException("Cannot load resource for class [" + name + "]", var8);
         }
      }
   }

   private byte[] applyTransformers(String name, byte[] bytes) {
      String internalName = StringUtils.replace(name, ".", "/");

      try {
         byte[] transformed;
         for(Iterator var4 = this.classFileTransformers.iterator(); var4.hasNext(); bytes = transformed != null ? transformed : bytes) {
            ClassFileTransformer transformer = (ClassFileTransformer)var4.next();
            transformed = transformer.transform(this, internalName, (Class)null, (ProtectionDomain)null, bytes);
         }

         return bytes;
      } catch (IllegalClassFormatException var7) {
         throw new IllegalStateException(var7);
      }
   }

   public URL getResource(String name) {
      return this.enclosingClassLoader.getResource(name);
   }

   @Nullable
   public InputStream getResourceAsStream(String name) {
      return this.enclosingClassLoader.getResourceAsStream(name);
   }

   public Enumeration getResources(String name) throws IOException {
      return this.enclosingClassLoader.getResources(name);
   }
}

package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.ClassReader;
import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DuplicatesPredicate implements Predicate {
   private final Set unique;
   private final Set rejected;

   public DuplicatesPredicate() {
      this.unique = new HashSet();
      this.rejected = Collections.emptySet();
   }

   public DuplicatesPredicate(List allMethods) {
      this.rejected = new HashSet();
      this.unique = new HashSet();
      Map scanned = new HashMap();
      Map suspects = new HashMap();
      Iterator var4 = allMethods.iterator();

      Object o;
      Method m;
      while(var4.hasNext()) {
         Object o = var4.next();
         Method method = (Method)o;
         o = MethodWrapper.create(method);
         m = (Method)scanned.get(o);
         if (m == null) {
            scanned.put(o, method);
         } else if (!suspects.containsKey(o) && m.isBridge() && !method.isBridge()) {
            suspects.put(o, m);
         }
      }

      if (!suspects.isEmpty()) {
         Set classes = new HashSet();
         UnnecessaryBridgeFinder finder = new UnnecessaryBridgeFinder(this.rejected);
         Iterator var18 = suspects.values().iterator();

         while(var18.hasNext()) {
            o = var18.next();
            m = (Method)o;
            classes.add(m.getDeclaringClass());
            finder.addSuspectMethod(m);
         }

         var18 = classes.iterator();

         while(var18.hasNext()) {
            o = var18.next();
            Class c = (Class)o;

            try {
               ClassLoader cl = getClassLoader(c);
               if (cl != null) {
                  InputStream is = cl.getResourceAsStream(c.getName().replace('.', '/') + ".class");
                  if (is != null) {
                     try {
                        (new ClassReader(is)).accept(finder, 6);
                     } finally {
                        is.close();
                     }
                  }
               }
            } catch (IOException var15) {
            }
         }
      }

   }

   public boolean evaluate(Object arg) {
      return !this.rejected.contains(arg) && this.unique.add(MethodWrapper.create((Method)arg));
   }

   private static ClassLoader getClassLoader(Class c) {
      ClassLoader cl = c.getClassLoader();
      if (cl == null) {
         cl = DuplicatesPredicate.class.getClassLoader();
      }

      if (cl == null) {
         cl = Thread.currentThread().getContextClassLoader();
      }

      return cl;
   }

   private static class UnnecessaryBridgeFinder extends ClassVisitor {
      private final Set rejected;
      private Signature currentMethodSig = null;
      private Map methods = new HashMap();

      UnnecessaryBridgeFinder(Set rejected) {
         super(Constants.ASM_API);
         this.rejected = rejected;
      }

      void addSuspectMethod(Method m) {
         this.methods.put(ReflectUtils.getSignature(m), m);
      }

      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         Signature sig = new Signature(name, desc);
         final Method currentMethod = (Method)this.methods.remove(sig);
         if (currentMethod != null) {
            this.currentMethodSig = sig;
            return new MethodVisitor(Constants.ASM_API) {
               public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                  if (opcode == 183 && UnnecessaryBridgeFinder.this.currentMethodSig != null) {
                     Signature target = new Signature(name, desc);
                     if (target.equals(UnnecessaryBridgeFinder.this.currentMethodSig)) {
                        UnnecessaryBridgeFinder.this.rejected.add(currentMethod);
                     }

                     UnnecessaryBridgeFinder.this.currentMethodSig = null;
                  }

               }
            };
         } else {
            return null;
         }
      }
   }
}

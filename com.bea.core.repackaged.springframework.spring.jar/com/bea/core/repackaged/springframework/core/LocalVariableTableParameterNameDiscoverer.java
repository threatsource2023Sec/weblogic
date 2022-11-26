package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.asm.ClassReader;
import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.Label;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalVariableTableParameterNameDiscoverer implements ParameterNameDiscoverer {
   private static final Log logger = LogFactory.getLog(LocalVariableTableParameterNameDiscoverer.class);
   private static final Map NO_DEBUG_INFO_MAP = Collections.emptyMap();
   private final Map parameterNamesCache = new ConcurrentHashMap(32);

   @Nullable
   public String[] getParameterNames(Method method) {
      Method originalMethod = BridgeMethodResolver.findBridgedMethod(method);
      Class declaringClass = originalMethod.getDeclaringClass();
      Map map = (Map)this.parameterNamesCache.get(declaringClass);
      if (map == null) {
         map = this.inspectClass(declaringClass);
         this.parameterNamesCache.put(declaringClass, map);
      }

      return map != NO_DEBUG_INFO_MAP ? (String[])map.get(originalMethod) : null;
   }

   @Nullable
   public String[] getParameterNames(Constructor ctor) {
      Class declaringClass = ctor.getDeclaringClass();
      Map map = (Map)this.parameterNamesCache.get(declaringClass);
      if (map == null) {
         map = this.inspectClass(declaringClass);
         this.parameterNamesCache.put(declaringClass, map);
      }

      return map != NO_DEBUG_INFO_MAP ? (String[])map.get(ctor) : null;
   }

   private Map inspectClass(Class clazz) {
      InputStream is = clazz.getResourceAsStream(ClassUtils.getClassFileName(clazz));
      if (is == null) {
         if (logger.isDebugEnabled()) {
            logger.debug("Cannot find '.class' file for class [" + clazz + "] - unable to determine constructor/method parameter names");
         }

         return NO_DEBUG_INFO_MAP;
      } else {
         try {
            ClassReader classReader = new ClassReader(is);
            Map map = new ConcurrentHashMap(32);
            classReader.accept(new ParameterNameDiscoveringVisitor(clazz, map), 0);
            ConcurrentHashMap var5 = map;
            return var5;
         } catch (IOException var17) {
            if (logger.isDebugEnabled()) {
               logger.debug("Exception thrown while reading '.class' file for class [" + clazz + "] - unable to determine constructor/method parameter names", var17);
            }
         } catch (IllegalArgumentException var18) {
            if (logger.isDebugEnabled()) {
               logger.debug("ASM ClassReader failed to parse class file [" + clazz + "], probably due to a new Java class file version that isn't supported yet - unable to determine constructor/method parameter names", var18);
            }
         } finally {
            try {
               is.close();
            } catch (IOException var16) {
            }

         }

         return NO_DEBUG_INFO_MAP;
      }
   }

   private static class LocalVariableTableVisitor extends MethodVisitor {
      private static final String CONSTRUCTOR = "<init>";
      private final Class clazz;
      private final Map memberMap;
      private final String name;
      private final Type[] args;
      private final String[] parameterNames;
      private final boolean isStatic;
      private boolean hasLvtInfo = false;
      private final int[] lvtSlotIndex;

      public LocalVariableTableVisitor(Class clazz, Map map, String name, String desc, boolean isStatic) {
         super(458752);
         this.clazz = clazz;
         this.memberMap = map;
         this.name = name;
         this.args = Type.getArgumentTypes(desc);
         this.parameterNames = new String[this.args.length];
         this.isStatic = isStatic;
         this.lvtSlotIndex = computeLvtSlotIndices(isStatic, this.args);
      }

      public void visitLocalVariable(String name, String description, String signature, Label start, Label end, int index) {
         this.hasLvtInfo = true;

         for(int i = 0; i < this.lvtSlotIndex.length; ++i) {
            if (this.lvtSlotIndex[i] == index) {
               this.parameterNames[i] = name;
            }
         }

      }

      public void visitEnd() {
         if (this.hasLvtInfo || this.isStatic && this.parameterNames.length == 0) {
            this.memberMap.put(this.resolveMember(), this.parameterNames);
         }

      }

      private Member resolveMember() {
         ClassLoader loader = this.clazz.getClassLoader();
         Class[] argTypes = new Class[this.args.length];

         for(int i = 0; i < this.args.length; ++i) {
            argTypes[i] = ClassUtils.resolveClassName(this.args[i].getClassName(), loader);
         }

         try {
            return (Member)("<init>".equals(this.name) ? this.clazz.getDeclaredConstructor(argTypes) : this.clazz.getDeclaredMethod(this.name, argTypes));
         } catch (NoSuchMethodException var4) {
            throw new IllegalStateException("Method [" + this.name + "] was discovered in the .class file but cannot be resolved in the class object", var4);
         }
      }

      private static int[] computeLvtSlotIndices(boolean isStatic, Type[] paramTypes) {
         int[] lvtIndex = new int[paramTypes.length];
         int nextIndex = isStatic ? 0 : 1;

         for(int i = 0; i < paramTypes.length; ++i) {
            lvtIndex[i] = nextIndex;
            if (isWideType(paramTypes[i])) {
               nextIndex += 2;
            } else {
               ++nextIndex;
            }
         }

         return lvtIndex;
      }

      private static boolean isWideType(Type aType) {
         return aType == Type.LONG_TYPE || aType == Type.DOUBLE_TYPE;
      }
   }

   private static class ParameterNameDiscoveringVisitor extends ClassVisitor {
      private static final String STATIC_CLASS_INIT = "<clinit>";
      private final Class clazz;
      private final Map memberMap;

      public ParameterNameDiscoveringVisitor(Class clazz, Map memberMap) {
         super(458752);
         this.clazz = clazz;
         this.memberMap = memberMap;
      }

      @Nullable
      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         return !isSyntheticOrBridged(access) && !"<clinit>".equals(name) ? new LocalVariableTableVisitor(this.clazz, this.memberMap, name, desc, isStatic(access)) : null;
      }

      private static boolean isSyntheticOrBridged(int access) {
         return (access & 4096 | access & 64) > 0;
      }

      private static boolean isStatic(int access) {
         return (access & 8) > 0;
      }
   }
}

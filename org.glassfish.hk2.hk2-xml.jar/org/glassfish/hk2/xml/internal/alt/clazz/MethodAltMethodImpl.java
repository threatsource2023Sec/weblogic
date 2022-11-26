package org.glassfish.hk2.xml.internal.alt.clazz;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.xml.internal.alt.AltAnnotation;
import org.glassfish.hk2.xml.internal.alt.AltClass;
import org.glassfish.hk2.xml.internal.alt.AltMethod;
import org.glassfish.hk2.xml.internal.alt.MethodInformationI;

public class MethodAltMethodImpl implements AltMethod {
   private final Method method;
   private final ClassReflectionHelper helper;
   private List parameterTypes;
   private List altAnnotations;
   private MethodInformationI methodInformation;

   public MethodAltMethodImpl(Method method, ClassReflectionHelper helper) {
      this.method = method;
      this.helper = helper;
   }

   public Method getOriginalMethod() {
      return this.method;
   }

   public String getName() {
      return this.method.getName();
   }

   public AltClass getReturnType() {
      Class retVal = this.method.getReturnType();
      if (retVal == null) {
         retVal = Void.TYPE;
      }

      return new ClassAltClassImpl(retVal, this.helper);
   }

   public synchronized List getParameterTypes() {
      if (this.parameterTypes != null) {
         return this.parameterTypes;
      } else {
         Class[] pTypes = this.method.getParameterTypes();
         List retVal = new ArrayList(pTypes.length);
         Class[] var3 = pTypes;
         int var4 = pTypes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class pType = var3[var5];
            retVal.add(new ClassAltClassImpl(pType, this.helper));
         }

         this.parameterTypes = Collections.unmodifiableList(retVal);
         return this.parameterTypes;
      }
   }

   public AltClass getFirstTypeArgument() {
      Type type = this.method.getGenericReturnType();
      if (type == null) {
         return null;
      } else {
         Type first = ReflectionHelper.getFirstTypeArgument(type);
         if (first == null) {
            return null;
         } else {
            Class retVal = ReflectionHelper.getRawClass(first);
            return retVal == null ? null : new ClassAltClassImpl(retVal, this.helper);
         }
      }
   }

   public AltClass getFirstTypeArgumentOfParameter(int index) {
      Type[] pTypes = this.method.getGenericParameterTypes();
      Type pType = pTypes[index];
      Type first = ReflectionHelper.getFirstTypeArgument(pType);
      if (first == null) {
         return null;
      } else {
         Class retVal = ReflectionHelper.getRawClass(first);
         return retVal == null ? null : new ClassAltClassImpl(retVal, this.helper);
      }
   }

   public AltAnnotation getAnnotation(String annotation) {
      if (annotation == null) {
         return null;
      } else {
         Annotation[] annotations = this.method.getAnnotations();
         Annotation[] var3 = annotations;
         int var4 = annotations.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Annotation anno = var3[var5];
            if (annotation.equals(anno.annotationType().getName())) {
               return new AnnotationAltAnnotationImpl(anno, this.helper);
            }
         }

         return null;
      }
   }

   public synchronized List getAnnotations() {
      if (this.altAnnotations != null) {
         return this.altAnnotations;
      } else {
         Annotation[] annotations = this.method.getAnnotations();
         ArrayList retVal = new ArrayList(annotations.length);
         Annotation[] var3 = annotations;
         int var4 = annotations.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Annotation annotation = var3[var5];
            retVal.add(new AnnotationAltAnnotationImpl(annotation, this.helper));
         }

         this.altAnnotations = Collections.unmodifiableList(retVal);
         return this.altAnnotations;
      }
   }

   public boolean isVarArgs() {
      return this.method.isVarArgs();
   }

   public void setMethodInformation(MethodInformationI methodInfo) {
      this.methodInformation = methodInfo;
   }

   public MethodInformationI getMethodInformation() {
      return this.methodInformation;
   }

   public String toString() {
      return "MethodAltMethodImpl(" + this.method + "," + System.identityHashCode(this) + ")";
   }
}

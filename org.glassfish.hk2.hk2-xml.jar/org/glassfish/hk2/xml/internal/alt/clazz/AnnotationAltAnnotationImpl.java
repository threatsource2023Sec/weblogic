package org.glassfish.hk2.xml.internal.alt.clazz;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.utilities.reflection.internal.ClassReflectionHelperImpl;
import org.glassfish.hk2.xml.internal.alt.AltAnnotation;
import org.glassfish.hk2.xml.internal.alt.AltClass;
import org.glassfish.hk2.xml.internal.alt.AltEnum;
import org.glassfish.hk2.xml.jaxb.internal.XmlElementImpl;

public class AnnotationAltAnnotationImpl implements AltAnnotation {
   private static final Set DO_NOT_HANDLE_METHODS = new HashSet();
   private final Annotation annotation;
   private final ClassReflectionHelper helper;
   private Map values;

   public AnnotationAltAnnotationImpl(Annotation annotation, ClassReflectionHelper helper) {
      this.annotation = annotation;
      if (helper == null) {
         this.helper = new ClassReflectionHelperImpl();
      } else {
         this.helper = helper;
      }

   }

   public Annotation getOriginalAnnotation() {
      return this.annotation;
   }

   public String annotationType() {
      return this.annotation.annotationType().getName();
   }

   public synchronized String getStringValue(String methodName) {
      if (this.values == null) {
         this.getAnnotationValues();
      }

      if (XmlElementImpl.class.equals(this.annotation.getClass()) && "getTypeByName".equals(methodName)) {
         XmlElementImpl xei = (XmlElementImpl)this.annotation;
         return xei.getTypeByName();
      } else {
         return (String)this.values.get(methodName);
      }
   }

   public synchronized boolean getBooleanValue(String methodName) {
      if (this.values == null) {
         this.getAnnotationValues();
      }

      return (Boolean)this.values.get(methodName);
   }

   public synchronized String[] getStringArrayValue(String methodName) {
      if (this.values == null) {
         this.getAnnotationValues();
      }

      return (String[])((String[])this.values.get(methodName));
   }

   public AltAnnotation[] getAnnotationArrayValue(String methodName) {
      if (this.values == null) {
         this.getAnnotationValues();
      }

      return (AltAnnotation[])((AltAnnotation[])this.values.get(methodName));
   }

   public AltClass getClassValue(String methodName) {
      if (this.values == null) {
         this.getAnnotationValues();
      }

      return (AltClass)this.values.get(methodName);
   }

   public synchronized Map getAnnotationValues() {
      if (this.values != null) {
         return this.values;
      } else {
         Map retVal = new TreeMap();
         Method[] var2 = this.annotation.annotationType().getMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method javaAnnotationMethod = var2[var4];
            if (javaAnnotationMethod.getParameterTypes().length == 0 && !DO_NOT_HANDLE_METHODS.contains(javaAnnotationMethod.getName())) {
               String key = javaAnnotationMethod.getName();

               Object value;
               try {
                  value = ReflectionHelper.invoke(this.annotation, javaAnnotationMethod, new Object[0], false);
                  if (value == null) {
                     throw new AssertionError("Recieved null from annotation method " + javaAnnotationMethod.getName());
                  }
               } catch (RuntimeException var11) {
                  throw var11;
               } catch (Throwable var12) {
                  throw new RuntimeException(var12);
               }

               if (value instanceof Class) {
                  value = new ClassAltClassImpl((Class)value, this.helper);
               } else if (Enum.class.isAssignableFrom(value.getClass())) {
                  value = new EnumAltEnumImpl((Enum)value);
               } else {
                  int lcv;
                  if (value.getClass().isArray() && Class.class.equals(value.getClass().getComponentType())) {
                     Class[] cValue = (Class[])((Class[])value);
                     AltClass[] translatedValue = new AltClass[cValue.length];

                     for(lcv = 0; lcv < cValue.length; ++lcv) {
                        translatedValue[lcv] = new ClassAltClassImpl(cValue[lcv], this.helper);
                     }

                     value = translatedValue;
                  } else if (value.getClass().isArray() && Enum.class.isAssignableFrom(value.getClass().getComponentType())) {
                     Enum[] eValue = (Enum[])((Enum[])value);
                     AltEnum[] translatedValue = new AltEnum[eValue.length];

                     for(lcv = 0; lcv < eValue.length; ++lcv) {
                        translatedValue[lcv] = new EnumAltEnumImpl(eValue[lcv]);
                     }

                     value = translatedValue;
                  } else if (value.getClass().isArray() && Annotation.class.isAssignableFrom(value.getClass().getComponentType())) {
                     Annotation[] aValue = (Annotation[])((Annotation[])value);
                     AltAnnotation[] translatedValue = new AltAnnotation[aValue.length];

                     for(lcv = 0; lcv < aValue.length; ++lcv) {
                        translatedValue[lcv] = new AnnotationAltAnnotationImpl(aValue[lcv], this.helper);
                     }

                     value = translatedValue;
                  }
               }

               retVal.put(key, value);
            }
         }

         this.values = Collections.unmodifiableMap(retVal);
         return this.values;
      }
   }

   public String toString() {
      return "AnnotationAltAnnotationImpl(" + this.annotation + "," + System.identityHashCode(this) + ")";
   }

   static {
      DO_NOT_HANDLE_METHODS.add("hashCode");
      DO_NOT_HANDLE_METHODS.add("equals");
      DO_NOT_HANDLE_METHODS.add("toString");
      DO_NOT_HANDLE_METHODS.add("annotationType");
   }
}

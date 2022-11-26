package org.glassfish.hk2.xml.internal.alt.clazz;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.utilities.reflection.internal.ClassReflectionHelperImpl;
import org.glassfish.hk2.xml.internal.alt.AltClass;

public class ClassAltClassImpl implements AltClass {
   private static final ClassReflectionHelper SCALAR_HELPER = new ClassReflectionHelperImpl();
   public static final AltClass VOID;
   public static final AltClass BOOLEAN;
   public static final AltClass BYTE;
   public static final AltClass CHAR;
   public static final AltClass SHORT;
   public static final AltClass INT;
   public static final AltClass LONG;
   public static final AltClass FLOAT;
   public static final AltClass DOUBLE;
   public static final AltClass OBJECT;
   public static final AltClass XML_ADAPTER;
   private final Class clazz;
   private final ClassReflectionHelper helper;
   private List methods;
   private List annotations;

   public ClassAltClassImpl(Class clazz, ClassReflectionHelper helper) {
      this.clazz = clazz;
      this.helper = helper;
   }

   public Class getOriginalClass() {
      return this.clazz;
   }

   public String getName() {
      return this.clazz.getName();
   }

   public String getSimpleName() {
      return this.clazz.getSimpleName();
   }

   public synchronized List getAnnotations() {
      if (this.annotations != null) {
         return this.annotations;
      } else {
         Annotation[] annotationz = this.clazz.getAnnotations();
         ArrayList retVal = new ArrayList(annotationz.length);
         Annotation[] var3 = annotationz;
         int var4 = annotationz.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Annotation annotation = var3[var5];
            retVal.add(new AnnotationAltAnnotationImpl(annotation, this.helper));
         }

         this.annotations = Collections.unmodifiableList(retVal);
         return this.annotations;
      }
   }

   public synchronized List getMethods() {
      if (this.methods != null) {
         return this.methods;
      } else {
         Set wrappers = this.helper.getAllMethods(this.clazz);
         ArrayList retVal = new ArrayList(wrappers.size());
         Iterator var3 = wrappers.iterator();

         while(var3.hasNext()) {
            MethodWrapper method = (MethodWrapper)var3.next();
            retVal.add(new MethodAltMethodImpl(method.getMethod(), this.helper));
         }

         this.methods = Collections.unmodifiableList(retVal);
         return this.methods;
      }
   }

   public AltClass getSuperParameterizedType(AltClass superType, int paramIndex) {
      Class previousClazz = this.clazz;

      for(Class superClass = previousClazz.getSuperclass(); superClass != null; superClass = superClass.getSuperclass()) {
         if (superType.getName().equals(superClass.getName())) {
            Type genericType = previousClazz.getGenericSuperclass();
            if (!(genericType instanceof ParameterizedType)) {
               return null;
            }

            ParameterizedType pt = (ParameterizedType)genericType;
            Type actualType = pt.getActualTypeArguments()[paramIndex];
            Class rawType = ReflectionHelper.getRawClass(actualType);
            if (rawType == null) {
               return null;
            }

            return new ClassAltClassImpl(rawType, this.helper);
         }

         previousClazz = superClass;
      }

      return null;
   }

   public boolean isInterface() {
      return this.clazz.isInterface();
   }

   public boolean isArray() {
      return this.clazz.isArray();
   }

   public AltClass getComponentType() {
      Class cType = this.clazz.getComponentType();
      return cType == null ? null : new ClassAltClassImpl(cType, this.helper);
   }

   public int hashCode() {
      return this.getName().hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof AltClass)) {
         return false;
      } else {
         AltClass other = (AltClass)o;
         return this.getName().equals(other.getName());
      }
   }

   public String toString() {
      return "ClassAltClassImpl(" + this.clazz.getName() + "," + System.identityHashCode(this) + ")";
   }

   static {
      VOID = new ClassAltClassImpl(Void.TYPE, SCALAR_HELPER);
      BOOLEAN = new ClassAltClassImpl(Boolean.TYPE, SCALAR_HELPER);
      BYTE = new ClassAltClassImpl(Byte.TYPE, SCALAR_HELPER);
      CHAR = new ClassAltClassImpl(Character.TYPE, SCALAR_HELPER);
      SHORT = new ClassAltClassImpl(Short.TYPE, SCALAR_HELPER);
      INT = new ClassAltClassImpl(Integer.TYPE, SCALAR_HELPER);
      LONG = new ClassAltClassImpl(Long.TYPE, SCALAR_HELPER);
      FLOAT = new ClassAltClassImpl(Float.TYPE, SCALAR_HELPER);
      DOUBLE = new ClassAltClassImpl(Double.TYPE, SCALAR_HELPER);
      OBJECT = new ClassAltClassImpl(Object.class, SCALAR_HELPER);
      XML_ADAPTER = new ClassAltClassImpl(XmlAdapter.class, SCALAR_HELPER);
   }
}

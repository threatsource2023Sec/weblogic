package jnr.ffi.provider.jffi;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.util.Annotations;

class ConverterMetaData {
   private static volatile Reference cacheReference;
   final Collection classAnnotations;
   final Collection toNativeMethodAnnotations;
   final Collection fromNativeMethodAnnotations;
   final Collection nativeTypeMethodAnnotations;
   final Collection toNativeAnnotations;
   final Collection fromNativeAnnotations;

   ConverterMetaData(Class converterClass, Class nativeType) {
      this.classAnnotations = Annotations.sortedAnnotationCollection(converterClass.getAnnotations());
      this.nativeTypeMethodAnnotations = getConverterMethodAnnotations(converterClass, "nativeType");
      this.fromNativeMethodAnnotations = getConverterMethodAnnotations(converterClass, "fromNative", nativeType, FromNativeContext.class);
      this.toNativeMethodAnnotations = getConverterMethodAnnotations(converterClass, "toNative", nativeType, ToNativeContext.class);
      this.toNativeAnnotations = Annotations.mergeAnnotations(this.classAnnotations, this.toNativeMethodAnnotations, this.nativeTypeMethodAnnotations);
      this.fromNativeAnnotations = Annotations.mergeAnnotations(this.classAnnotations, this.fromNativeMethodAnnotations, this.nativeTypeMethodAnnotations);
   }

   private static Collection getToNativeMethodAnnotations(Class converterClass, Class resultClass) {
      try {
         Method baseMethod = converterClass.getMethod("toNative", Object.class, ToNativeContext.class);
         Method[] var3 = converterClass.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            if (m.getName().equals("toNative") && resultClass.isAssignableFrom(m.getReturnType())) {
               Class[] methodParameterTypes = m.getParameterTypes();
               if (methodParameterTypes.length == 2 && methodParameterTypes[1].isAssignableFrom(ToNativeContext.class)) {
                  return Annotations.mergeAnnotations(Annotations.sortedAnnotationCollection(m.getAnnotations()), Annotations.sortedAnnotationCollection(baseMethod.getAnnotations()));
               }
            }
         }

         return Annotations.EMPTY_ANNOTATIONS;
      } catch (SecurityException var8) {
         return Annotations.EMPTY_ANNOTATIONS;
      } catch (NoSuchMethodException var9) {
         return Annotations.EMPTY_ANNOTATIONS;
      }
   }

   private static Collection getConverterMethodAnnotations(Class converterClass, String methodName, Class... parameterClasses) {
      try {
         return Annotations.sortedAnnotationCollection(converterClass.getMethod(methodName).getAnnotations());
      } catch (NoSuchMethodException var4) {
         return Annotations.EMPTY_ANNOTATIONS;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   private static ConverterMetaData getMetaData(Class converterClass, Class nativeType) {
      Map cache = cacheReference != null ? (Map)cacheReference.get() : null;
      ConverterMetaData metaData;
      return cache != null && (metaData = (ConverterMetaData)cache.get(converterClass)) != null ? metaData : addMetaData(converterClass, nativeType);
   }

   private static synchronized ConverterMetaData addMetaData(Class converterClass, Class nativeType) {
      Map cache = cacheReference != null ? (Map)cacheReference.get() : null;
      ConverterMetaData metaData;
      if (cache != null && (metaData = (ConverterMetaData)cache.get(converterClass)) != null) {
         return metaData;
      } else {
         Map m = new HashMap(cache != null ? cache : Collections.EMPTY_MAP);
         m.put(converterClass, metaData = new ConverterMetaData(converterClass, nativeType));
         cacheReference = new SoftReference(new IdentityHashMap(m));
         return metaData;
      }
   }

   static Collection getAnnotations(ToNativeConverter toNativeConverter) {
      return toNativeConverter != null ? getMetaData(toNativeConverter.getClass(), toNativeConverter.nativeType()).toNativeAnnotations : Annotations.EMPTY_ANNOTATIONS;
   }

   static Collection getAnnotations(FromNativeConverter fromNativeConverter) {
      return fromNativeConverter != null ? getMetaData(fromNativeConverter.getClass(), fromNativeConverter.nativeType()).fromNativeAnnotations : Annotations.EMPTY_ANNOTATIONS;
   }
}

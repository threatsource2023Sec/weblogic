package jnr.ffi.provider.jffi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import jnr.ffi.mapper.AbstractSignatureTypeMapper;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.FromNativeType;
import jnr.ffi.mapper.FromNativeTypes;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.mapper.ToNativeType;
import jnr.ffi.mapper.ToNativeTypes;

public class AnnotationTypeMapper extends AbstractSignatureTypeMapper implements SignatureTypeMapper {
   public FromNativeType getFromNativeType(SignatureType type, FromNativeContext context) {
      Method fromNativeMethod = findMethodWithAnnotation(type, FromNativeConverter.FromNative.class);
      if (fromNativeMethod == null) {
         return null;
      } else if (!Modifier.isStatic(fromNativeMethod.getModifiers())) {
         throw new IllegalArgumentException(fromNativeMethod.getDeclaringClass().getName() + "." + fromNativeMethod.getName() + " should be declared static");
      } else {
         return FromNativeTypes.create(new ReflectionFromNativeConverter(fromNativeMethod, ((FromNativeConverter.FromNative)fromNativeMethod.getAnnotation(FromNativeConverter.FromNative.class)).nativeType()));
      }
   }

   public ToNativeType getToNativeType(SignatureType type, ToNativeContext context) {
      Method toNativeMethod = findMethodWithAnnotation(type, ToNativeConverter.ToNative.class);
      if (toNativeMethod == null) {
         return null;
      } else if (!Modifier.isStatic(toNativeMethod.getModifiers())) {
         throw new IllegalArgumentException(toNativeMethod.getDeclaringClass().getName() + "." + toNativeMethod.getName() + " should be declared static");
      } else {
         return ToNativeTypes.create(new ReflectionToNativeConverter(toNativeMethod, ((ToNativeConverter.ToNative)toNativeMethod.getAnnotation(ToNativeConverter.ToNative.class)).nativeType()));
      }
   }

   private static Method findMethodWithAnnotation(SignatureType type, Class annotationClass) {
      for(Class klass = type.getDeclaredType(); klass != null && klass != Object.class; klass = klass.getSuperclass()) {
         Method[] var3 = klass.getDeclaredMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            if (m.isAnnotationPresent(annotationClass)) {
               return m;
            }
         }
      }

      return null;
   }

   @ToNativeConverter.Cacheable
   public final class ReflectionToNativeConverter extends AbstractReflectionConverter implements ToNativeConverter {
      public ReflectionToNativeConverter(Method method, Class nativeType) {
         super(method, nativeType);
      }

      public Object toNative(Object nativeValue, ToNativeContext context) {
         return this.invoke(nativeValue, context);
      }
   }

   @FromNativeConverter.Cacheable
   public final class ReflectionFromNativeConverter extends AbstractReflectionConverter implements FromNativeConverter {
      public ReflectionFromNativeConverter(Method method, Class nativeType) {
         super(method, nativeType);
      }

      public Object fromNative(Object nativeValue, FromNativeContext context) {
         return this.invoke(nativeValue, context);
      }
   }

   public abstract class AbstractReflectionConverter {
      protected final Method method;
      protected final Class nativeType;

      public AbstractReflectionConverter(Method method, Class nativeType) {
         this.method = method;
         this.nativeType = nativeType;
      }

      protected final Object invoke(Object value, Object context) {
         try {
            return this.method.invoke(this.method.getDeclaringClass(), value, context);
         } catch (IllegalAccessException var4) {
            throw new RuntimeException(var4);
         } catch (InvocationTargetException var5) {
            throw new RuntimeException(var5);
         }
      }

      public final Class nativeType() {
         return this.nativeType;
      }
   }
}

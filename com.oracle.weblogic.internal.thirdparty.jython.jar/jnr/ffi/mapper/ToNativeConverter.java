package jnr.ffi.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ToNativeConverter {
   Object toNative(Object var1, ToNativeContext var2);

   Class nativeType();

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD})
   public @interface ToNative {
      Class nativeType();
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.TYPE})
   public @interface Cacheable {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.TYPE, ElementType.METHOD})
   public @interface NoContext {
   }

   public interface PostInvocation extends ToNativeConverter {
      void postInvoke(Object var1, Object var2, ToNativeContext var3);
   }
}

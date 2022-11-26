package jnr.ffi.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface FromNativeConverter {
   Object fromNative(Object var1, FromNativeContext var2);

   Class nativeType();

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD})
   public @interface FromNative {
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
}

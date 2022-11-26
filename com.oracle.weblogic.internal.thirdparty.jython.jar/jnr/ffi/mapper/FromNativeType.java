package jnr.ffi.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface FromNativeType {
   FromNativeConverter getFromNativeConverter();

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.TYPE})
   public @interface Cacheable {
   }
}

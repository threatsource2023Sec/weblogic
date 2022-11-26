package jnr.ffi.provider;

import java.lang.reflect.Type;
import java.util.Collection;
import jnr.ffi.NativeType;
import jnr.ffi.mapper.SignatureType;

public abstract class SigType implements SignatureType {
   private final Class javaType;
   private final Class convertedType;
   private final Collection annotations;
   private final NativeType nativeType;

   public SigType(Class javaType, NativeType nativeType, Collection annotations, Class convertedType) {
      this.javaType = javaType;
      this.annotations = annotations;
      this.convertedType = convertedType;
      this.nativeType = nativeType;
   }

   public final Class getDeclaredType() {
      return this.javaType;
   }

   public final Class effectiveJavaType() {
      return this.convertedType;
   }

   public final Collection annotations() {
      return this.annotations;
   }

   public final Collection getAnnotations() {
      return this.annotations;
   }

   public Type getGenericType() {
      return this.getDeclaredType();
   }

   public final String toString() {
      return String.format("declared: %s, effective: %s, native: %s", this.getDeclaredType(), this.effectiveJavaType(), this.getNativeType());
   }

   public NativeType getNativeType() {
      return this.nativeType;
   }
}

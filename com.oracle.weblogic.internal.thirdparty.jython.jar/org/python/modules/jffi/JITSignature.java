package org.python.modules.jffi;

import com.kenai.jffi.CallingConvention;
import java.util.Arrays;

public final class JITSignature {
   private final NativeType resultType;
   private final NativeType[] parameterTypes;
   private final boolean hasResultConverter;
   private final boolean[] hasParameterConverter;
   private final CallingConvention convention;
   private final boolean ignoreError;

   public JITSignature(NativeType resultType, NativeType[] parameterTypes, boolean hasResultConverter, boolean[] hasParameterConverter, CallingConvention convention, boolean ignoreError) {
      this.resultType = resultType;
      this.parameterTypes = (NativeType[])((NativeType[])parameterTypes.clone());
      this.convention = convention;
      this.ignoreError = ignoreError;
      this.hasResultConverter = hasResultConverter;
      this.hasParameterConverter = (boolean[])((boolean[])hasParameterConverter.clone());
   }

   public boolean equals(Object o) {
      if (o != null && o.getClass().equals(this.getClass())) {
         JITSignature rhs = (JITSignature)o;
         return this.resultType.equals(rhs.resultType) && this.convention.equals(rhs.convention) && this.ignoreError == rhs.ignoreError && Arrays.equals(this.parameterTypes, rhs.parameterTypes) && this.hasResultConverter == rhs.hasResultConverter && Arrays.equals(this.hasParameterConverter, rhs.hasParameterConverter);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.resultType.hashCode() ^ this.convention.hashCode() ^ Boolean.valueOf(this.ignoreError).hashCode() ^ Arrays.hashCode(this.parameterTypes) ^ Boolean.valueOf(this.hasResultConverter).hashCode() ^ Arrays.hashCode(this.hasParameterConverter);
   }

   public final NativeType getResultType() {
      return this.resultType;
   }

   public final NativeType getParameterType(int parameterIndex) {
      return this.parameterTypes[parameterIndex];
   }

   public final CallingConvention getCallingConvention() {
      return this.convention;
   }

   public final int getParameterCount() {
      return this.parameterTypes.length;
   }

   public final boolean hasResultConverter() {
      return this.hasResultConverter;
   }

   public final boolean hasParameterConverter(int parameterIndex) {
      return this.hasParameterConverter[parameterIndex];
   }

   public boolean isIgnoreError() {
      return this.ignoreError;
   }
}

package jnr.ffi.provider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import jnr.ffi.CallingConvention;
import jnr.ffi.annotations.IgnoreError;
import jnr.ffi.annotations.SaveError;

public final class NativeFunction {
   private final Method method;
   private final Collection annotations;
   private final boolean saveError;
   private final boolean ignoreError;
   private final CallingConvention callingConvention;

   public NativeFunction(Method method, CallingConvention callingConvention) {
      this.method = method;
      this.annotations = Collections.unmodifiableCollection(Arrays.asList(method.getAnnotations()));
      this.saveError = hasSaveError(method);
      this.ignoreError = hasIgnoreError(method);
      this.callingConvention = callingConvention;
   }

   public Collection annotations() {
      return this.annotations;
   }

   public CallingConvention convention() {
      return this.callingConvention;
   }

   public String name() {
      return this.method.getName();
   }

   public boolean isErrnoRequired() {
      return !this.ignoreError || this.saveError;
   }

   public boolean hasSaveError() {
      return this.saveError;
   }

   public boolean hasIgnoreError() {
      return this.ignoreError;
   }

   public Method getMethod() {
      return this.method;
   }

   public static boolean hasSaveError(Method method) {
      return method.getAnnotation(SaveError.class) != null;
   }

   public static boolean hasIgnoreError(Method method) {
      return method.getAnnotation(IgnoreError.class) != null;
   }
}

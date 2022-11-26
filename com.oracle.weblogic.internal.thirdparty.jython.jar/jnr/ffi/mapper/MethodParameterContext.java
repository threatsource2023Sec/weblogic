package jnr.ffi.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import jnr.ffi.Runtime;
import jnr.ffi.util.Annotations;

public final class MethodParameterContext implements ToNativeContext {
   private final Runtime runtime;
   private final Method method;
   private final int parameterIndex;
   private Collection annotations;
   private Annotation[] annotationArray;

   public MethodParameterContext(Runtime runtime, Method method, int parameterIndex) {
      this.runtime = runtime;
      this.method = method;
      this.parameterIndex = parameterIndex;
   }

   public MethodParameterContext(Runtime runtime, Method method, int parameterIndex, Annotation[] annotationArray) {
      this.runtime = runtime;
      this.method = method;
      this.parameterIndex = parameterIndex;
      this.annotationArray = (Annotation[])annotationArray.clone();
   }

   public MethodParameterContext(Runtime runtime, Method method, int parameterIndex, Collection annotations) {
      this.runtime = runtime;
      this.method = method;
      this.parameterIndex = parameterIndex;
      this.annotations = Annotations.sortedAnnotationCollection(annotations);
   }

   public Method getMethod() {
      return this.method;
   }

   public int getParameterIndex() {
      return this.parameterIndex;
   }

   public Collection getAnnotations() {
      return this.annotations != null ? this.annotations : this.buildAnnotationCollection();
   }

   public Runtime getRuntime() {
      return this.runtime;
   }

   private Collection buildAnnotationCollection() {
      return this.annotationArray != null ? (this.annotations = Annotations.sortedAnnotationCollection(this.annotationArray)) : (this.annotations = Annotations.sortedAnnotationCollection(this.annotationArray = this.method.getParameterAnnotations()[this.parameterIndex]));
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MethodParameterContext that = (MethodParameterContext)o;
         return this.parameterIndex == that.parameterIndex && this.method.equals(that.method) && this.getAnnotations().equals(that.getAnnotations());
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.method.hashCode();
      result = 31 * result + this.parameterIndex;
      result = 31 * result + this.getAnnotations().hashCode();
      return result;
   }
}

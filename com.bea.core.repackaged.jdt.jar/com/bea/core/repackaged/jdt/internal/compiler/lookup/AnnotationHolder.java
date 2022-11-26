package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class AnnotationHolder {
   AnnotationBinding[] annotations;

   static AnnotationHolder storeAnnotations(AnnotationBinding[] annotations, AnnotationBinding[][] parameterAnnotations, Object defaultValue, LookupEnvironment optionalEnv) {
      if (parameterAnnotations != null) {
         boolean isEmpty = true;
         int i = parameterAnnotations.length;

         while(isEmpty) {
            --i;
            if (i < 0) {
               break;
            }

            if (parameterAnnotations[i] != null && parameterAnnotations[i].length > 0) {
               isEmpty = false;
            }
         }

         if (isEmpty) {
            parameterAnnotations = null;
         }
      }

      if (defaultValue != null) {
         return new AnnotationMethodHolder(annotations, parameterAnnotations, defaultValue, optionalEnv);
      } else {
         return (AnnotationHolder)(parameterAnnotations != null ? new MethodHolder(annotations, parameterAnnotations) : (new AnnotationHolder()).setAnnotations(annotations));
      }
   }

   AnnotationBinding[] getAnnotations() {
      return this.annotations;
   }

   Object getDefaultValue() {
      return null;
   }

   public AnnotationBinding[][] getParameterAnnotations() {
      return null;
   }

   AnnotationBinding[] getParameterAnnotations(int paramIndex) {
      return Binding.NO_ANNOTATIONS;
   }

   AnnotationHolder setAnnotations(AnnotationBinding[] annotations) {
      this.annotations = annotations;
      return annotations != null && annotations.length != 0 ? this : null;
   }

   static class AnnotationMethodHolder extends MethodHolder {
      Object defaultValue;
      LookupEnvironment env;

      AnnotationMethodHolder(AnnotationBinding[] annotations, AnnotationBinding[][] parameterAnnotations, Object defaultValue, LookupEnvironment optionalEnv) {
         super(annotations, parameterAnnotations);
         this.defaultValue = defaultValue;
         this.env = optionalEnv;
      }

      Object getDefaultValue() {
         if (this.defaultValue instanceof UnresolvedReferenceBinding) {
            if (this.env == null) {
               throw new IllegalStateException();
            }

            this.defaultValue = ((UnresolvedReferenceBinding)this.defaultValue).resolve(this.env, false);
         }

         return this.defaultValue;
      }
   }

   static class MethodHolder extends AnnotationHolder {
      AnnotationBinding[][] parameterAnnotations;

      MethodHolder(AnnotationBinding[] annotations, AnnotationBinding[][] parameterAnnotations) {
         this.setAnnotations(annotations);
         this.parameterAnnotations = parameterAnnotations;
      }

      public AnnotationBinding[][] getParameterAnnotations() {
         return this.parameterAnnotations;
      }

      AnnotationBinding[] getParameterAnnotations(int paramIndex) {
         AnnotationBinding[] result = this.parameterAnnotations == null ? null : this.parameterAnnotations[paramIndex];
         return result == null ? Binding.NO_ANNOTATIONS : result;
      }

      AnnotationHolder setAnnotations(AnnotationBinding[] annotations) {
         this.annotations = annotations != null && annotations.length != 0 ? annotations : Binding.NO_ANNOTATIONS;
         return this;
      }
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class UnresolvedAnnotationBinding extends AnnotationBinding {
   private LookupEnvironment env;
   private boolean typeUnresolved = true;

   UnresolvedAnnotationBinding(ReferenceBinding type, ElementValuePair[] pairs, LookupEnvironment env) {
      super(type, pairs);
      this.env = env;
   }

   public void resolve() {
      if (this.typeUnresolved) {
         boolean wasToleratingMissingTypeProcessingAnnotations = this.env.mayTolerateMissingType;
         this.env.mayTolerateMissingType = true;

         try {
            this.type = (ReferenceBinding)BinaryTypeBinding.resolveType(this.type, this.env, false);
         } finally {
            this.env.mayTolerateMissingType = wasToleratingMissingTypeProcessingAnnotations;
         }

         this.typeUnresolved = false;
      }

   }

   public ReferenceBinding getAnnotationType() {
      this.resolve();
      return this.type;
   }

   public ElementValuePair[] getElementValuePairs() {
      if (this.env != null) {
         if (this.typeUnresolved) {
            this.resolve();
         }

         int i = this.pairs.length;

         while(true) {
            --i;
            if (i < 0) {
               this.env = null;
               break;
            }

            ElementValuePair pair = this.pairs[i];
            MethodBinding[] methods = this.type.getMethods(pair.getName());
            if (methods != null && methods.length == 1) {
               pair.setMethodBinding(methods[0]);
            }

            Object value = pair.getValue();
            boolean wasToleratingMissingTypeProcessingAnnotations = this.env.mayTolerateMissingType;
            this.env.mayTolerateMissingType = true;

            try {
               if (value instanceof UnresolvedReferenceBinding) {
                  pair.setValue(((UnresolvedReferenceBinding)value).resolve(this.env, false));
               } else if (value instanceof Object[]) {
                  Object[] values = (Object[])value;

                  for(int j = 0; j < values.length; ++j) {
                     if (values[j] instanceof UnresolvedReferenceBinding) {
                        values[j] = ((UnresolvedReferenceBinding)values[j]).resolve(this.env, false);
                     }
                  }
               }
            } finally {
               this.env.mayTolerateMissingType = wasToleratingMissingTypeProcessingAnnotations;
            }
         }
      }

      return this.pairs;
   }
}
